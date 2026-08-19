export function getRedirectPath(accountType) {
  switch (accountType) {
    case "company":
      return "/company/dashboard";
    case "university":
      return "/university/dashboard";
    case "student":
    default:
      return "/dashboard";
  }
}
