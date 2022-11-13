package com.jbcamp1.moonlighthotel.service;

import com.jbcamp1.moonlighthotel.model.Role;

import java.util.Set;

public interface RoleService {

    Role findById(Long id);

    Role findByName(String roleName);

    Role update(Role role, Long id);

    void delete(Long id);

    Set<Role> findAll();

    Role save(Role role);
}
