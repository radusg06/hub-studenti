import Link from "next/link";
import styles from "./Hero.module.css";

export default function Hero() {
  return (
    <section className={styles.hero}>
      <h1>
        <span className={styles.dim}>Your</span> student{" "}
        <span className={styles.light}>life</span> starts here
      </h1>
      <p>
        Discover universities, find accommodation, connect with students,
        explore events, and build your academic journey—all on one platform.
      </p>
      <div className={styles.heroActions}>
        <Link href="/signup" className="btn btn-solid btn-lg">Create Account</Link>
        <a href="#pricing" className="btn btn-outline btn-lg">Discover Plans &#9656;</a>
      </div>
      <div className={styles.trust}>
        <div className={styles.avatars}>
          <img src="/assets/avatar3.jpg" alt="" />
          <img src="/assets/avatar2.jpg" alt="" />
          <img src="/assets/avatar1.webp" alt="" />
        </div>
        <span>Trusted by students from <strong>120+</strong> universities.</span>
      </div>
    </section>
  );
}
