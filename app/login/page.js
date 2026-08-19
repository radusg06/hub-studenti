import { redirect } from "next/navigation";
import { createClient } from "@/lib/supabase/server";
import { getRedirectPath } from "@/lib/getRedirectPath";
import StarsBackground from "@/components/StarsBackground/StarsBackground";
import Navbar from "@/components/Navbar/Navbar";
import Footer from "@/components/Footer/Footer";
import LoginForm from "@/components/Auth/LoginForm";
import styles from "./login.module.css";

export const metadata = {
  title: "Log in — UNIverse",
  description: "Log in to your UNIverse account.",
};

export default async function LoginPage() {
  const supabase = await createClient();
  const {
    data: { user },
  } = await supabase.auth.getUser();

  if (user) {
    redirect(getRedirectPath(user.user_metadata?.account_type));
  }

  return (
    <>
      <StarsBackground />
      <Navbar />
      <main className={styles.main}>
        <LoginForm />
      </main>
      <Footer />
    </>
  );
}
