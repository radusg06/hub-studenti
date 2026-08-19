import FeatureCard from "./FeatureCard";
import styles from "./Features.module.css";

const features = [
  {
    id: "marketplace",
    title: "Marketplace",
    description:
      "Sell your textbooks, bikes and gear – or grab a deal from students on your campus.",
    icon: (
      <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
        <path d="M3 9l1-5h16l1 5" />
        <path d="M3 9a2 2 0 0 0 4 0 2 2 0 0 0 4 0 2 2 0 0 0 4 0 2 2 0 0 0 4 0" />
        <path d="M5 9v10h14V9" />
        <path d="M9 21v-6h6v6" />
      </svg>
    ),
  },
  {
    id: "housing",
    title: "Accommodation",
    description:
      "Find rooms, flats and flatmates near your university – make lifetime connections.",
    icon: (
      <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
        <path d="M3 11l9-7 9 7" />
        <path d="M5 10v10h14V10" />
        <path d="M9 20v-6h6v6" />
      </svg>
    ),
  },
  {
    id: "jobs",
    title: "Student jobs",
    description:
      "Part-time roles, internships and campus gigs that fit around your timetable.",
    icon: (
      <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
        <rect x="3" y="7" width="18" height="13" rx="2" />
        <path d="M8 7V5a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" />
        <circle cx="12" cy="13" r="2.2" />
      </svg>
    ),
  },
  {
    id: "universities",
    title: "Universities",
    description:
      "Browse universities, compare courses and see what student life really is.",
    icon: (
      <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
        <path d="M2 9l10-5 10 5-10 5-10-5z" />
        <path d="M6 11.5V17c0 1.5 3 3 6 3s6-1.5 6-3v-5.5" />
        <path d="M22 9v6" />
      </svg>
    ),
  },
];

export default function Features() {
  return (
    <section className={styles.features}>
      {features.map((f) => (
        <FeatureCard key={f.id} id={f.id} icon={f.icon} title={f.title} description={f.description} />
      ))}
    </section>
  );
}
