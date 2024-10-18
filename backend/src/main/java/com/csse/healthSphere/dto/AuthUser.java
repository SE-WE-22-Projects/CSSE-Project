package com.csse.healthSphere.dto;

import com.csse.healthSphere.enums.Role;
import com.csse.healthSphere.model.Person;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class AuthUser implements UserDetails {
    private final String username;
    private final String password;
    private final Role role;
    private final Person person;

    @Override
    public Collection<Role> getAuthorities() {
        return switch (role) {
            case ADMIN -> List.of(Role.ADMIN, Role.STAFF, Role.USER);
            case STAFF -> List.of(Role.STAFF, Role.USER);
            case USER -> List.of(Role.USER);
        };
    }
}
