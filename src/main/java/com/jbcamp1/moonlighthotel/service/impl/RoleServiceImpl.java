package com.jbcamp1.moonlighthotel.service.impl;

import com.jbcamp1.moonlighthotel.enumeration.RolePrefix;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.model.Role;
import com.jbcamp1.moonlighthotel.repository.RoleRepository;
import com.jbcamp1.moonlighthotel.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private final RoleRepository roleRepository;


    @Override
    public Role findById(Long id) {
        return null;
    }

    @Override
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() ->
                new RecordNotFoundException(String.format("Role with name %s is not existing", roleName.substring(RolePrefix.ROLE_.lengthRolePrefix()).toLowerCase())));
    }

    @Override
    public Role update(Role role, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Set<Role> findAll() {
        return null;
    }

    @Override
    public Role save(Role role) {
        return null;
    }
}
