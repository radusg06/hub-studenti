import { redirect } from "next/navigation";
import { createClient } from "@/lib/supabase/server";
import ProfileView from "@/components/Profile/ProfileView";
import AcademicInfo from "@/components/Profile/AcademicInfo";
import ActivitySection from "@/components/Profile/ActivitySection";
import AccountSettings from "@/components/Profile/AccountSettings";

export const metadata = {
    title: "Profile — UNIverse",
};

export default async function ProfilePage() {
    const supabase = await createClient();
    const {
        data: { user },
    } = await supabase.auth.getUser();

    if (!user) {
        redirect("/login");
    }

    const { data: profile } = await supabase
        .from("profiles")
        .select(
            "full_name, username, bio, city, account_type, avatar_url, university, faculty, program, study_year, interests, email_notifications, private_profile"
        )
        .eq("id", user.id)
        .single();

    return (
        <>
            <ProfileView user={user} profile={profile} />
            <div style={{ maxWidth: "560px", margin: "0 auto", padding: "20px 24px 100px" }}>
                <AcademicInfo userId={user.id} profile={profile} />
                <ActivitySection userId={user.id} />
                <AccountSettings userId={user.id} email={user.email} profile={profile} />
            </div>
        </>
    );
}