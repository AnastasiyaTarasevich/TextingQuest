import org.example.textingquest.dtos.UserDTO;
import org.example.textingquest.entities.Role;
import org.example.textingquest.entities.User;
import org.example.textingquest.services.UserService;
import org.example.textingquest.validators.CreateUserValidator;
import org.example.textingquest.validators.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateUserValidatorTest {

    @Mock
    UserService userService;
    private CreateUserValidator validator;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        validator=CreateUserValidator.getInstance();
    }

    @Test
    void testValidUserDTO() {
        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .nickname("validNickname")
                .password("valid@example.com")
                .email("validEmail")
                .role(Role.USER)
                .build();
        try (MockedStatic<UserService> mockedUserService = mockStatic(UserService.class)) {
            UserService mockUserService = mock(UserService.class);
            when(mockUserService.checkDoubleRegistration(anyString(), anyString())).thenReturn(Optional.empty());
            mockedUserService.when(UserService::getInstance).thenReturn(mockUserService);

            // When
            ValidationResult result = validator.isValid(userDTO);

            // Then
            assertTrue(result.isValid());
            assertTrue(result.getErrors().isEmpty());

        }
    }
    @Test
    void testInvalidNickname() {
        // UserDTO с пустым никнеймом
        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .nickname("") // Неправильный никнейм
                .password("validPassword")
                .email("valid@example.com")
                .role(Role.USER)
                .build();

        // Валидация
        ValidationResult result = validator.isValid(userDTO);

        // Ожидаем, что валидация не пройдёт
        assertFalse(result.isValid(), "Expected validation to fail due to an empty nickname.");
        assertEquals(1, result.getErrors().size());
        assertEquals("invalid.nickname", result.getErrors().get(0).getCode());
    }

    @Test
    void testInvalidPassword() {
        // UserDTO с пустым паролем
        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .nickname("validNickname")
                .password("") // Неправильный пароль
                .email("valid@example.com")
                .role(Role.USER)
                .build();

        // Валидация
        ValidationResult result = validator.isValid(userDTO);

        // Ожидаем, что валидация не пройдёт
        assertFalse(result.isValid(), "Expected validation to fail due to an empty password.");
        assertEquals(1, result.getErrors().size());
        assertEquals("invalid.password", result.getErrors().get(0).getCode());
    }

    @Test
    void testInvalidEmail() {
        // UserDTO с пустым email
        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .nickname("validNickname")
                .password("validPassword")
                .email("") // Неправильный email
                .role(Role.USER)
                .build();

        // Валидация
        ValidationResult result = validator.isValid(userDTO);

        // Ожидаем, что валидация не пройдёт
        assertFalse(result.isValid(), "Expected validation to fail due to an empty email.");
        assertEquals(1, result.getErrors().size());
        assertEquals("invalid.email", result.getErrors().get(0).getCode());
    }
    @Test
    void testUserAlreadyRegistered() {
        // Создаем объект userDTO, который должен быть "зарегистрирован"
        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .nickname("existingNickname")
                .password("validPassword")
                .email("existingEmail")
                .role(Role.USER)
                .build();

        try (MockedStatic<UserService> mockedUserService = mockStatic(UserService.class)) {
            UserService mockUserService = mock(UserService.class);
            when(mockUserService.checkDoubleRegistration("existingNickname", "existingEmail"))
                    .thenReturn(Optional.of(userDTO));
            mockedUserService.when(UserService::getInstance).thenReturn(mockUserService);

            // When
            ValidationResult result = validator.isValid(userDTO);

            // Then
            assertFalse(result.isValid());
            assertEquals(1, result.getErrors().size());
            assertEquals("user.is.present", result.getErrors().get(0).getCode());
        }
    }
}
