"use client";

import { useState } from "react";
import styles from "./CreatePost.module.css";

export const CreatePost = ({ onPost, authorName }) => {
    const [content, setContent] = useState("");
    const initial = (authorName || "?").charAt(0).toUpperCase();

    function handleSubmit(e) {
        e.preventDefault();
        if (!content.trim()) return;
        onPost?.(content.trim());
        setContent("");
    }

    return (
        <form className={styles.card} onSubmit={handleSubmit}>
            <div className={styles.topRow}>
                <span className={styles.avatar}>{initial}</span>
                <textarea
                    className={styles.textarea}
                    placeholder="What's happening?"
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    rows={2}
                />
            </div>
            <div className={styles.bottomRow}>
                <div className={styles.iconGroup}>
                    <button type="button" className={styles.iconBtn} title="Photo (coming soon)" disabled>
                        <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                            <rect x="3" y="5" width="18" height="14" rx="2" />
                            <circle cx="8.5" cy="10" r="1.5" />
                            <path d="M21 15l-5-5-9 9" />
                        </svg>
                        <span>Photo</span>
                    </button>
                    <button type="button" className={styles.iconBtn} title="Location (coming soon)" disabled>
                        <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                            <path d="M12 21s7-6.5 7-11a7 7 0 0 0-14 0c0 4.5 7 11 7 11z" />
                            <circle cx="12" cy="10" r="2.5" />
                        </svg>
                        <span>Location</span>
                    </button>
                </div>
                <button type="submit" className={`btn btn-solid ${styles.postBtn}`} disabled={!content.trim()}>
                    Post →
                </button>
            </div>
        </form>
    );
};