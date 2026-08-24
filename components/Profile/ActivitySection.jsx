"use client";

import { useState, useEffect } from "react";
import { createClient } from "@/lib/supabase/client";
import {
    fetchUserPosts,
    fetchLikedPosts,
    fetchSavedPosts,
    fetchLikedPostIds,
    fetchSavedPostIds,
    mapPostRow,
    toggleLike,
    toggleSave,
} from "@/lib/posts";
import { UniScroll } from "@/components/InfiniteScroll/Scrollcomponents/UniScroll/UniScroll";
import { JobScroll } from "@/components/InfiniteScroll/Scrollcomponents/JobScroll/JobScroll";
import { MarketplaceScroll } from "@/components/InfiniteScroll/Scrollcomponents/MarketplaceScroll/MarketplaceScroll";
import { HousingScroll } from "@/components/InfiniteScroll/Scrollcomponents/HousingScroll/HousingScroll";
import { GeneralPost } from "@/components/InfiniteScroll/Scrollcomponents/GeneralPost/GeneralPost";
import styles from "./ActivitySection.module.css";

const TABS = [
    { key: "posts", label: "Posts" },
    { key: "saved", label: "Saved" },
    { key: "liked", label: "Liked" },
];

function renderItem(item, handlers) {
    switch (item.type) {
        case "GeneralPost":
            return <GeneralPost item={item} onLike={handlers.onLike} onSave={handlers.onSave} />;
        case "UniScroll":
            return <UniScroll item={item} onSave={handlers.onSave} />;
        case "JobScroll":
            return <JobScroll item={item} onSave={handlers.onSave} />;
        case "MarketplaceScroll":
            return <MarketplaceScroll item={item} onSave={handlers.onSave} />;
        case "HousingScroll":
            return <HousingScroll item={item} onSave={handlers.onSave} />;
        default:
            return null;
    }
}

export default function ActivitySection({ userId }) {
    const [counts, setCounts] = useState({ posts: 0, saved: 0, liked: 0 });
    const [activeTab, setActiveTab] = useState("posts");
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function loadCounts() {
            const supabase = createClient();
            const [{ count: postsCount }, { count: savedCount }, { count: likedCount }] =
                await Promise.all([
                    supabase.from("posts").select("id", { count: "exact", head: true }).eq("author_id", userId),
                    supabase.from("post_saves").select("post_id", { count: "exact", head: true }).eq("user_id", userId),
                    supabase.from("post_likes").select("post_id", { count: "exact", head: true }).eq("user_id", userId),
                ]);
            setCounts({ posts: postsCount ?? 0, saved: savedCount ?? 0, liked: likedCount ?? 0 });
        }
        loadCounts();
    }, [userId]);

    useEffect(() => {
        async function loadTab() {
            setLoading(true);
            const supabase = createClient();
            const likedIds = await fetchLikedPostIds(supabase, userId);
            const savedIds = await fetchSavedPostIds(supabase, userId);

            let rows = [];
            if (activeTab === "posts") rows = await fetchUserPosts(supabase, userId);
            if (activeTab === "saved") rows = await fetchSavedPosts(supabase, userId);
            if (activeTab === "liked") rows = await fetchLikedPosts(supabase, userId);

            const mapped = rows.map((row) =>
                mapPostRow(row, { liked: likedIds.has(row.id), saved: savedIds.has(row.id) })
            );
            setItems(mapped);
            setLoading(false);
        }
        loadTab();
    }, [activeTab, userId]);

    async function handleLike(id) {
        const item = items.find((it) => it.id === id);
        if (!item) return;
        const wasLiked = item.liked;
        const prevCount = item.likeCount;

        setItems((prev) =>
            prev.map((it) =>
                it.id === id
                    ? { ...it, liked: !wasLiked, likeCount: prevCount + (wasLiked ? -1 : 1) }
                    : it
            )
        );

        const supabase = createClient();
        await toggleLike(supabase, id, userId, wasLiked);
        setCounts((c) => ({ ...c, liked: c.liked + (wasLiked ? -1 : 1) }));
    }

    async function handleSave(id) {
        const item = items.find((it) => it.id === id);
        if (!item) return;
        const wasSaved = item.saved;

        setItems((prev) =>
            activeTab === "saved" && wasSaved
                ? prev.filter((it) => it.id !== id)
                : prev.map((it) => (it.id === id ? { ...it, saved: !wasSaved } : it))
        );

        const supabase = createClient();
        await toggleSave(supabase, id, userId, wasSaved);
        setCounts((c) => ({ ...c, saved: c.saved + (wasSaved ? -1 : 1) }));
    }

    const handlers = { onLike: handleLike, onSave: handleSave };

    return (
        <div className={styles.card}>
            <h2 className={styles.title}>My Activity</h2>

            <div className={styles.statsRow}>
                <div className={styles.stat}>
                    <span className={styles.statNumber}>{counts.posts}</span>
                    <span className={styles.statLabel}>Posts</span>
                </div>
                <div className={styles.stat}>
                    <span className={styles.statNumber}>{counts.liked}</span>
                    <span className={styles.statLabel}>Liked</span>
                </div>
                <div className={styles.stat}>
                    <span className={styles.statNumber}>{counts.saved}</span>
                    <span className={styles.statLabel}>Saved</span>
                </div>
            </div>

            <div className={styles.tabs} role="tablist">
                {TABS.map((tab) => (
                    <button
                        key={tab.key}
                        type="button"
                        role="tab"
                        aria-selected={activeTab === tab.key}
                        className={`${styles.tab} ${activeTab === tab.key ? styles.tabActive : ""}`}
                        onClick={() => setActiveTab(tab.key)}
                    >
                        {tab.label}
                    </button>
                ))}
            </div>

            <div className={styles.list}>
                {loading && <p className={styles.empty}>Loading…</p>}
                {!loading && items.length === 0 && (
                    <p className={styles.empty}>Nothing here yet.</p>
                )}
                {!loading &&
                    items.map((item) => (
                        <div key={item.id} className={styles.itemWrap}>
                            {renderItem(item, handlers)}
                        </div>
                    ))}
            </div>
        </div>
    );
}