"use client";

import InfiniteScroll from "react-infinite-scroll-component";
import { useState } from "react";
import { createClient } from "@/lib/supabase/client";
import {
  fetchPosts,
  createPost,
  toggleLike,
  toggleSave,
  mapPostRow,
  buildDbPayload,
} from "@/lib/posts";
import { UniScroll } from "./Scrollcomponents/UniScroll/UniScroll";
import { JobScroll } from "./Scrollcomponents/JobScroll/JobScroll";
import { MarketplaceScroll } from "./Scrollcomponents/MarketplaceScroll/MarketplaceScroll";
import { HousingScroll } from "./Scrollcomponents/HousingScroll/HousingScroll";
import { GeneralPost } from "./Scrollcomponents/GeneralPost/GeneralPost";
import { CreatePost } from "./CreatePost/CreatePost";
import styles from "./StudentFeed.module.css";

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

export const StudentFeed = ({ authorName, userId, initialItems }) => {
  const [items, setItems] = useState(initialItems || []);
  const [hasMore, setHasMore] = useState((initialItems || []).length > 0);

  const fetchMore = async () => {
    const supabase = createClient();
    const rows = await fetchPosts(supabase, { offset: items.length });
    if (rows.length === 0) {
      setHasMore(false);
      return;
    }

    const ids = rows.map((r) => r.id);
    const { data: likes } = await supabase
        .from("post_likes")
        .select("post_id")
        .eq("user_id", userId)
        .in("post_id", ids);
    const { data: saves } = await supabase
        .from("post_saves")
        .select("post_id")
        .eq("user_id", userId)
        .in("post_id", ids);

    const likedSet = new Set((likes || []).map((r) => r.post_id));
    const savedSet = new Set((saves || []).map((r) => r.post_id));

    const mapped = rows.map((row) =>
        mapPostRow(row, { liked: likedSet.has(row.id), saved: savedSet.has(row.id) })
    );
    setItems((prev) => [...prev, ...mapped]);
  };

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

    try {
      const supabase = createClient();
      await toggleLike(supabase, id, userId, wasLiked);
    } catch (e) {
      // revert on failure
      setItems((prev) =>
          prev.map((it) => (it.id === id ? { ...it, liked: wasLiked, likeCount: prevCount } : it))
      );
    }
  }

  async function handleSave(id) {
    const item = items.find((it) => it.id === id);
    if (!item) return;
    const wasSaved = item.saved;

    setItems((prev) =>
        prev.map((it) => (it.id === id ? { ...it, saved: !wasSaved } : it))
    );

    try {
      const supabase = createClient();
      await toggleSave(supabase, id, userId, wasSaved);
    } catch (e) {
      setItems((prev) =>
          prev.map((it) => (it.id === id ? { ...it, saved: wasSaved } : it))
      );
    }
  }

  async function handlePost(text) {
    const { dbType, dbPayload } = buildDbPayload("General", { content: text });
    const supabase = createClient();
    try {
      const row = await createPost(supabase, userId, dbType, dbPayload);
      const mapped = mapPostRow(row, { liked: false, saved: false });
      setItems((prev) => [mapped, ...prev]);
    } catch (e) {
      console.error("Failed to create post:", e);
    }
  }

  const handlers = { onLike: handleLike, onSave: handleSave };

  return (
      <div className={styles.feedWrap}>
        <h1 className={styles.title}>Your feed</h1>

        <CreatePost onPost={handlePost} authorName={authorName} />

        <InfiniteScroll
            dataLength={items.length}
            next={fetchMore}
            hasMore={hasMore}
            loader={<p className={styles.loader}>Loading more…</p>}
            endMessage={
              <p className={styles.endMessage}>You&apos;re all caught up ✨</p>
            }
            className={styles.scrollColumn}
        >
          {items.map((item) => (
              <div className={styles.itemWrap} key={item.id}>
                {renderItem(item, handlers)}
              </div>
          ))}
        </InfiniteScroll>
      </div>
  );
};