package org.example.textingquest.dtos;

import lombok.Builder;
import lombok.Value;
import org.example.textingquest.entities.Role;

@Builder
@Value
public class UserDTO {
    Integer id;
    String nickname;
    String password;
    String email;
    Role role;

}
