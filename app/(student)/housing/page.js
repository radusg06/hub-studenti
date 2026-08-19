import { createClient } from "@/lib/supabase/server";
import { fetchPostsByType, fetchSavedPostIds, mapPostRow } from "@/lib/posts";
import HousingPage from "@/components/Housing/HousingPage";

export const metadata = {
    title: "Housing — UNIverse",
};

export default async function Housing() {
    const supabase = await createClient();
    const {
        data: { user },
    } = await supabase.auth.getUser();

    const rows = await fetchPostsByType(supabase, "HousingScroll");
    const savedIds = await fetchSavedPostIds(supabase, user.id);

    const listings = rows.map((row) =>
        mapPostRow(row, { saved: savedIds.has(row.id) })
    );

    return <HousingPage listings={listings} userId={user.id} />;
}