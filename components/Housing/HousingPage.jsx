"use client";

import { useMemo, useState } from "react";
import dynamic from "next/dynamic";
import HousingCard from "./HousingCard";
import CreateListingModal from "./CreateListingModal";
import { distanceKm } from "@/lib/geo";
import styles from "./HousingPage.module.css";

const HousingMap = dynamic(() => import("./HousingMap"), { ssr: false });

export default function HousingPage({ listings, userId }) {
    const [search, setSearch] = useState("");
    const [minPrice, setMinPrice] = useState("");
    const [maxPrice, setMaxPrice] = useState("");
    const [modalOpen, setModalOpen] = useState(false);
    const [mapOpen, setMapOpen] = useState(false);
    const [center, setCenter] = useState(null); // { lat, lng } | null
    const [radius, setRadius] = useState(5); // km

    const quickLocations = useMemo(() => {
        const unique = [];
        for (const l of listings) {
            const loc = (l.location || "").split(",")[0].trim();
            if (loc && !unique.includes(loc)) unique.push(loc);
            if (unique.length >= 4) break;
        }
        return unique;
    }, [listings]);

    const filtered = useMemo(() => {
        return listings.filter((l) => {
            const searchLower = search.trim().toLowerCase();
            if (searchLower) {
                const haystack = `${l.name || ""} ${l.location || ""}`.toLowerCase();
                if (!haystack.includes(searchLower)) return false;
            }
            const price = parseFloat(l.price);
            if (minPrice && (isNaN(price) || price < parseFloat(minPrice))) return false;
            if (maxPrice && (isNaN(price) || price > parseFloat(maxPrice))) return false;

            if (center) {
                if (!l.lat || !l.lng) return false; // no pin -> can't verify it's in range
                const d = distanceKm(center.lat, center.lng, l.lat, l.lng);
                if (d > radius) return false;
            }
            return true;
        });
    }, [listings, search, minPrice, maxPrice, center, radius]);

    return (
        <div className={styles.wrap}>
            <div className={styles.headerTop}>
                <div>
                    <h1 className={styles.title}>Housing</h1>
                    <p className={styles.tagline}>Find your place near campus.</p>
                </div>
                <button
                    type="button"
                    className={`btn btn-solid ${styles.listBtn}`}
                    onClick={() => setModalOpen(true)}
                >
                    + List a place
                </button>
            </div>

            <div className={styles.searchBar}>
                <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                    <circle cx="11" cy="11" r="7" />
                    <path d="M21 21l-4.3-4.3" />
                </svg>
                <input
                    type="text"
                    placeholder="Search city, university or neighborhood"
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                />
            </div>

            {quickLocations.length > 0 && (
                <div className={styles.chipRow}>
                    {quickLocations.map((loc) => (
                        <button
                            key={loc}
                            type="button"
                            className={styles.locationChip}
                            onClick={() => setSearch(loc)}
                        >
                            📍 {loc}
                        </button>
                    ))}
                </div>
            )}

            <div className={styles.filters}>
                <div className={styles.priceFilter}>
                    <span>Price</span>
                    <input
                        type="number"
                        placeholder="Min €"
                        value={minPrice}
                        onChange={(e) => setMinPrice(e.target.value)}
                    />
                    <span>–</span>
                    <input
                        type="number"
                        placeholder="Max €"
                        value={maxPrice}
                        onChange={(e) => setMaxPrice(e.target.value)}
                    />
                </div>

                <button
                    type="button"
                    className={`btn btn-outline ${styles.mapToggle}`}
                    onClick={() => setMapOpen((v) => !v)}
                >
                    🗺️ {mapOpen ? "Hide map" : "Map view"}
                </button>

                {(search || minPrice || maxPrice || center) && (
                    <button
                        type="button"
                        className={styles.clearBtn}
                        onClick={() => {
                            setSearch("");
                            setMinPrice("");
                            setMaxPrice("");
                            setCenter(null);
                        }}
                    >
                        Clear filters
                    </button>
                )}
            </div>

            {mapOpen && (
                <>
                    <HousingMap
                        listings={listings}
                        center={center}
                        radius={radius}
                        onSetCenter={(lat, lng) => setCenter({ lat, lng })}
                    />
                    {center && (
                        <div className={styles.radiusRow}>
                            <span>Within {radius} km</span>
                            <input
                                type="range"
                                min="1"
                                max="15"
                                value={radius}
                                onChange={(e) => setRadius(Number(e.target.value))}
                            />
                            <button
                                type="button"
                                className={styles.clearBtn}
                                onClick={() => setCenter(null)}
                            >
                                Clear area
                            </button>
                        </div>
                    )}
                </>
            )}

            <p className={styles.resultsCount}>{filtered.length} places found</p>

            {filtered.length === 0 ? (
                <p className={styles.empty}>No listings match your search yet.</p>
            ) : (
                <div className={styles.grid}>
                    {filtered.map((listing) => (
                        <HousingCard key={listing.id} listing={listing} userId={userId} />
                    ))}
                </div>
            )}

            {modalOpen && (
                <CreateListingModal userId={userId} onClose={() => setModalOpen(false)} />
            )}
        </div>
    );
}