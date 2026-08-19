"use client";

import { useState, useRef, useEffect } from "react";
import Link from "next/link";
import styles from "./Navbar.module.css";

export default function Navbar() {
  const [open, setOpen] = useState(false);
  const navRef = useRef(null);
  const toggleRef = useRef(null);

  useEffect(() => {
    const onDocClick = (e) => {
      if (
        navRef.current &&
        !navRef.current.contains(e.target) &&
        toggleRef.current &&
        !toggleRef.current.contains(e.target)
      ) {
        setOpen(false);
      }
    };
    const onKeyDown = (e) => {
      if (e.key === "Escape") setOpen(false);
    };
    document.addEventListener("click", onDocClick);
    document.addEventListener("keydown", onKeyDown);
    return () => {
      document.removeEventListener("click", onDocClick);
      document.removeEventListener("keydown", onKeyDown);
    };
  }, []);

  const closeMenu = () => setOpen(false);

  return (
    <header id="top" className={styles.header}>
      <div className={styles.logo}>
        <img src="/assets/logo.png" alt="UNIverse" className={styles.logoImg} />
      </div>

      <nav
        className={`${styles.links} ${open ? styles.open : ""}`}
        id="nav-links"
        ref={navRef}
      >
        <a href="#universities" onClick={closeMenu}>Universities</a>
        <a href="#marketplace" onClick={closeMenu}>Marketplace</a>
        <a href="#jobs" onClick={closeMenu}>Jobs</a>
        <a href="#housing" onClick={closeMenu}>Housing</a>
        <a href="#pricing" onClick={closeMenu}>Pricing</a>
      </nav>

      <div className={styles.navActions}>
        <Link href="/login" className="btn btn-outline">Log in</Link>
        <Link href="/signup" className="btn btn-solid">Sign up</Link>
        <button
          className={styles.menuToggle}
          ref={toggleRef}
          aria-label="Toggle menu"
          aria-expanded={open}
          aria-controls="nav-links"
          onClick={(e) => {
            e.stopPropagation();
            setOpen((v) => !v);
          }}
        >
          <svg
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinecap="round"
          >
            <line x1="4" y1="7" x2="20" y2="7" />
            <line x1="4" y1="12" x2="20" y2="12" />
            <line x1="4" y1="17" x2="20" y2="17" />
          </svg>
        </button>
      </div>
    </header>
  );
}
