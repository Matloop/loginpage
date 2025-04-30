package com.example.loginauthapi.domain.user.enums;

public enum UserEnum {
    USER("ROLE_USER"),  // Standard user
    ADMIN("ROLE_ADMIN"); // Administrator

    private final String roleName;

    UserEnum(String roleName) {
        this.roleName = roleName;
    }

    // Getter to easily retrieve the string representation needed by Spring Security
    public String getRoleName() {
        return roleName;
    }
}
