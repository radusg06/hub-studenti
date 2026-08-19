# UNIverse — Next.js + Supabase

## 1. Instalare

```
npm install
```

## 2. Assets

Copiaza `logo.png`, `avatar1.webp`, `avatar2.jpg`, `avatar3.jpg` din
proiectul tau vechi in `public/assets/` (vezi nota din acel folder).

## 3. Configurare Supabase

1. supabase.com -> proiect nou
2. SQL Editor -> ruleaza tot continutul din `supabase/schema.sql`
3. Settings -> API -> copiaza Project URL si anon/publishable key
4. Copiaza `.env.local.example` -> redenumeste in `.env.local` -> completeaza cheile
5. (optional, pt testare rapida) Authentication -> Providers -> Email -> dezactiveaza "Confirm email"

## 4. Rulare

```
npm run dev
```

## 5. Structura

```
app/
  layout.js, page.js          -> landing page public
  login/, signup/               -> autentificare (conectate la Supabase)
  (student)/
    layout.js                   -> Sidebar + verificare autentificare
    dashboard/page.js           -> feed cu scroll infinit
  company/dashboard/            -> destinatie cont Companie
  university/dashboard/         -> destinatie cont Facultate (cu status pending/approved)
components/
  Navbar/ Hero/ Features/ CTA/ Footer/ StarsBackground/   -> landing page
  Auth/                          -> SignupForm, LoginForm
  Sidebar/                       -> navigare + profil (zona logata, studenti)
  DashboardShell/                -> Navbar+Footer comun (companie/facultate)
  InfiniteScroll/                -> feedul de tip social media
lib/
  supabase/client.js, server.js  -> conexiune Supabase
  getRedirectPath.js             -> decide unde ajunge fiecare tip de cont dupa login
middleware.js                     -> tine sesiunea de login activa
supabase/schema.sql                -> schema bazei de date
```

## 6. Ce nu e construit inca

- `/forgot-password` — linkul exista, pagina nu
- `/dashboard/marketplace`, `/dashboard/jobs`, `/dashboard/housing`, `/dashboard/universities` — linkuri in Sidebar, pagini inca neconstruite
- `/profile` — link in meniul de profil, pagina inca neconstruita
- Dashboard de admin pentru aprobarea conturilor de Facultate — se face manual din Supabase Table Editor (`profiles` -> schimba `status` in `approved`)
