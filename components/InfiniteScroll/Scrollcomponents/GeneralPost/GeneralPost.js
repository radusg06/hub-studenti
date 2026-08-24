import styles from "../FeedCard.module.css";

export const GeneralPost = ({ item, onLike, onSave }) => {
    const initial = (item.authorName || "?").charAt(0).toUpperCase();

    return (
        <div className={styles.card} style={{ flexDirection: "column" }}>
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start" }}>
                <div className={styles.authorRow}>
                    <span className={styles.authorAvatar}>{initial}</span>
                    <div className={styles.authorInfo}>
                        <span className={styles.authorName}>{item.authorName}</span>
                        {item.authorUniversity && (
                            <span className={styles.authorMeta}>{item.authorUniversity}</span>
                        )}
                    </div>
                </div>
                <button
                    type="button"
                    className={`${styles.saveBtn} ${item.saved ? styles.saveBtnActive : ""}`}
                    onClick={() => onSave?.(item.id)}
                    aria-label={item.saved ? "Unsave post" : "Save post"}
                >
                    <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                        <path d="M6 3h12a1 1 0 0 1 1 1v17l-7-4-7 4V4a1 1 0 0 1 1-1z" />
                    </svg>
                </button>
            </div>

            <p className={styles.postContent}>{item.content}</p>

            <div className={styles.actionsRow}>
                <button
                    type="button"
                    className={`${styles.actionBtn} ${item.liked ? styles.actionBtnActive : ""}`}
                    onClick={() => onLike?.(item.id)}
                >
                    <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                        <path d="M20.8 4.6a5.5 5.5 0 0 0-7.8 0L12 5.6l-1-1a5.5 5.5 0 0 0-7.8 7.8l1 1L12 21l7.8-7.6 1-1a5.5 5.5 0 0 0 0-7.8z" />
                    </svg>
                    {item.likeCount ?? 0}
                </button>
                <button type="button" className={styles.actionBtn}>
                    <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                        <path d="M21 11.5a8.4 8.4 0 0 1-8.9 8.4A9 9 0 0 1 3 11.5 8.4 8.4 0 0 1 12 3a8.4 8.4 0 0 1 9 8.5z" />
                    </svg>
                    {item.commentCount ?? 0}
                </button>
                <button type="button" className={styles.actionBtn}>
                    <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                        <path d="M4 12v7a1 1 0 0 0 1 1h14a1 1 0 0 0 1-1v-7" />
                        <path d="M16 6l-4-4-4 4" />
                        <path d="M12 2v14" />
                    </svg>
                    Share
                </button>
            </div>
        </div>
    );
};