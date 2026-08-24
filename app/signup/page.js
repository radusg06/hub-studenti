import { redirect } from "next/navigation";
import { createClient } from "@/lib/supabase/server";
import { getRedirectPath } from "@/lib/getRedirectPath";
import StarsBackground from "@/components/StarsBackground/StarsBackground";
import Navbar from "@/components/Navbar/Navbar";
import Footer from "@/components/Footer/Footer";
import SignupForm from "@/components/Auth/SignupForm";
import styles from "./signup.module.css";

export const metadata = {
  title: "Sign up — UNIverse",
  description: "Create your UNIverse account.",
};

export default async function SignupPage() {
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
        <SignupForm />
      </main>
      <Footer />
    </>
  );
}
