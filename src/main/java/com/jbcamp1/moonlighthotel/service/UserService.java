package com.jbcamp1.moonlighthotel.service;

import com.jbcamp1.moonlighthotel.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User update(User user, Long id);

    void delete(Long id);

    List<User> findAll();

    User save(User user);

    User saveByAdmin(User user);

    User findByEmail(String email);
}
