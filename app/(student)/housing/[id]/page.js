import { notFound } from "next/navigation";
import Link from "next/link";
import { createClient } from "@/lib/supabase/server";
import { fetchPostById, fetchSavedPostIds, mapPostRow } from "@/lib/posts";
import SaveButton from "@/components/Housing/SaveButton";
import PhotoGallery from "@/components/Housing/PhotoGallery";
import styles from "./page.module.css";
import {ChatButton} from "@/components/ChatButton/ChatButton";

export default async function HousingDetailPage({ params }) {
    const supabase = await createClient();
    const {
        data: { user },
    } = await supabase.auth.getUser();

    let row;
    try {
        row = await fetchPostById(supabase, params.id);
    } catch {
        notFound();
    }

    if (!row || row.type !== "HousingScroll") {
        notFound();
    }

    const savedIds = await fetchSavedPostIds(supabase, user.id);
    const listing = mapPostRow(row, { saved: savedIds.has(row.id) });

    return (
        <div className={styles.wrap}>
            <Link href="/housing" className={styles.backLink}>
                ← Back to Housing
            </Link>

            <PhotoGallery photos={listing.photos} />

            <div className={styles.headerRow}>
                <div>
                    <div className={styles.priceRow}>
                        <span className={styles.price}>€{listing.price || "—"}</span>
                        <span className={styles.perMonth}>/ month</span>
                    </div>
                    {listing.name && <h1 className={styles.name}>{listing.name}</h1>}
                </div>
                <SaveButton postId={listing.id} userId={user.id} initialSaved={listing.saved} />
            </div>

            <div className={styles.metaRow}>
                {listing.location && <span>📍 {listing.location}</span>}
                {listing.roommates && <span>👥 {listing.roommates} roommates</span>}
            </div>

            {listing.description && (
                <>
                    <hr className={styles.divider} />
                    <h2 className={styles.sectionTitle}>About this place</h2>
                    <p className={styles.description}>{listing.description}</p>
                </>
            )}

            <hr className={styles.divider} />
            <h2 className={styles.sectionTitle}>Posted by</h2>
            <div className={styles.posterRow}>
        <span className={styles.posterAvatar}>
          {listing.authorName.charAt(0).toUpperCase()}
        </span>
                <div>
                    <div className={styles.posterName}>{listing.authorName}</div>
                    <div className={styles.posterBadge}>✓ Student verified</div>
                </div>
            </div>
            <ChatButton authorName={listing.authorName}/>
        </div>
    );
}