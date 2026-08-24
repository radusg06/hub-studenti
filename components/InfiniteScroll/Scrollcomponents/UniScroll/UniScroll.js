import styles from "../FeedCard.module.css";

export const UniScroll = ({ item }) => {
  return (
    <div className={styles.card}>
      <div className={styles.badge}>
        <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
          <path d="M2 9l10-5 10 5-10 5-10-5z" />
          <path d="M6 11.5V17c0 1.5 3 3 6 3s6-1.5 6-3v-5.5" />
        </svg>
      </div>
      <div className={styles.body}>
        <div className={styles.topRow}>
          <span className={styles.name}>{item.name}</span>
          <span className={styles.tag}>University</span>
        </div>
        {item.description && (
          <p className={styles.description}>{item.description}</p>
        )}
        {item.rating && (
          <div className={styles.metaRow}>
            <span>⭐ <strong>{item.rating}</strong> / 5</span>
          </div>
        )}
      </div>
    </div>
  );
};
