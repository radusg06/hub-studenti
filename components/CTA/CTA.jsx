import Link from "next/link";
import styles from "./CTA.module.css";

export default function CTA() {
  return (
    <section className={styles.cta} id="pricing">
      <h2 className={`mono ${styles.heading}`}>Ready to launch?</h2>
      <Link href="/signup" className={`btn btn-solid btn-lg ${styles.ctaBtn}`}>
        Create Account
      </Link>
    </section>
  );
}
