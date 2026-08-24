"use client";

import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { createClient } from "@/lib/supabase/client";
import { getRedirectPath } from "@/lib/getRedirectPath";
import styles from "./SignupForm.module.css";

const ACCOUNT_TYPES = [
  { value: "student", label: "Student" },
  { value: "company", label: "Companie" },
  { value: "university", label: "Facultate" },
];

export default function SignupForm() {
  const router = useRouter();
  const [accountType, setAccountType] = useState("student");
  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    confirm: "",
    companyName: "",
    institutionName: "",
  });
  const [errors, setErrors] = useState({});
  const [serverError, setServerError] = useState("");
  const [status, setStatus] = useState("idle"); // idle | loading | success

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

  function handleTypeChange(type) {
    setAccountType(type);
    setErrors({});
  }

  function validate() {
    const errs = {};

    if (accountType === "company") {
      if (!form.companyName.trim())
        errs.companyName = "Please enter the company name.";
    } else if (accountType === "university") {
      if (!form.institutionName.trim())
        errs.institutionName = "Please enter the institution name.";
    } else {
      if (!form.name.trim()) errs.name = "Please enter your name.";
    }

    if (!form.email.trim()) errs.email = "Please enter your email.";
    else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email))
      errs.email = "Enter a valid email address.";

    if (!form.password) errs.password = "Please enter a password.";
    else if (form.password.length < 8)
      errs.password = "Password must be at least 8 characters.";

    if (form.confirm !== form.password) errs.confirm = "Passwords don't match.";

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
    const { data, error } = await supabase.auth.signUp({
      email: form.email,
      password: form.password,
      options: {
        data: {
          account_type: accountType,
          full_name: form.name,
          company_name: form.companyName,
          institution_name: form.institutionName,
        },
      },
    });

    if (error) {
      setServerError(error.message);
      setStatus("idle");
      return;
    }

    if (data.session) {
      // Email confirmation is disabled -- user is already logged in.
      router.refresh();
      router.push(getRedirectPath(accountType));
      return;
    }

    setStatus("success");
  }

  if (status === "success") {
    const identity =
      accountType === "company"
        ? form.companyName
        : accountType === "university"
          ? form.institutionName
          : form.name;
    return (
      <div className={styles.card}>
        <h1 className={styles.title}>Check your email 📬</h1>
        <p className={styles.subtitle}>
          We sent a confirmation link to <strong>{form.email}</strong>. Click it
          to activate the account for <strong>{identity || form.email}</strong>
          {accountType === "university" && (
            <>
              {" "}
              — institution accounts also need manual approval before they can
              post official content.
            </>
          )}
        </p>
        <Link href="/" className="btn btn-outline btn-lg">
          Back to home
        </Link>
      </div>
    );
  }

  return (
    <div className={styles.card}>
      <h1 className={styles.title}>Create your account</h1>
      <p className={styles.subtitle}>
        Join 120+ universities already on UNIverse.
      </p>

      <div
        className={styles.typeSelector}
        role="tablist"
        aria-label="Account type"
      >
        {ACCOUNT_TYPES.map((type) => (
          <button
            key={type.value}
            type="button"
            role="tab"
            aria-selected={accountType === type.value}
            className={`${styles.typeButton} ${
              accountType === type.value ? styles.typeButtonActive : ""
            }`}
            onClick={() => handleTypeChange(type.value)}
          >
            {type.label}
          </button>
        ))}
      </div>

      <form className={styles.form} onSubmit={handleSubmit} noValidate>
        {accountType === "company" ? (
          <label className={styles.field}>
            <span>Company name</span>
            <input
              type="text"
              name="companyName"
              value={form.companyName}
              onChange={handleChange}
              placeholder="Acme Inc."
              autoComplete="organization"
            />
            {errors.companyName && (
              <span className={styles.error}>{errors.companyName}</span>
            )}
          </label>
        ) : accountType === "university" ? (
          <label className={styles.field}>
            <span>Institution name</span>
            <input
              type="text"
              name="institutionName"
              value={form.institutionName}
              onChange={handleChange}
              placeholder="Universitatea din Craiova"
              autoComplete="organization"
            />
            {errors.institutionName && (
              <span className={styles.error}>{errors.institutionName}</span>
            )}
          </label>
        ) : (
          <label className={styles.field}>
            <span>Full name</span>
            <input
              type="text"
              name="name"
              value={form.name}
              onChange={handleChange}
              placeholder="Alex Popescu"
              autoComplete="name"
            />
            {errors.name && <span className={styles.error}>{errors.name}</span>}
          </label>
        )}

        <label className={styles.field}>
          <span>Email</span>
          <input
            type="email"
            name="email"
            value={form.email}
            onChange={handleChange}
            placeholder={
              accountType === "company"
                ? "contact@company.com"
                : accountType === "university"
                  ? "contact@universitate.ro"
                  : "you@university.edu"
            }
            autoComplete="email"
          />
          {errors.email && <span className={styles.error}>{errors.email}</span>}
        </label>

        <label className={styles.field}>
          <span>Password</span>
          <input
            type="password"
            name="password"
            value={form.password}
            onChange={handleChange}
            placeholder="At least 8 characters"
            autoComplete="new-password"
          />
          {errors.password && (
            <span className={styles.error}>{errors.password}</span>
          )}
        </label>

        <label className={styles.field}>
          <span>Confirm password</span>
          <input
            type="password"
            name="confirm"
            value={form.confirm}
            onChange={handleChange}
            placeholder="Repeat your password"
            autoComplete="new-password"
          />
          {errors.confirm && (
            <span className={styles.error}>{errors.confirm}</span>
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
          {status === "loading" ? "Creating account…" : "Create Account"}
        </button>
      </form>

      <p className={styles.footerNote}>
        Already have an account? <Link href="/login">Log in</Link>
      </p>
    </div>
  );
}
