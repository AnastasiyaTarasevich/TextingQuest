package org.example.textingquest.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.dtos.UserDTO;
import org.example.textingquest.entities.Role;
import org.example.textingquest.entities.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDTOMapper implements Mapper<User, UserDTO> {

    public static final UserDTOMapper INSTANCE=new UserDTOMapper();

    @Override
    public User mapFrom(UserDTO userDTO) {
        return User.builder()
                .nickname(userDTO.getNickname())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .role(Role.USER)
                .build();
    }

    public static UserDTOMapper getInstance() {
        return INSTANCE;
    }
}
