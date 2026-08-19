import StarsBackground from "@/components/StarsBackground/StarsBackground";
import Navbar from "@/components/Navbar/Navbar";
import Footer from "@/components/Footer/Footer";
import styles from "./DashboardShell.module.css";

export default function DashboardShell({ children }) {
  return (
    <>
      <StarsBackground />
      <Navbar />
      <main className={styles.main}>{children}</main>
      <Footer />
    </>
  );
}
