"use client";

import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { createClient } from "@/lib/supabase/client";
import { getRedirectPath } from "@/lib/getRedirectPath";
import styles from "./LoginForm.module.css";

export default function LoginForm() {
  const router = useRouter();
  const [form, setForm] = useState({ email: "", password: "" });
  const [showPassword, setShowPassword] = useState(false);
  const [errors, setErrors] = useState({});
  const [serverError, setServerError] = useState("");
  const [status, setStatus] = useState("idle"); // idle | loading

  function handleChange(e) {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
    if (errors[name]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[name];
        return next;
      });
    }
  }

  function validate() {
    const errs = {};
    if (!form.email.trim()) errs.email = "Please enter your email.";
    else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email))
      errs.email = "Enter a valid email address.";
    if (!form.password) errs.password = "Please enter your password.";
    return errs;
  }

  async function handleSubmit(e) {
    e.preventDefault();
    const errs = validate();
    setErrors(errs);
    if (Object.keys(errs).length > 0) return;

    setServerError("");
    setStatus("loading");

    const supabase = createClient();
    const { data, error } = await supabase.auth.signInWithPassword({
      email: form.email,
      password: form.password,
    });

    if (error) {
      setServerError(error.message);
      setStatus("idle");
      return;
    }

    const accountType = data.user?.user_metadata?.account_type;

    router.refresh();
    router.push(getRedirectPath(accountType));
  }

  return (
    <div className={styles.card}>
      <h1 className={styles.title}>Welcome back</h1>
      <p className={styles.subtitle}>Log in to continue to UNIverse.</p>

      <form className={styles.form} onSubmit={handleSubmit} noValidate>
        <label className={styles.field}>
          <span>Email</span>
          <input
            type="email"
            name="email"
            value={form.email}
            onChange={handleChange}
            placeholder="you@university.edu"
            autoComplete="email"
          />
          {errors.email && (
            <span className={styles.error}>{errors.email}</span>
          )}
        </label>

        <label className={styles.field}>
          <div className={styles.fieldHeader}>
            <span>Password</span>
            <Link href="/forgot-password" className={styles.forgotLink}>
              Forgot password?
            </Link>
          </div>
          <div className={styles.passwordWrap}>
            <input
              type={showPassword ? "text" : "password"}
              name="password"
              value={form.password}
              onChange={handleChange}
              placeholder="Your password"
              autoComplete="current-password"
            />
            <button
              type="button"
              className={styles.togglePassword}
              onClick={() => setShowPassword((v) => !v)}
              aria-label={showPassword ? "Hide password" : "Show password"}
            >
              {showPassword ? "Hide" : "Show"}
            </button>
          </div>
          {errors.password && (
            <span className={styles.error}>{errors.password}</span>
          )}
        </label>

        {serverError && (
          <p className={styles.serverError} role="alert">
            {serverError}
          </p>
        )}

        <button
          type="submit"
          className={`btn btn-solid btn-lg ${styles.submitBtn}`}
          disabled={status === "loading"}
        >
          {status === "loading" ? "Logging in…" : "Log in"}
        </button>
      </form>

      <p className={styles.footerNote}>
        Don&apos;t have an account? <Link href="/signup">Sign up</Link>
      </p>
    </div>
  );
}
