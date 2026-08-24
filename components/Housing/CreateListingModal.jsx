"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import dynamic from "next/dynamic";
import { createClient } from "@/lib/supabase/client";
import { createPost, buildDbPayload } from "@/lib/posts";
import styles from "./CreateListingModal.module.css";

const LocationPicker = dynamic(() => import("./LocationPicker"), { ssr: false });

const MAX_PHOTOS = 10;

export default function CreateListingModal({ userId, onClose }) {
    const router = useRouter();
    const [form, setForm] = useState({
        name: "",
        price: "",
        location: "",
        roommates: "",
        description: "",
    });
    const [photos, setPhotos] = useState([]); // [{ file, previewUrl }]
    const [lat, setLat] = useState(null);
    const [lng, setLng] = useState(null);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");

    function handleChange(e) {
        const { name, value } = e.target;
        setForm((f) => ({ ...f, [name]: value }));
    }

    function handlePhotosSelected(e) {
        const files = Array.from(e.target.files || []);
        e.target.value = ""; // allow picking the same file again later

        const room = MAX_PHOTOS - photos.length;
        if (room <= 0) {
            setError(`You can add up to ${MAX_PHOTOS} photos.`);
            return;
        }

        const accepted = files.filter((f) => f.type.startsWith("image/")).slice(0, room);
        const newPhotos = accepted.map((file) => ({
            file,
            previewUrl: URL.createObjectURL(file),
        }));

        setPhotos((prev) => [...prev, ...newPhotos]);
        if (files.length > accepted.length) {
            setError(`Only added what fits under ${MAX_PHOTOS} photos.`);
        } else {
            setError("");
        }
    }

    function removePhoto(index) {
        setPhotos((prev) => {
            URL.revokeObjectURL(prev[index].previewUrl);
            return prev.filter((_, i) => i !== index);
        });
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setError("");

        if (!form.name.trim() || !form.price.trim()) {
            setError("Title and price are required.");
            return;
        }
        if (photos.length === 0) {
            setError("Add at least 1 photo.");
            return;
        }

        setSaving(true);
        const supabase = createClient();

        try {
            // Upload photos in order, so the first one stays the cover photo.
            const uploadedUrls = [];
            for (let i = 0; i < photos.length; i++) {
                const { file } = photos[i];
                const ext = file.name.split(".").pop();
                const path = `${userId}/${Date.now()}-${i}.${ext}`;

                const { error: uploadError } = await supabase.storage
                    .from("listing-photos")
                    .upload(path, file, { contentType: file.type });

                if (uploadError) throw uploadError;

                const { data: publicUrlData } = supabase.storage
                    .from("listing-photos")
                    .getPublicUrl(path);
                uploadedUrls.push(publicUrlData.publicUrl);
            }

            const { dbType, dbPayload } = buildDbPayload("Housing", {
                ...form,
                photos: uploadedUrls,
                lat: lat ?? undefined,
                lng: lng ?? undefined,
            });

            await createPost(supabase, userId, dbType, dbPayload);
            router.refresh();
            onClose();
        } catch (err) {
            setError(err.message);
            setSaving(false);
        }
    }

    return (
        <div className={styles.overlay} onClick={onClose}>
            <form
                className={styles.modal}
                onClick={(e) => e.stopPropagation()}
                onSubmit={handleSubmit}
            >
                <h2 className={styles.title}>List a place</h2>

                <label className={styles.field}>
                    <span>Photos ({photos.length}/{MAX_PHOTOS}) — first one is the cover photo</span>
                    <div className={styles.photoGrid}>
                        {photos.map((p, i) => (
                            <div key={p.previewUrl} className={styles.photoThumb}>
                                <img src={p.previewUrl} alt="" />
                                {i === 0 && <span className={styles.coverTag}>Cover</span>}
                                <button
                                    type="button"
                                    className={styles.removePhotoBtn}
                                    onClick={() => removePhoto(i)}
                                    aria-label="Remove photo"
                                >
                                    ✕
                                </button>
                            </div>
                        ))}
                        {photos.length < MAX_PHOTOS && (
                            <label className={styles.addPhotoBtn}>
                                <input
                                    type="file"
                                    accept="image/*"
                                    multiple
                                    onChange={handlePhotosSelected}
                                    className={styles.hiddenInput}
                                />
                                + Add
                            </label>
                        )}
                    </div>
                </label>

                <label className={styles.field}>
                    <span>Listing title</span>
                    <input
                        type="text"
                        name="name"
                        value={form.name}
                        onChange={handleChange}
                        placeholder="Room near Politehnica"
                    />
                </label>

                <label className={styles.field}>
                    <span>Price per month (€)</span>
                    <input
                        type="text"
                        name="price"
                        value={form.price}
                        onChange={handleChange}
                        placeholder="350"
                    />
                </label>

                <label className={styles.field}>
                    <span>Location</span>
                    <input
                        type="text"
                        name="location"
                        value={form.location}
                        onChange={handleChange}
                        placeholder="Bucharest, near campus"
                    />
                </label>

                <label className={styles.field}>
                    <span>Pin on map (optional)</span>
                    <LocationPicker lat={lat} lng={lng} onChange={(la, ln) => { setLat(la); setLng(ln); }} />
                </label>

                <label className={styles.field}>
                    <span>Roommates already living there</span>
                    <input
                        type="text"
                        name="roommates"
                        value={form.roommates}
                        onChange={handleChange}
                        placeholder="2"
                    />
                </label>

                <label className={styles.field}>
                    <span>Description</span>
                    <textarea
                        name="description"
                        value={form.description}
                        onChange={handleChange}
                        placeholder="Describe the place, the neighborhood, what's included…"
                        rows={3}
                    />
                </label>

                {error && <p className={styles.error}>{error}</p>}

                <div className={styles.actions}>
                    <button type="submit" className="btn btn-solid" disabled={saving}>
                        {saving ? "Posting…" : "Post listing"}
                    </button>
                    <button type="button" className="btn btn-outline" onClick={onClose} disabled={saving}>
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
}