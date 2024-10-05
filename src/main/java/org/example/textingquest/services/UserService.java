package org.example.textingquest.services;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.daos.UserDAO;
import org.example.textingquest.dtos.UserDTO;
import org.example.textingquest.exceptions.ValidationException;
import org.example.textingquest.mappers.UserDTOMapper;
import org.example.textingquest.validators.CreateUserValidator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    public static final UserService INSTANCE=new UserService();
    private final UserDTOMapper userDTOMapper=UserDTOMapper.getInstance();

    private final UserDAO userDAO=UserDAO.getInstance();

    private final CreateUserValidator createUserValidator=CreateUserValidator.getInstance();
    public void createUser(UserDTO userDTO)
    {
        var validationResult=createUserValidator.isValid(userDTO);
        if(!validationResult.isValid())
        {
            throw new ValidationException(validationResult.getErrors());
        }
        var user = userDTOMapper.mapFrom(userDTO);
        userDAO.save(user);
    }
    public static UserService getInstance() {
        return INSTANCE;
    }
}


