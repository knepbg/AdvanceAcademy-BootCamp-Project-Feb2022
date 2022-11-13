package com.jbcamp1.moonlighthotel.repository;

import com.jbcamp1.moonlighthotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE u.email =?1")
    User isEmailExist(String email);
}
