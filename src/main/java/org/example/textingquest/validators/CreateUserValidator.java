package org.example.textingquest.validators;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.dtos.UserDTO;
import org.example.textingquest.entities.Role;

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
       if(object.getNickname().isEmpty())
       {
           validationResult.add(Error.of("invalid.nickname","Nickname is invalid"));

       }
        if(object.getPassword().isEmpty())
        {
            validationResult.add(Error.of("invalid.password","Password is invalid"));

        }
        if(object.getEmail().isEmpty())
        {
            validationResult.add(Error.of("invalid.email","Email is invalid"));

        }

        return validationResult;
    }
}
