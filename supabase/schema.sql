-- ============================================================
-- UNIverse -- schema initiala pentru profile de utilizatori
-- Ruleaza tot acest fisier o singura data in Supabase SQL Editor
-- ============================================================

-- Tipurile de cont si starea de aprobare
create type account_type as enum ('student', 'company', 'university');
create type approval_status as enum ('pending', 'approved', 'rejected');

-- Un rand de profil pentru fiecare user din auth.users
create table public.profiles (
  id uuid primary key references auth.users(id) on delete cascade,
  account_type account_type not null,
  full_name text,
  company_name text,
  institution_name text,
  status approval_status not null default 'approved',
  created_at timestamptz not null default now()
);

-- La fiecare signup nou, se creeaza automat randul de profil.
-- Conturile de tip "university" pornesc cu status "pending".
create or replace function public.handle_new_user()
returns trigger as $$
begin
  insert into public.profiles (id, account_type, full_name, company_name, institution_name, status)
  values (
    new.id,
    coalesce(new.raw_user_meta_data->>'account_type', 'student')::public.account_type,
    new.raw_user_meta_data->>'full_name',
    new.raw_user_meta_data->>'company_name',
    new.raw_user_meta_data->>'institution_name',
    case
      when coalesce(new.raw_user_meta_data->>'account_type', 'student') = 'university'
        then 'pending'::public.approval_status
      else 'approved'::public.approval_status
    end
  );
  return new;
end;
$$ language plpgsql security definer
set search_path = public;

create trigger on_auth_user_created
  after insert on auth.users
  for each row execute procedure public.handle_new_user();

-- Row Level Security
alter table public.profiles enable row level security;

create policy "Users can view their own profile"
  on public.profiles for select
  using (auth.uid() = id);

create policy "Users can update their own profile"
  on public.profiles for update
  using (auth.uid() = id);
