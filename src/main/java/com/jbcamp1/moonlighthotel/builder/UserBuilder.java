package com.jbcamp1.moonlighthotel.builder;

import com.jbcamp1.moonlighthotel.enumeration.RolePrefix;
import com.jbcamp1.moonlighthotel.model.Role;
import com.jbcamp1.moonlighthotel.model.User;
import com.jbcamp1.moonlighthotel.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserBuilder {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final RoleService roleService;

    public User userBuilderUpdate(User user, Long id) {
        User updatedUser = User.builder()
                .id(id)
                .email(user.getEmail())
                .password((passwordEncoder.encode(user.getPassword())))
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .created(Instant.now())
                .roles(user.getRoles().stream()
                        .map(role -> roleService.findByName(RolePrefix.ROLE_ + role.getName().toUpperCase()))
                        .collect(Collectors.toSet()))
                .build();
        return updatedUser;
    }

    public User userBuilderSaveByClient(User user, Role role) {
        User savedUser = User.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .phone(user.getPhone())
                .surname(user.getSurname())
                .roles(Set.of(role))
                .created(Instant.now())
                .build();
        return savedUser;
    }

    public User userBuilderSaveByAdmin(User user) {
        User savedUser = User.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .phone(user.getPhone())
                .surname(user.getSurname())
                .roles(user.getRoles().stream()
                        .map(role -> roleService.findByName(RolePrefix.ROLE_ + role.getName().toUpperCase()))
                        .collect(Collectors.toSet()))
                .created(Instant.now())
                .build();
        return savedUser;
    }
}
