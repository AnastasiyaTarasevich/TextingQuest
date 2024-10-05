package org.example.textingquest.dtos;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserDTO {

    String nickname;
    String password;
    String email;

}
