"use client";

import { useState } from "react";
import styles from "./PhotoGallery.module.css";

export default function PhotoGallery({ photos }) {
    const [active, setActive] = useState(0);

    if (!photos || photos.length === 0) {
        return (
            <div className={styles.placeholder}>
                <span>🏠</span>
            </div>
        );
    }

    return (
        <div>
            <div className={styles.main}>
                <img src={photos[active]} alt="" />
            </div>
            {photos.length > 1 && (
                <div className={styles.thumbs}>
                    {photos.map((p, i) => (
                        <button
                            key={p}
                            type="button"
                            className={`${styles.thumbBtn} ${i === active ? styles.thumbActive : ""}`}
                            onClick={() => setActive(i)}
                            aria-label={`View photo ${i + 1}`}
                        >
                            <img src={p} alt="" />
                        </button>
                    ))}
                </div>
            )}
        </div>
    );
}