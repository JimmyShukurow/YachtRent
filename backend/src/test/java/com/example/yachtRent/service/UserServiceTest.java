package com.example.yachtRent.service;

import com.example.yachtRent.entity.UserEntity;
import com.example.yachtRent.exception.UsernameIsAlreadyTakenException;
import com.example.yachtRent.repository.InvitationLinkRepository;
import com.example.yachtRent.repository.RoleRepository;
import com.example.yachtRent.repository.UserRepository;
import com.example.yachtRent.repository.UserRoleRepository;
import com.example.yachtRent.request.RegisterRequest;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {


    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private InvitationLinkRepository invitationLinkRepository;
    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(
                userRepository,
                roleRepository,
                userRoleRepository,
                emailSenderService,
                invitationLinkRepository
        );
    }

    @Test
    void itShouldGetAllUsersFromTable() {
        //when
        underTest.getAll();
        //then
        verify(userRepository).findAll();
    }

    @Test
    @Disabled
    void itShouldRegisterUser() {
        //given
        var request = createRegisterRequest();
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(underTest.hashString(request.getPassword()));
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setToken(underTest.generateToken());
        //when
        underTest.register(request);
        //then
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(argumentCaptor.capture());
        var capturedEntity = argumentCaptor.getValue();

        assertThat(capturedEntity).isEqualTo(userEntity);

    }

    @Test
    public void itShouldThrowUserAlreadyExistsException() {
        //given
        var request = createRegisterRequest();
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(request.getPassword());
        userEntity.setCreatedAt(LocalDateTime.now());

        given(userRepository.findByUsername(request.getUsername())).willReturn(Optional.of(userEntity));
        //when
        //then
        assertThatThrownBy(() -> underTest.register(request)).isInstanceOf(UsernameIsAlreadyTakenException.class);
        verify(userRepository, never()).save(any());
    }

    public UserEntity createUser() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        UserEntity entity = new UserEntity();
        entity.setFirstName(faker.name().firstName());
        entity.setLastName(faker.name().lastName());
        entity.setUsername(faker.name().username());
        entity.setPassword(fakeValuesService.regexify("[a-z1-9]{10}"));
        entity.setCreatedAt(LocalDateTime.now());

        return entity;
    }

    public RegisterRequest createRegisterRequest() {
        RegisterRequest registerRequest = new RegisterRequest();
        Faker faker = new Faker();
        registerRequest.setFirstName(faker.name().firstName());
        registerRequest.setLastName(faker.name().lastName());
        registerRequest.setUsername(faker.name().username());
        registerRequest.setPassword(faker.random().toString());

        return registerRequest;
    }
}