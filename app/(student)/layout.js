import { redirect } from "next/navigation";
import { createClient } from "@/lib/supabase/server";
import Sidebar from "@/components/Sidebar/Sidebar";
import styles from "./student-layout.module.css";

export default async function StudentLayout({ children }) {
  const supabase = await createClient();
  const {
    data: { user },
  } = await supabase.auth.getUser();

  if (!user) {
    redirect("/login");
  }

  const { data: profile } = await supabase
      .from("profiles")
      .select("full_name, account_type, avatar_url")
      .eq("id", user.id)
      .single();

  if (profile && profile.account_type !== "student") {
    redirect(
        profile.account_type === "company"
            ? "/company/dashboard"
            : "/university/dashboard"
    );
  }

  return (
      <div className={styles.shell}>
        <Sidebar displayName={profile?.full_name} email={user.email} avatarUrl={profile?.avatar_url} />
        <div className={styles.content}>{children}</div>
      </div>
  );
}