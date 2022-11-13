package com.jbcamp1.moonlighthotel.service.impl;

import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.model.User;
import com.jbcamp1.moonlighthotel.repository.RoleRepository;
import com.jbcamp1.moonlighthotel.repository.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void verifyFindById() {
        //given
        when(userRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(User.builder().build()));
        //when
        userServiceImpl.findById(1L);
        //then
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyFindByIdException() {
        //given
        Long id = 1L;
        exception.expect(RecordNotFoundException.class);
        exception.expectMessage(String.format("User with id %d is not existing", id));
        when(userRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        //when
        userServiceImpl.findById(id);
        //then
        verify(userRepository, times(1)).findById(id);
    }

//    @Test
//    public void verifyFindByEmail() {
//        //given
//        when(userRepository.findByEmail(any(String.class)))
//                .thenReturn(Optional.of(User.builder().build()));
//        //when
//        userServiceImpl.findByEmail("test");
//        //then
//        verify(userRepository, times(1)).findByEmail("test");
//    }

//    @Test
//    public void verifyFindByEmailException() {
//        //given
//        String email = "test";
//        exception.expect(RecordNotFoundException.class);
//        exception.expectMessage(String.format("User with %s email, not found.", email));
//        when(userRepository.findByEmail(any(String.class)))
//                .thenReturn(Optional.empty());
//        //when
//        userServiceImpl.findByEmail(email);
//        //then
//        verify(userRepository, times(1)).findByEmail(any(String.class));
//    }

//    @Test
//    public void verifyUserUpdateFindById() {
//        //given
//        User foundUser = User.builder().id(1L).email("test1").build();
//        User userForUpdate = User.builder().email("test").build();
//        when(userRepository.findById(1L))
//                .thenReturn(Optional.of(foundUser));
//        when(userRepository.findByEmail("test")).thenThrow(RecordNotFoundException.class);
//        //when
//        userServiceImpl.update(userForUpdate, 1L);
//        //then
//        verify(userRepository, times(1)).findById(1L);
//    }

//    @Test
//    public void verifyUserUpdateFindByEmail() {
//        //given
//        User foundUser = User.builder().id(1L).email("test1").build();
//        User userForUpdate = User.builder().email("test").build();
//        when(userRepository.findById(1L))
//                .thenReturn(Optional.of(foundUser));
//        when(userRepository.findByEmail("test")).thenThrow(RecordNotFoundException.class);
//        //when
//        userServiceImpl.update(userForUpdate, 1L);
//        //then
//        verify(userRepository, times(1)).findByEmail(any(String.class));
//    }

//    @Test
//    public void verifyUserUpdateDuplicateException() {
//        //given
//        User userForUpdate = User.builder().email("test").build();
//        User user = User.builder().id(1L).email("test").build();
//        exception.expect(DuplicateRecordException.class);
//        exception.expectMessage(String.format(String
//                .format("User with %s email, already exist,try another email", user.getEmail()), "email"));
//        when(userRepository.findById(1L))
//                .thenReturn(Optional.of(user));
//        when(userRepository.findByEmail("test")).thenReturn(Optional.of(user));
//        //when
//        userServiceImpl.update(userForUpdate, 1L);
//    }

//    @Test
//    public void verifyUserUpdatePasswordEncoding() {
//        //given
//        User foundUser = User.builder().id(1L).email("test1").build();
//        User userForUpdate = User.builder().email("test").build();
//        when(userRepository.findById(1L))
//                .thenReturn(Optional.of(foundUser));
//        when(userRepository.findByEmail("test")).thenThrow(RecordNotFoundException.class);
//        //when
//        userServiceImpl.update(userForUpdate, 1L);
//        //then
//        verify(passwordEncoder, times(1)).encode(userForUpdate.getPassword());
//    }
//
//    @Test
//    public void verifyCorrectlyBuildUserOnUpdate() {
//        //given
//        User foundUser = User.builder().id(1L).email("test1").build();
//        User userForUpdate = User.builder().email("test").build();
//        when(userRepository.findById(1L))
//                .thenReturn(Optional.of(foundUser));
//        when(userRepository.findByEmail("test")).thenThrow(RecordNotFoundException.class);
//        //when
//        userServiceImpl.update(userForUpdate, 1L);
//        //then
//        verify(userRepository, times(1)).save(any(User.class));
//    }

    @Test
    public void verifyUserFindAll() {
        //when
        userServiceImpl.findAll();
        //then
        verify(userRepository, times(1)).findAll();
    }

//    @Test
//    public void verifyNewUserSave() {
//        //given
//        User user = User.builder().build();
//        when(roleRepository.findByName(any(String.class)))
//                .thenReturn(Optional.of(Role.builder()
//                        .name("ROLE_CLIENT").build()));
//        //when
//        userServiceImpl.save(user);
//        //then
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    public void verifyDuplicateExceptionOnNewUserSave() {
//        //given
//        User user = User.builder().email("testEmail").build();
//        User foundUser = User.builder().email("testEmail").build();
//        when(roleRepository.findByName(any(String.class)))
//                .thenReturn(Optional.of(Role.builder()
//                        .name("ROLE_CLIENT").build()));
//        when(userRepository.findByEmail(any(String.class)))
//                .thenReturn(Optional.of(foundUser));
//        exception.expect(DuplicateRecordException.class);
//        exception.expectMessage(String.format(String
//                .format("User with %s email, already exist,try another email", user.getEmail()), "email"));
//        //when
//        userServiceImpl.save(user);
//    }

//    @Test
//    public void verifyUserSaveFindRoleByName() {
//        //given
//        User user = User.builder().build();
//        when(roleRepository.findByName(any(String.class)))
//                .thenReturn(Optional.of(Role.builder()
//                        .name("ROLE_CLIENT")
//                        .build()));
//        //when
//        userServiceImpl.save(user);
//        //then
//        verify(roleRepository, times(1)).findByName(any(String.class));
//    }

//    @Test
//    public void verifyUserSaveFindUserByEmail() {
//        //given
//        User user = User.builder().build();
//        when(roleRepository.findByName(any(String.class)))
//                .thenReturn(Optional.of(Role.builder()
//                        .name("ROLE_CLIENT")
//                        .build()));
//        //when
//        userServiceImpl.save(user);
//        //then
//        verify(userRepository, times(1)).findByEmail(user.getEmail());
//    }

//    @Test
//    public void verifyNewUserSavePasswordsEncoded() {
//        //given
//        User user = User.builder().build();
//        when(roleRepository.findByName(any(String.class)))
//                .thenReturn(Optional.of(Role.builder()
//                        .name("ROLE_CLIENT").build()));
//        //when
//        userServiceImpl.save(user);
//        //then
//        verify(passwordEncoder, times(1)).encode(user.getPassword());
//    }

//    @Test
//    public void verifyUserSaveByAdmin() {
//        //given
//        User user = User.builder().build();
//        //when
//        userServiceImpl.saveByAdmin(user);
//        //then
//        verify(userRepository, times(1)).save(any(User.class));
//    }

//    @Test
//    public void verifyDuplicateExceptionOnUserSaveByAdmin() {
//        //given
//        User user = User.builder().email("testEmail").build();
//        User foundUser = User.builder().email("testEmail").build();
//        when(userRepository.findByEmail(any(String.class)))
//                .thenReturn(Optional.of(foundUser));
//        exception.expect(DuplicateRecordException.class);
//        exception.expectMessage(String.format(String
//                .format("User with %s email, already exist,try another email", user.getEmail()), "email"));
//        //when
//        userServiceImpl.saveByAdmin(user);
//    }

//    @Test
//    public void verifyUserSaveByAdminFindUserByEmail() {
//        //given
//        User user = User.builder().build();
//        //when
//        userServiceImpl.saveByAdmin(user);
//        //then
//        verify(userRepository, times(1)).findByEmail(user.getEmail());
//    }
//
//    @Test
//    public void verifyUserSaveByAdminPasswordsEncoded() {
//        //given
//        User user = User.builder().build();
//        //when
//        userServiceImpl.saveByAdmin(user);
//        //then
//        verify(passwordEncoder, times(1)).encode(user.getPassword());
//    }

}
