"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { createClient } from "@/lib/supabase/client";
import styles from "./AcademicInfo.module.css";

const INTEREST_OPTIONS = [
    "Technology",
    "AI",
    "Business",
    "Travel",
    "Sports",
    "Arts",
    "Science",
    "Health",
    "Music",
    "Gaming",
];

export default function AcademicInfo({ userId, profile }) {
    const router = useRouter();
    const [editing, setEditing] = useState(false);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");
    const [form, setForm] = useState({
        university: profile?.university || "",
        faculty: profile?.faculty || "",
        program: profile?.program || "",
        study_year: profile?.study_year || "",
        interests: profile?.interests || [],
    });

    function handleChange(e) {
        const { name, value } = e.target;
        setForm((f) => ({ ...f, [name]: value }));
    }

    function toggleInterest(interest) {
        setForm((f) => ({
            ...f,
            interests: f.interests.includes(interest)
                ? f.interests.filter((i) => i !== interest)
                : [...f.interests, interest],
        }));
    }

    async function handleSave(e) {
        e.preventDefault();
        setError("");
        setSaving(true);

        const supabase = createClient();
        const { error: updateError } = await supabase
            .from("profiles")
            .update({
                university: form.university.trim() || null,
                faculty: form.faculty.trim() || null,
                program: form.program.trim() || null,
                study_year: form.study_year.trim() || null,
                interests: form.interests,
            })
            .eq("id", userId);

        setSaving(false);

        if (updateError) {
            setError(updateError.message);
            return;
        }

        setEditing(false);
        router.refresh();
    }

    function handleCancel() {
        setForm({
            university: profile?.university || "",
            faculty: profile?.faculty || "",
            program: profile?.program || "",
            study_year: profile?.study_year || "",
            interests: profile?.interests || [],
        });
        setError("");
        setEditing(false);
    }

    if (editing) {
        return (
            <form className={styles.card} onSubmit={handleSave}>
                <h2 className={styles.title}>Academic information</h2>

                <label className={styles.field}>
                    <span>University</span>
                    <input
                        type="text"
                        name="university"
                        value={form.university}
                        onChange={handleChange}
                        placeholder="POLITEHNICA Bucharest"
                    />
                </label>

                <label className={styles.field}>
                    <span>Faculty</span>
                    <input
                        type="text"
                        name="faculty"
                        value={form.faculty}
                        onChange={handleChange}
                        placeholder="ETTI"
                    />
                </label>

                <label className={styles.field}>
                    <span>Program</span>
                    <input
                        type="text"
                        name="program"
                        value={form.program}
                        onChange={handleChange}
                        placeholder="Computer Science"
                    />
                </label>

                <label className={styles.field}>
                    <span>Year</span>
                    <input
                        type="text"
                        name="study_year"
                        value={form.study_year}
                        onChange={handleChange}
                        placeholder="4"
                    />
                </label>

                <div className={styles.field}>
                    <span>Interests</span>
                    <div className={styles.chipRow}>
                        {INTEREST_OPTIONS.map((interest) => {
                            const active = form.interests.includes(interest);
                            return (
                                <button
                                    key={interest}
                                    type="button"
                                    className={`${styles.chip} ${active ? styles.chipActive : ""}`}
                                    onClick={() => toggleInterest(interest)}
                                >
                                    {interest}
                                </button>
                            );
                        })}
                    </div>
                </div>

                {error && <p className={styles.error}>{error}</p>}

                <div className={styles.actions}>
                    <button type="submit" className="btn btn-solid" disabled={saving}>
                        {saving ? "Saving…" : "Save changes"}
                    </button>
                    <button type="button" className="btn btn-outline" onClick={handleCancel}>
                        Cancel
                    </button>
                </div>
            </form>
        );
    }

    const hasAnyInfo =
        profile?.university || profile?.faculty || profile?.program || profile?.study_year;

    return (
        <div className={styles.card}>
            <div className={styles.titleRow}>
                <h2 className={styles.title}>Academic information</h2>
                <button type="button" className="btn btn-outline" onClick={() => setEditing(true)}>
                    Edit
                </button>
            </div>

            {!hasAnyInfo && (
                <p className={styles.empty}>
                    Add your university, faculty and interests to personalize your feed.
                </p>
            )}

            {profile?.university && (
                <div className={styles.infoRow}>
                    <span>University</span>
                    <span>{profile.university}</span>
                </div>
            )}
            {profile?.faculty && (
                <div className={styles.infoRow}>
                    <span>Faculty</span>
                    <span>{profile.faculty}</span>
                </div>
            )}
            {profile?.program && (
                <div className={styles.infoRow}>
                    <span>Program</span>
                    <span>{profile.program}</span>
                </div>
            )}
            {profile?.study_year && (
                <div className={styles.infoRow}>
                    <span>Year</span>
                    <span>{profile.study_year}</span>
                </div>
            )}

            {profile?.interests?.length > 0 && (
                <div className={styles.chipRow} style={{ marginTop: "14px" }}>
                    {profile.interests.map((interest) => (
                        <span key={interest} className={styles.chipReadonly}>
              {interest}
            </span>
                    ))}
                </div>
            )}
        </div>
    );
}