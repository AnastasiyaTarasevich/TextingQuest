package org.example.textingquest.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User {

    private Integer id;
    private String nickname;
    private String password;
    private String email;
    private Role role;
}
