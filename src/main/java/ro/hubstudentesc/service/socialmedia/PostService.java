package ro.hubstudentesc.service.socialmedia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.dto.socialmedia.*;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;
import ro.hubstudentesc.mapper.socialmedia.PostMapper;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.socialmedia.Post;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;
import ro.hubstudentesc.persistence.repository.socialmedia.PostLikeRepository;
import ro.hubstudentesc.persistence.repository.socialmedia.PostRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final String FEED_CACHE_PREFIX = "feed:first:";
    private static final Duration CACHE_TTL = Duration.ofSeconds(60);

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Creează o postare nouă.
     *
     * userId este primit din JWT, nu din request body.
     */
    public UUID addPost(
            UUID userId,
            PostRecordDto dto
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Utilizator inexistent"
                        )
                );

        Post post = postMapper.toEntity(dto);

        post.setAuthor(user);

        LocalDateTime now = LocalDateTime.now();

        post.setCreatedAt(now);
        post.setUpdatedAt(now);

        postRepository.save(post);

        // Feed-ul s-a modificat, deci invalidăm cache-ul.
        clearFeedCache();

        return post.getId();
    }

    /**
     * Returnează toate postările folosind paginare Spring.
     */
    public Page<PostRecordDto> findAll(
            Pageable pageable
    ) {
        return postRepository
                .findAll(pageable)
                .map(postMapper::toDto);
    }

    /**
     * Returnează postările filtrate după tip.
     */
    public Page<PostRecordDto> findByType(
            PostType type,
            Pageable pageable
    ) {
        return postRepository
                .findByType(type, pageable)
                .map(postMapper::toDto);
    }

    /**
     * Modifică o postare.
     *
     * Doar autorul postării o poate modifica.
     */
    public void updatePost(
            UUID id,
            UUID userId,
            PostRecordDto dto
    ) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Postare inexistenta"
                        )
                );

        if (!post.getAuthor().getId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Nu poti modifica postarea altui utilizator"
            );
        }

        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setType(dto.type());
        post.setMediaUrls(dto.mediaUrls());
        post.setEventDate(dto.eventDate());
        post.setLocation(dto.location());

        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        clearFeedCache();
    }

    /**
     * Șterge o postare.
     *
     * Doar autorul postării o poate șterge.
     */
    public void deletePost(
            UUID id,
            UUID userId
    ) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Postare inexistenta"
                        )
                );

        if (!post.getAuthor().getId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Nu poti sterge postarea altui utilizator"
            );
        }

        postRepository.delete(post);

        clearFeedCache();
    }

    /**
     * Construiește feed-ul utilizatorului folosind cursor pagination.
     */
    public FeedResponseDto getFeed(
            LocalDateTime cursor,
            int limit,
            UUID userId,
            PostType type
    ) {
        if (limit < 1) {
            limit = 10;
        }

        if (limit > 100) {
            limit = 100;
        }

        /*
         * Cache-ul este folosit doar pentru prima pagină
         * și doar pentru feed-ul global.
         */
        if (cursor == null && type == null) {

            FeedResponseDto cachedFeed =
                    getFromCache(userId, limit);

            if (cachedFeed != null) {
                return cachedFeed;
            }
        }

        /*
         * Cerem limit + 1 postări.
         * Astfel putem determina dacă există o pagină următoare.
         */
        PageRequest pageable = PageRequest.of(
                0,
                limit + 1,
                Sort.by(
                        Sort.Direction.DESC,
                        "createdAt"
                )
        );

        List<Post> posts;

        /*
         * Prima pagină.
         */
        if (cursor == null) {

            if (type == null) {

                posts = postRepository
                        .findAll(pageable)
                        .getContent();

            } else {

                posts = postRepository
                        .findByType(
                                type,
                                pageable
                        )
                        .getContent();
            }

        } else {

            /*
             * Paginile următoare.
             */
            if (type == null) {

                posts = postRepository
                        .findPostsBefore(
                                cursor,
                                pageable
                        );

            } else {

                posts = postRepository
                        .findPostsBeforeAndType(
                                cursor,
                                type,
                                pageable
                        );
            }
        }

        boolean hasMore = posts.size() > limit;

        if (hasMore) {
            posts = posts.subList(0, limit);
        }

        LocalDateTime nextCursor = null;

        if (!posts.isEmpty()) {
            nextCursor = posts
                    .get(posts.size() - 1)
                    .getCreatedAt();
        }

        List<FeedPostDto> data = posts.stream()
                .map(post -> new FeedPostDto(
                        post.getId(),

                        new FeedAuthorDto(
                                post.getAuthor().getId(),
                                buildAuthorName(post.getAuthor()),
                                post.getAuthor().getPicture()
                        ),

                        post.getType(),
                        post.getTitle(),
                        post.getContent(),

                        post.getMediaUrls() == null
                                ? List.of()
                                : List.of(post.getMediaUrls()),

                        post.getEventDate(),
                        post.getLocation(),

                        post.getLikesCount(),
                        post.getCommentsCount(),

                        postLikeRepository
                                .existsByPost_IdAndUser_Id(
                                        post.getId(),
                                        userId
                                ),

                        post.getCreatedAt()
                ))
                .toList();

        FeedPaginationDto pagination =
                new FeedPaginationDto(
                        nextCursor,
                        hasMore
                );

        FeedResponseDto response =
                new FeedResponseDto(
                        data,
                        pagination
                );

        /*
         * Salvăm în Redis doar prima pagină
         * a feed-ului global.
         */
        if (cursor == null && type == null) {

            saveToCache(
                    userId,
                    limit,
                    response
            );
        }

        return response;
    }

    /**
     * Construiește numele autorului.
     */
    private String buildAuthorName(
            User user
    ) {
        String givenName = user.getGivenName();
        String familyName = user.getFamilyName();

        if (givenName == null && familyName == null) {
            return user.getEmail();
        }

        if (givenName == null) {
            return familyName;
        }

        if (familyName == null) {
            return givenName;
        }

        return givenName + " " + familyName;
    }

    /**
     * Citește feed-ul din Redis.
     */
    private FeedResponseDto getFromCache(
            UUID userId,
            int limit
    ) {
        String key = buildCacheKey(
                userId,
                limit
        );

        String json = redisTemplate
                .opsForValue()
                .get(key);

        if (json == null) {
            return null;
        }

        try {

            return objectMapper.readValue(
                    json,
                    FeedResponseDto.class
            );

        } catch (JsonProcessingException e) {

            redisTemplate.delete(key);

            return null;
        }
    }

    /**
     * Salvează feed-ul în Redis.
     */
    private void saveToCache(
            UUID userId,
            int limit,
            FeedResponseDto response
    ) {
        try {

            String json =
                    objectMapper.writeValueAsString(
                            response
                    );

            redisTemplate
                    .opsForValue()
                    .set(
                            buildCacheKey(
                                    userId,
                                    limit
                            ),
                            json,
                            CACHE_TTL
                    );

        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                    "Could not serialize feed for Redis",
                    e
            );
        }
    }

    /**
     * Șterge toate cache-urile feed-ului.
     *
     * Este publică deoarece este apelată și de
     * PostCommentService atunci când se adaugă un comentariu.
     */
    public void clearFeedCache() {

        var keys = redisTemplate.keys(
                FEED_CACHE_PREFIX + "*"
        );

        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * Construiește cheia Redis.
     */
    private String buildCacheKey(
            UUID userId,
            int limit
    ) {
        return FEED_CACHE_PREFIX
                + userId
                + ":"
                + limit;
    }
}