package org.example.textingquest.mappers;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.dtos.UserDTO;
import org.example.textingquest.entities.User;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class UserMapper implements Mapper<UserDTO, User> {
    
    private static final UserMapper INSTANCE=new UserMapper();
    @Override
    public UserDTO mapFrom(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
