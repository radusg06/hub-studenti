import styles from "./FeatureCard.module.css";

export default function FeatureCard({ id, icon, title, description }) {
  return (
    <div className={styles.card} id={id}>
      <div className={styles.icon}>{icon}</div>
      <h3>{title}</h3>
      <p>{description}</p>
    </div>
  );
}
