package ro.hubstudentesc.service.socialmedia;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.socialmedia.FeedResponseDto;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedCacheService {

    private static final String KEY_PREFIX = "feed:first:";
    private static final Duration CACHE_TTL = Duration.ofSeconds(60);

    private final RedisTemplate<String, Object> redisTemplate;

    public FeedResponseDto get(
            UUID userId,
            int limit
    ) {
        String key = buildKey(userId, limit);

        Object value = redisTemplate.opsForValue().get(key);

        if (value instanceof FeedResponseDto feed) {
            return feed;
        }

        return null;
    }

    public void put(
            UUID userId,
            int limit,
            FeedResponseDto feed
    ) {
        String key = buildKey(userId, limit);

        redisTemplate.opsForValue().set(
                key,
                feed,
                CACHE_TTL
        );
    }

    public void clear() {
        var keys = redisTemplate.keys(KEY_PREFIX + "*");

        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    private String buildKey(
            UUID userId,
            int limit
    ) {
        return KEY_PREFIX + userId + ":" + limit;
    }
}