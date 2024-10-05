package org.example.textingquest.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {

    private Integer id;
    private String nickname;
    private String password;
    private String email;
    private Role role;
}
