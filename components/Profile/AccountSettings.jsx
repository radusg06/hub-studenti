"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { createClient } from "@/lib/supabase/client";
import styles from "./AccountSettings.module.css";

export default function AccountSettings({ userId, email, profile }) {
    const router = useRouter();

    const [newEmail, setNewEmail] = useState("");
    const [emailStatus, setEmailStatus] = useState("");
    const [emailSaving, setEmailSaving] = useState(false);

    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [passwordStatus, setPasswordStatus] = useState("");
    const [passwordSaving, setPasswordSaving] = useState(false);

    const [notifications, setNotifications] = useState(profile?.email_notifications ?? true);
    const [privateProfile, setPrivateProfile] = useState(profile?.private_profile ?? false);

    const [deleteStep, setDeleteStep] = useState("idle"); // idle | confirm | deleting
    const [deleteError, setDeleteError] = useState("");

    async function handleChangeEmail(e) {
        e.preventDefault();
        setEmailStatus("");
        if (!newEmail.trim()) return;
        setEmailSaving(true);

        const supabase = createClient();
        const { error } = await supabase.auth.updateUser({ email: newEmail.trim() });
        setEmailSaving(false);

        if (error) {
            setEmailStatus(error.message);
        } else {
            setEmailStatus("Check your new email address to confirm the change.");
            setNewEmail("");
        }
    }

    async function handleChangePassword(e) {
        e.preventDefault();
        setPasswordStatus("");

        if (newPassword.length < 8) {
            setPasswordStatus("Password must be at least 8 characters.");
            return;
        }
        if (newPassword !== confirmPassword) {
            setPasswordStatus("Passwords don't match.");
            return;
        }

        setPasswordSaving(true);
        const supabase = createClient();
        const { error } = await supabase.auth.updateUser({ password: newPassword });
        setPasswordSaving(false);

        if (error) {
            setPasswordStatus(error.message);
        } else {
            setPasswordStatus("Password updated.");
            setNewPassword("");
            setConfirmPassword("");
        }
    }

    async function handleToggleNotifications() {
        const next = !notifications;
        setNotifications(next);
        const supabase = createClient();
        await supabase.from("profiles").update({ email_notifications: next }).eq("id", userId);
    }

    async function handleTogglePrivate() {
        const next = !privateProfile;
        setPrivateProfile(next);
        const supabase = createClient();
        await supabase.from("profiles").update({ private_profile: next }).eq("id", userId);
    }

    async function handleDeleteAccount() {
        setDeleteStep("deleting");
        setDeleteError("");
        try {
            const res = await fetch("/api/account/delete", { method: "POST" });
            const data = await res.json();
            if (!res.ok) throw new Error(data.error || "Failed to delete account.");

            const supabase = createClient();
            await supabase.auth.signOut();
            router.push("/login");
        } catch (e) {
            setDeleteError(e.message);
            setDeleteStep("confirm");
        }
    }

    return (
        <div className={styles.card}>
            <h2 className={styles.title}>Account settings</h2>

            <form className={styles.section} onSubmit={handleChangeEmail}>
                <label className={styles.field}>
                    <span>Email</span>
                    <div className={styles.inline}>
                        <input
                            type="email"
                            value={newEmail}
                            onChange={(e) => setNewEmail(e.target.value)}
                            placeholder={email}
                        />
                        <button type="submit" className="btn btn-outline" disabled={emailSaving}>
                            {emailSaving ? "Saving…" : "Change"}
                        </button>
                    </div>
                </label>
                {emailStatus && <p className={styles.status}>{emailStatus}</p>}
            </form>

            <form className={styles.section} onSubmit={handleChangePassword}>
                <label className={styles.field}>
                    <span>New password</span>
                    <input
                        type="password"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        placeholder="At least 8 characters"
                    />
                </label>
                <label className={styles.field}>
                    <span>Confirm new password</span>
                    <div className={styles.inline}>
                        <input
                            type="password"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            placeholder="Repeat password"
                        />
                        <button type="submit" className="btn btn-outline" disabled={passwordSaving}>
                            {passwordSaving ? "Saving…" : "Change"}
                        </button>
                    </div>
                </label>
                {passwordStatus && <p className={styles.status}>{passwordStatus}</p>}
            </form>

            <div className={styles.section}>
                <div className={styles.toggleRow}>
                    <span>Email notifications</span>
                    <button
                        type="button"
                        className={`${styles.toggle} ${notifications ? styles.toggleOn : ""}`}
                        onClick={handleToggleNotifications}
                        aria-pressed={notifications}
                    >
                        <span className={styles.toggleDot} />
                    </button>
                </div>
                <div className={styles.toggleRow}>
                    <span>Private profile</span>
                    <button
                        type="button"
                        className={`${styles.toggle} ${privateProfile ? styles.toggleOn : ""}`}
                        onClick={handleTogglePrivate}
                        aria-pressed={privateProfile}
                    >
                        <span className={styles.toggleDot} />
                    </button>
                </div>
            </div>

            <div className={styles.dangerSection}>
                {deleteStep === "idle" && (
                    <button type="button" className={styles.deleteBtn} onClick={() => setDeleteStep("confirm")}>
                        Delete account
                    </button>
                )}
                {(deleteStep === "confirm" || deleteStep === "deleting") && (
                    <div className={styles.confirmBox}>
                        <p>
                            This permanently deletes your account and all your posts. This
                            can&apos;t be undone.
                        </p>
                        {deleteError && <p className={styles.status}>{deleteError}</p>}
                        <div className={styles.actions}>
                            <button
                                type="button"
                                className={styles.deleteBtn}
                                onClick={handleDeleteAccount}
                                disabled={deleteStep === "deleting"}
                            >
                                {deleteStep === "deleting" ? "Deleting…" : "Yes, delete my account"}
                            </button>
                            <button
                                type="button"
                                className="btn btn-outline"
                                onClick={() => setDeleteStep("idle")}
                                disabled={deleteStep === "deleting"}
                            >
                                Cancel
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}