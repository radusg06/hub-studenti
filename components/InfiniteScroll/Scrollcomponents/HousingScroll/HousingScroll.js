import Link from "next/link";
import styles from "../FeedCard.module.css";

export const HousingScroll = ({ item, onSave }) => {
    return (
        <div className={styles.card} style={{ flexDirection: "column" }}>
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start" }}>
                <div className={styles.topRow} style={{ flex: 1 }}>
                    <span className={styles.name}>🏠 {item.name}</span>
                    <span className={styles.tag}>Housing</span>
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

            {item.photos?.[0] ? (
                <img src={item.photos[0]} alt="" className={styles.thumbnailImg} />
            ) : (
                <div className={styles.imagePlaceholder}>🏠</div>
            )}

            <div className={styles.metaRow}>
                {item.price && <span className={styles.price}>€{item.price}/month</span>}
                {item.location && <span>{item.location}</span>}
            </div>
            {item.roommates && (
                <p className={styles.description}>{item.roommates} students already living there</p>
            )}

            <Link href={`/housing/${item.id}`} className={styles.ctaBtn}>
                View accommodation
            </Link>
        </div>
    );
};