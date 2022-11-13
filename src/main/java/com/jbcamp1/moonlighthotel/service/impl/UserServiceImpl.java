package com.jbcamp1.moonlighthotel.service.impl;

import com.jbcamp1.moonlighthotel.builder.UserBuilder;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.model.Role;
import com.jbcamp1.moonlighthotel.model.User;
import com.jbcamp1.moonlighthotel.repository.RoleRepository;
import com.jbcamp1.moonlighthotel.repository.UserRepository;
import com.jbcamp1.moonlighthotel.service.UserService;
import com.jbcamp1.moonlighthotel.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final UserValidator userValidation;

    @Autowired
    private final UserBuilder userBuilder;

    private static final String ROLE_NAME = "ROLE_CLIENT";

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(String
                .format("User with id %d is not existing", id)));
    }

    @Override
    public User update(User user, Long id) {
        userValidation.validateUserExisting(id);
        userValidation.validateEmailDuplicate(user.getEmail());
        User userForUpdate = userBuilder.userBuilderUpdate(user, id);
        return userRepository.save(userForUpdate);
    }

    @Override
    public void delete(Long id) {

        User foundUser = findById(id);

        userRepository.deleteById(foundUser.getId());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        Role role = findRoleByName(ROLE_NAME);
        userValidation.validateEmailDuplicate(user.getEmail());
        User userForSave = userBuilder.userBuilderSaveByClient(user, role);
        return userRepository.save(userForSave);
    }

    @Override
    public User saveByAdmin(User user) {
        userValidation.validateEmailDuplicate(user.getEmail());
        User userForSave = userBuilder.userBuilderSaveByAdmin(user);
        return userRepository.save(userForSave);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException(String
                .format("User with %s email, not found.", email)));
    }

    private Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RecordNotFoundException(String.format("%s not found.", ROLE_NAME)));
    }
}
