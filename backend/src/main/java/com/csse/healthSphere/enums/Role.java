package com.csse.healthSphere.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, STAFF, ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
