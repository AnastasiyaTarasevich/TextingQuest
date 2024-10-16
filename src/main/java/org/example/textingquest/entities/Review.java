package org.example.textingquest.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Review {

    private Integer id;
    private Integer user_id;
    private String name;
    private Integer rate;
    private String description;
    private Integer quest_id;
}
