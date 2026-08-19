import styles from "../FeedCard.module.css";

export const MarketplaceScroll = ({ item, onSave }) => {
    return (
        <div className={styles.card} style={{ flexDirection: "column" }}>
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start" }}>
                <div className={styles.topRow} style={{ flex: 1 }}>
                    <span className={styles.name}>🛍 {item.name}</span>
                    <span className={styles.tag}>Marketplace</span>
                </div>
                <button
                    type="button"
                    className={`${styles.saveBtn} ${item.saved ? styles.saveBtnActive : ""}`}
                    onClick={() => onSave?.(item.id)}
                    aria-label={item.saved ? "Unsave listing" : "Save listing"}
                >
                    <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                        <path d="M6 3h12a1 1 0 0 1 1 1v17l-7-4-7 4V4a1 1 0 0 1 1-1z" />
                    </svg>
                </button>
            </div>

            <div className={styles.imagePlaceholder}>🛍️</div>

            <div className={styles.metaRow}>
                {item.price && <span className={styles.price}>€{item.price}</span>}
                {item.location && <span>{item.location}</span>}
                {item.condition && <span>{item.condition}</span>}
            </div>

            <a href="#" className={styles.ctaBtn}>View listing</a>
        </div>
    );
};