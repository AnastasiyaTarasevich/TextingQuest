package org.example.textingquest.dtos;


import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ReviewDTO {
     Integer id;
     Integer user_id;
     String name;
     Integer rate;
     String description;
     Integer quest_id;
     String userNickname;
     String questName;
}
