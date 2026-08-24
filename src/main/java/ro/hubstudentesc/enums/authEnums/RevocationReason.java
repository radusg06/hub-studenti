package ro.hubstudentesc.enums.authEnums;

public enum RevocationReason {
    logout,
    user_request,
    admin_request,
    password_change,
    rotated,
    reuse_detected,
    session_expired,
}
