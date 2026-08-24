import { createClient } from "@/lib/supabase/server";
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

    return <StudentFeed authorName={profile?.full_name} />;
}