"use client";

import { useState } from "react";
import { createClient } from "@/lib/supabase/client";
import { toggleSave } from "@/lib/posts";
import styles from "./SaveButton.module.css";

export default function SaveButton({ postId, userId, initialSaved }) {
    const [saved, setSaved] = useState(initialSaved);

    async function handleClick() {
        const wasSaved = saved;
        setSaved(!wasSaved);
        const supabase = createClient();
        await toggleSave(supabase, postId, userId, wasSaved);
    }

    return (
        <button
            type="button"
            className={`${styles.btn} ${saved ? styles.btnActive : ""}`}
            onClick={handleClick}
            aria-label={saved ? "Unsave listing" : "Save listing"}
        >
            <svg viewBox="0 0 24 24" fill={saved ? "currentColor" : "none"} strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <path d="M20.8 4.6a5.5 5.5 0 0 0-7.8 0L12 5.6l-1-1a5.5 5.5 0 0 0-7.8 7.8l1 1L12 21l7.8-7.6 1-1a5.5 5.5 0 0 0 0-7.8z" />
            </svg>
        </button>
    );
}