package org.example.textingquest.services;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.daos.UserDAO;
import org.example.textingquest.dtos.UserDTO;
import org.example.textingquest.exceptions.ValidationException;
import org.example.textingquest.mappers.UserDTOMapper;
import org.example.textingquest.mappers.UserMapper;
import org.example.textingquest.validators.CreateUserValidator;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService INSTANCE=new UserService();
    private final UserDTOMapper userDTOMapper=UserDTOMapper.getInstance();

    private final UserDAO userDAO=UserDAO.getInstance();

    private final CreateUserValidator createUserValidator=CreateUserValidator.getInstance();

    private final UserMapper userMapper=UserMapper.getInstance();
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

    public Optional<UserDTO> login(String nickname, String password) {
        return userDAO.findByNicknameAndPassword(nickname,password).map(userMapper::mapFrom);
    }
}


