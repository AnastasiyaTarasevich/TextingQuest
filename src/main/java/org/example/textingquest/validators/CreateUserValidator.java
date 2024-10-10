package org.example.textingquest.validators;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.dtos.UserDTO;
import org.example.textingquest.entities.Role;
import org.example.textingquest.exceptions.ValidationException;
import org.example.textingquest.services.UserService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<UserDTO>{

    private static final CreateUserValidator INSTANCE=new CreateUserValidator();

    public static CreateUserValidator getInstance()
    {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(UserDTO object) {
       var validationResult=new ValidationResult();
        if (object.getNickname() == null || object.getNickname().isEmpty()) {
            validationResult.add(Error.of("invalid.nickname", "Nickname is invalid"));
        }

        if(object.getNickname() == null || object.getPassword().isEmpty())
        {
            validationResult.add(Error.of("invalid.password","Password is invalid"));

        }
        if(object.getNickname() == null || object.getEmail().isEmpty())
        {
            validationResult.add(Error.of("invalid.email","Email is invalid"));

        }
        if (UserService.getInstance().checkDoubleRegistration(object.getNickname(), object.getEmail()).isPresent()) {
            validationResult.add(Error.of("user.is.present", "User with this nickname and email has already registered"));
        }
        return validationResult;
    }
}
