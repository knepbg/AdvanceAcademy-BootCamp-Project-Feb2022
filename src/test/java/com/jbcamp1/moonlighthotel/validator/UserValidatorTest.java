package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.exception.DuplicateRecordException;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.model.User;
import com.jbcamp1.moonlighthotel.repository.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidator userValidation;
    private final Long id = 1L;


    @Test
    public void validateWhenNoUserFoundThrowCorrectException() {
        when(userRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> userValidation.validateUserExisting(id));
        assertThat(thrown).isInstanceOf(RecordNotFoundException.class)
                .hasMessage("User with id %d is not existing", id);
    }

    @Test
    public void validateEmailDuplicate() {
        when(userRepository.isEmailExist(anyString()))
                .thenReturn(User.builder().build());
        String email = "testmail@abv.bg";
        Throwable thrown = catchThrowable(() -> userValidation.validateEmailDuplicate(email));
        assertThat(thrown).isInstanceOf(DuplicateRecordException.class)
                .hasMessage("User with %s email, already exist,try another email", email);
    }
}
