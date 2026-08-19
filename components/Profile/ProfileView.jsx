"use client";

import { useState, useRef, useEffect } from "react";
import { useRouter } from "next/navigation";
import { createClient } from "@/lib/supabase/client";
import LogoutButton from "@/components/Auth/LogoutButton";
import styles from "./ProfileView.module.css";

export default function ProfileView({ user, profile }) {
    const router = useRouter();
    const fileInputRef = useRef(null);

    const [editing, setEditing] = useState(false);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");
    const [form, setForm] = useState({
        full_name: profile?.full_name || "",
        username: profile?.username || "",
        bio: profile?.bio || "",
        city: profile?.city || "",
    });

    const [avatarUrl, setAvatarUrl] = useState(profile?.avatar_url || "");
    const [uploading, setUploading] = useState(false);
    const [avatarError, setAvatarError] = useState("");

    // Keep local avatar state in sync whenever the server sends a
    // fresh profile (e.g. after router.refresh()) -- without this,
    // the big avatar can lag behind the one in the Sidebar.
    useEffect(() => {
        setAvatarUrl(profile?.avatar_url || "");
    }, [profile?.avatar_url]);

    const displayName = profile?.full_name || "Student";
    const initial = displayName.charAt(0).toUpperCase();
    const handle = profile?.username || user.email.split("@")[0];

    function handleChange(e) {
        const { name, value } = e.target;
        setForm((f) => ({ ...f, [name]: value }));
    }

    async function handleSave(e) {
        e.preventDefault();
        setError("");
        setSaving(true);

        const supabase = createClient();
        const { error: updateError } = await supabase
            .from("profiles")
            .update({
                full_name: form.full_name.trim() || null,
                username: form.username.trim() || null,
                bio: form.bio.trim() || null,
                city: form.city.trim() || null,
            })
            .eq("id", user.id);

        setSaving(false);

        if (updateError) {
            if (updateError.code === "23505") {
                setError("That username is already taken.");
            } else {
                setError(updateError.message);
            }
            return;
        }

        setEditing(false);
        router.refresh();
    }

    function handleCancel() {
        setForm({
            full_name: profile?.full_name || "",
            username: profile?.username || "",
            bio: profile?.bio || "",
            city: profile?.city || "",
        });
        setError("");
        setEditing(false);
    }

    function handleAvatarClick() {
        setAvatarError("");
        fileInputRef.current?.click();
    }

    async function handleAvatarChange(e) {
        const file = e.target.files?.[0];
        e.target.value = ""; // allow re-selecting the same file later
        if (!file) return;

        if (!file.type.startsWith("image/")) {
            setAvatarError("Please choose an image file.");
            return;
        }
        if (file.size > 3 * 1024 * 1024) {
            setAvatarError("Image must be smaller than 3MB.");
            return;
        }

        setUploading(true);
        setAvatarError("");

        const supabase = createClient();
        const ext = file.name.split(".").pop();
        const path = `${user.id}/${Date.now()}.${ext}`;

        const { error: uploadError } = await supabase.storage
            .from("avatars")
            .upload(path, file, { upsert: true, contentType: file.type });

        if (uploadError) {
            setUploading(false);
            setAvatarError(uploadError.message);
            return;
        }

        const { data: publicUrlData } = supabase.storage.from("avatars").getPublicUrl(path);
        const publicUrl = publicUrlData.publicUrl;

        const { error: updateError } = await supabase
            .from("profiles")
            .update({ avatar_url: publicUrl })
            .eq("id", user.id);

        setUploading(false);

        if (updateError) {
            setAvatarError(updateError.message);
            return;
        }

        setAvatarUrl(publicUrl);
        router.refresh();
    }

    if (editing) {
        return (
            <div className={styles.wrap}>
                <h1 className={styles.title}>Edit profile</h1>
                <form className={styles.card} onSubmit={handleSave}>
                    <label className={styles.field}>
                        <span>Full name</span>
                        <input
                            type="text"
                            name="full_name"
                            value={form.full_name}
                            onChange={handleChange}
                            placeholder="Ada Tatulescu"
                        />
                    </label>

                    <label className={styles.field}>
                        <span>Username</span>
                        <input
                            type="text"
                            name="username"
                            value={form.username}
                            onChange={handleChange}
                            placeholder="ada"
                        />
                    </label>

                    <label className={styles.field}>
                        <span>Bio</span>
                        <textarea
                            name="bio"
                            value={form.bio}
                            onChange={handleChange}
                            placeholder="Frontend developer & student"
                            rows={3}
                        />
                    </label>

                    <label className={styles.field}>
                        <span>City</span>
                        <input
                            type="text"
                            name="city"
                            value={form.city}
                            onChange={handleChange}
                            placeholder="Bucharest"
                        />
                    </label>

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
            </div>
        );
    }

    return (
        <div className={styles.wrap}>
            <h1 className={styles.title}>Profile</h1>

            <div className={styles.card}>
                <div className={styles.header}>
                    <input
                        ref={fileInputRef}
                        type="file"
                        accept="image/*"
                        onChange={handleAvatarChange}
                        className={styles.hiddenInput}
                    />
                    <button
                        type="button"
                        className={styles.avatarWrap}
                        onClick={handleAvatarClick}
                        disabled={uploading}
                        aria-label="Change profile photo"
                        title="Change profile photo"
                    >
                        {avatarUrl ? (
                            <img src={avatarUrl} alt="" className={styles.avatarImg} />
                        ) : (
                            <span className={styles.avatar}>{initial}</span>
                        )}
                        <span className={styles.avatarOverlay}>
              {uploading ? (
                  <span className={styles.uploadingText}>Uploading…</span>
              ) : (
                  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                      <path d="M4 8a2 2 0 0 1 2-2h1.5l1-1.5h7l1 1.5H18a2 2 0 0 1 2 2v9a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V8z" />
                      <circle cx="12" cy="13" r="3.5" />
                  </svg>
              )}
            </span>
                    </button>
                    {avatarError && <p className={styles.avatarError}>{avatarError}</p>}
                    <div className={styles.name}>{displayName}</div>
                    <div className={styles.handle}>@{handle}</div>
                </div>

                {profile?.bio && <p className={styles.bio}>{profile.bio}</p>}

                <div className={styles.infoRow}>
                    <span>Email</span>
                    <span>{user.email}</span>
                </div>
                <div className={styles.infoRow}>
                    <span>Account type</span>
                    <span>{profile?.account_type || "student"}</span>
                </div>
                {profile?.city && (
                    <div className={styles.infoRow}>
                        <span>City</span>
                        <span>{profile.city}</span>
                    </div>
                )}

                <div className={styles.actions}>
                    <button type="button" className="btn btn-outline" onClick={() => setEditing(true)}>
                        Edit Profile
                    </button>
                    <LogoutButton className="btn btn-outline" />
                </div>
            </div>
        </div>
    );
}