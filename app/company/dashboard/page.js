import { redirect } from "next/navigation";
import { createClient } from "@/lib/supabase/server";
import DashboardShell from "@/components/DashboardShell/DashboardShell";
import styles from "@/components/DashboardShell/DashboardShell.module.css";

export const metadata = {
  title: "Company dashboard — UNIverse",
};

export default async function CompanyDashboardPage() {
  const supabase = await createClient();
  const {
    data: { user },
  } = await supabase.auth.getUser();

  if (!user) {
    redirect("/login");
  }

  const { data: profile } = await supabase
    .from("profiles")
    .select("company_name, account_type")
    .eq("id", user.id)
    .single();

  if (profile && profile.account_type !== "company") {
    redirect(
      profile.account_type === "university" ? "/university/dashboard" : "/dashboard"
    );
  }

  const displayName = profile?.company_name || user.email;

  return (
    <DashboardShell>
      <h1>Welcome, {displayName} 👋</h1>
      <p>
        This is your company dashboard. Job posting and applicant management
        aren&apos;t built yet — this is the landing spot for now.
      </p>

      <div className={styles.quickLinks}>
        <a href="/#jobs" className={styles.linkCard}>
          <h3>Post a job</h3>
          <p>Not built yet — coming soon.</p>
        </a>
        <a href="/#jobs" className={styles.linkCard}>
          <h3>Applicants</h3>
          <p>Review and approve/reject candidates.</p>
        </a>
      </div>

      <p className={styles.notice}>
        Signed in as <strong>{user.email}</strong>
      </p>
    </DashboardShell>
  );
}
