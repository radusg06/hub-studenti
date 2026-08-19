import { createClient } from "@supabase/supabase-js";

/**
 * Admin client — uses the SECRET service role key, which bypasses
 * Row Level Security entirely. NEVER import this in a Client Component
 * or expose it to the browser. Only used inside Route Handlers.
 */
export function createAdminClient() {
    return createClient(
        process.env.NEXT_PUBLIC_SUPABASE_URL,
        process.env.SUPABASE_SERVICE_ROLE_KEY,
        { auth: { autoRefreshToken: false, persistSession: false } }
    );
}