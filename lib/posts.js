const PAGE_SIZE = 4;

export async function fetchPosts(supabase, { offset = 0, limit = PAGE_SIZE } = {}) {
    const { data, error } = await supabase
        .from("posts")
        .select("*, profiles!posts_author_id_fkey(full_name)")
        .order("created_at", { ascending: false })
        .range(offset, offset + limit - 1);

    if (error) throw error;
    return data || [];
}

export async function fetchPostsByType(supabase, type) {
    const { data, error } = await supabase
        .from("posts")
        .select("*, profiles!posts_author_id_fkey(full_name)")
        .eq("type", type)
        .order("created_at", { ascending: false });
    if (error) throw error;
    return data || [];
}

export async function fetchPostById(supabase, id) {
    const { data, error } = await supabase
        .from("posts")
        .select("*, profiles!posts_author_id_fkey(full_name)")
        .eq("id", id)
        .single();
    if (error) throw error;
    return data;
}

export async function fetchLikedPostIds(supabase, userId) {
    if (!userId) return new Set();
    const { data } = await supabase
        .from("post_likes")
        .select("post_id")
        .eq("user_id", userId);
    return new Set((data || []).map((r) => r.post_id));
}

export async function fetchSavedPostIds(supabase, userId) {
    if (!userId) return new Set();
    const { data } = await supabase
        .from("post_saves")
        .select("post_id")
        .eq("user_id", userId);
    return new Set((data || []).map((r) => r.post_id));
}

export async function createPost(supabase, authorId, dbType, payload) {
    const { data, error } = await supabase
        .from("posts")
        .insert({ author_id: authorId, type: dbType, ...payload })
        .select("*, profiles!posts_author_id_fkey(full_name)")
        .single();
    if (error) throw error;
    return data;
}

export async function toggleLike(supabase, postId, userId, currentlyLiked) {
    if (currentlyLiked) {
        await supabase
            .from("post_likes")
            .delete()
            .eq("post_id", postId)
            .eq("user_id", userId);
    } else {
        await supabase.from("post_likes").insert({ post_id: postId, user_id: userId });
    }
}

export async function toggleSave(supabase, postId, userId, currentlySaved) {
    if (currentlySaved) {
        await supabase
            .from("post_saves")
            .delete()
            .eq("post_id", postId)
            .eq("user_id", userId);
    } else {
        await supabase.from("post_saves").insert({ post_id: postId, user_id: userId });
    }
}

// ---------- Activity tab (UC21 / UC22) ----------

export async function fetchUserPosts(supabase, userId) {
    const { data, error } = await supabase
        .from("posts")
        .select("*, profiles!posts_author_id_fkey(full_name)")
        .eq("author_id", userId)
        .order("created_at", { ascending: false });
    if (error) throw error;
    return data || [];
}

export async function fetchLikedPosts(supabase, userId) {
    const { data, error } = await supabase
        .from("post_likes")
        .select("posts(*, profiles!posts_author_id_fkey(full_name))")
        .eq("user_id", userId)
        .order("created_at", { ascending: false });
    if (error) throw error;
    return (data || []).map((row) => row.posts).filter(Boolean);
}

export async function fetchSavedPosts(supabase, userId) {
    const { data, error } = await supabase
        .from("post_saves")
        .select("posts(*, profiles!posts_author_id_fkey(full_name))")
        .eq("user_id", userId)
        .order("created_at", { ascending: false });
    if (error) throw error;
    return (data || []).map((row) => row.posts).filter(Boolean);
}

export async function fetchActivityCounts(supabase, userId) {
    const [posts, likes, saves] = await Promise.all([
        supabase.from("posts").select("id", { count: "exact", head: true }).eq("author_id", userId),
        supabase.from("post_likes").select("post_id", { count: "exact", head: true }).eq("user_id", userId),
        supabase.from("post_saves").select("post_id", { count: "exact", head: true }).eq("user_id", userId),
    ]);
    return {
        posts: posts.count ?? 0,
        likes: likes.count ?? 0,
        saved: saves.count ?? 0,
    };
}

// Maps a DB row (snake_case + author_id) into the shape the feed
// card components already expect (camelCase).
export function mapPostRow(row, { liked = false, saved = false } = {}) {
    return {
        id: row.id,
        type: row.type,
        content: row.content,
        name: row.name,
        description: row.description,
        price: row.price,
        location: row.location,
        condition: row.condition,
        roommates: row.roommates,
        company: row.company,
        availableSpots: row.available_spots,
        amenities: row.amenities || [],
        photos: row.photos || [],
        lat: row.lat,
        lng: row.lng,
        likeCount: row.like_count ?? 0,
        commentCount: row.comment_count ?? 0,
        authorName: row.profiles?.full_name || "Student",
        authorUniversity: "",
        liked,
        saved,
    };
}

// Maps the UI post-type label (from CreatePost's selector) + its
// form payload into what the "posts" table expects.
export function buildDbPayload(postType, payload) {
    const typeMap = {
        General: "GeneralPost",
        University: "UniScroll",
        Housing: "HousingScroll",
        Marketplace: "MarketplaceScroll",
        Jobs: "JobScroll",
    };

    const dbPayload = {};
    if (payload.content !== undefined) dbPayload.content = payload.content;
    if (payload.name !== undefined) dbPayload.name = payload.name;
    if (payload.description !== undefined) dbPayload.description = payload.description;
    if (payload.price !== undefined) dbPayload.price = payload.price;
    if (payload.location !== undefined) dbPayload.location = payload.location;
    if (payload.condition !== undefined) dbPayload.condition = payload.condition;
    if (payload.roommates !== undefined) dbPayload.roommates = payload.roommates;
    if (payload.company !== undefined) dbPayload.company = payload.company;
    if (payload.availableSpots !== undefined) dbPayload.available_spots = payload.availableSpots;
    if (payload.amenities !== undefined) {
        dbPayload.amenities = payload.amenities
            .split(",")
            .map((a) => a.trim())
            .filter(Boolean);
    }
    if (payload.photos !== undefined) dbPayload.photos = payload.photos;
    if (payload.lat !== undefined) dbPayload.lat = payload.lat;
    if (payload.lng !== undefined) dbPayload.lng = payload.lng;

    return { dbType: typeMap[postType], dbPayload };
}