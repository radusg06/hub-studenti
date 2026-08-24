import styles from "../FeedCard.module.css";

export const JobScroll = ({ item, onSave }) => {
    return (
        <div className={styles.card}>
            <div className={styles.badge}>
                <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                    <rect x="3" y="7" width="18" height="13" rx="2" />
                    <path d="M8 7V5a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" />
                </svg>
            </div>
            <div className={styles.body}>
                <div className={styles.topRow}>
                    <span className={styles.name}>{item.name}</span>
                    <span className={styles.tag}>Job</span>
                </div>
                {item.company && (
                    <p className={styles.description}>{item.company}</p>
                )}
                <div className={styles.metaRow}>
                    {item.location && <span>{item.location}</span>}
                    {item.availableSpots && (
                        <span><strong>{item.availableSpots}</strong> spots left</span>
                    )}
                </div>
            </div>
            <button
                type="button"
                className={`${styles.saveBtn} ${item.saved ? styles.saveBtnActive : ""}`}
                onClick={() => onSave?.(item.id)}
                aria-label={item.saved ? "Unsave job" : "Save job"}
            >
                <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                    <path d="M6 3h12a1 1 0 0 1 1 1v17l-7-4-7 4V4a1 1 0 0 1 1-1z" />
                </svg>
            </button>
        </div>
    );
};