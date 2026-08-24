"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import styles from "./Sidebar.module.css";

const NAV_ITEMS = [
    {
        href: "/dashboard",
        label: "Feed",
        icon: (
            <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <path d="M3 11l9-7 9 7" />
                <path d="M5 10v10h14V10" />
                <path d="M9 20v-6h6v6" />
            </svg>
        ),
    },
    {
        href: "/marketplace",
        label: "Marketplace",
        icon: (
            <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <path d="M3 9l1-5h16l1 5" />
                <path d="M3 9a2 2 0 0 0 4 0 2 2 0 0 0 4 0 2 2 0 0 0 4 0 2 2 0 0 0 4 0" />
                <path d="M5 9v10h14V9" />
            </svg>
        ),
    },
    {
        href: "/jobs",
        label: "Jobs",
        icon: (
            <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <rect x="3" y="7" width="18" height="13" rx="2" />
                <path d="M8 7V5a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" />
            </svg>
        ),
    },
    {
        href: "/housing",
        label: "Housing",
        icon: (
            <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <path d="M3 11l9-7 9 7" />
                <path d="M5 10v10h14V10" />
                <path d="M9 20v-6h6v6" />
            </svg>
        ),
    },
    {
        href: "/universities",
        label: "Universities",
        icon: (
            <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <path d="M2 9l10-5 10 5-10 5-10-5z" />
                <path d="M6 11.5V17c0 1.5 3 3 6 3s6-1.5 6-3v-5.5" />
            </svg>
        ),
    },
];

export default function Sidebar({ displayName, email, avatarUrl }) {
    const pathname = usePathname();

    const initial = (displayName || email || "?").charAt(0).toUpperCase();
    const handle = email ? email.split("@")[0] : "user";

    return (
        <aside className={styles.sidebar}>
            <Link href="/dashboard" className={styles.logo}>
                <img src="/assets/logo.png" alt="UNIverse" className={styles.logoImg} />
            </Link>

            <nav className={styles.nav}>
                {NAV_ITEMS.map((item) => {
                    const active = pathname === item.href;
                    return (
                        <Link
                            key={item.href}
                            href={item.href}
                            className={`${styles.navItem} ${active ? styles.navItemActive : ""}`}
                        >
                            <span className={styles.navIcon}>{item.icon}</span>
                            <span className={styles.navLabel}>{item.label}</span>
                        </Link>
                    );
                })}
            </nav>

            <Link href="/profile" className={styles.profileCard}>
                {avatarUrl ? (
                    <img src={avatarUrl} alt="" className={styles.avatarImg} />
                ) : (
                    <span className={styles.avatar}>{initial}</span>
                )}
                <span className={styles.profileText}>
          <span className={styles.profileName}>{displayName || "Account"}</span>
          <span className={styles.profileHandle}>@{handle}</span>
        </span>
            </Link>
        </aside>
    );
}