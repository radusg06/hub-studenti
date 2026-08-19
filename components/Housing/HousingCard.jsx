"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { createClient } from "@/lib/supabase/client";
import { toggleSave } from "@/lib/posts";
import styles from "./HousingCard.module.css";

export default function HousingCard({ listing, userId }) {
    const router = useRouter();
    const [saved, setSaved] = useState(listing.saved);

    async function handleSave(e) {
        e.stopPropagation();
        const wasSaved = saved;
        setSaved(!wasSaved);
        const supabase = createClient();
        await toggleSave(supabase, listing.id, userId, wasSaved);
    }

    return (
        <div className={styles.card} onClick={() => router.push(`/housing/${listing.id}`)}>
            <div className={styles.photo}>
                {listing.photos?.[0] ? (
                    <img src={listing.photos[0]} alt="" className={styles.photoImg} />
                ) : (
                    <span className={styles.photoPlaceholder}>🏠</span>
                )}
                <button
                    type="button"
                    className={`${styles.saveBtn} ${saved ? styles.saveBtnActive : ""}`}
                    onClick={handleSave}
                    aria-label={saved ? "Unsave listing" : "Save listing"}
                >
                    <svg viewBox="0 0 24 24" fill={saved ? "currentColor" : "none"} strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                        <path d="M20.8 4.6a5.5 5.5 0 0 0-7.8 0L12 5.6l-1-1a5.5 5.5 0 0 0-7.8 7.8l1 1L12 21l7.8-7.6 1-1a5.5 5.5 0 0 0 0-7.8z" />
                    </svg>
                </button>
            </div>

            <div className={styles.body}>
                <div className={styles.priceRow}>
                    <span className={styles.price}>€{listing.price || "—"}</span>
                    <span className={styles.perMonth}>/ month</span>
                </div>
                {listing.name && <p className={styles.name}>{listing.name}</p>}

                <div className={styles.metaRow}>
                    {listing.location && (
                        <span>📍 {listing.location}</span>
                    )}
                    {listing.roommates && (
                        <span>👥 {listing.roommates} roommates</span>
                    )}
                </div>

                <div className={styles.footer}>
                    {listing.authorName} • verified student
                </div>
            </div>
        </div>
    );
}