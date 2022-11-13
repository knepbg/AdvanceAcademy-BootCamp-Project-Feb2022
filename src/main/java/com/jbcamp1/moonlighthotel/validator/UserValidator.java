package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.exception.DuplicateRecordException;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserValidator {

    @Autowired
    private final UserRepository userRepository;

    public void validateUserExisting(Long id) {
        userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(String
                .format("User with id %d is not existing", id)));
    }

    public void validateEmailDuplicate(String email) {
        if (userRepository.isEmailExist(email) != null) {
            throw new DuplicateRecordException(String
                    .format("User with %s email, already exist,try another email", email), "email");
        }
    }
}