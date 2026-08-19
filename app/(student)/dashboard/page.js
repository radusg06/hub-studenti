import { createClient } from "@/lib/supabase/server";
import { fetchPosts, fetchLikedPostIds, fetchSavedPostIds, mapPostRow } from "@/lib/posts";
import { StudentFeed } from "@/components/InfiniteScroll/StudentFeed";

export default async function Dashboard() {
    const supabase = await createClient();
    const {
        data: { user },
    } = await supabase.auth.getUser();

    const { data: profile } = await supabase
        .from("profiles")
        .select("full_name")
        .eq("id", user.id)
        .single();

    const rows = await fetchPosts(supabase, { offset: 0 });
    const likedIds = await fetchLikedPostIds(supabase, user.id);
    const savedIds = await fetchSavedPostIds(supabase, user.id);

    const initialItems = rows.map((row) =>
        mapPostRow(row, { liked: likedIds.has(row.id), saved: savedIds.has(row.id) })
    );

    return (
        <StudentFeed
            authorName={profile?.full_name}
            userId={user.id}
            initialItems={initialItems}
        />
    );
}