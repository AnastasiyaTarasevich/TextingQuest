package org.example.textingquest.dtos;


import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class QuestDTO {

    Integer id;
    String title;
    String description;
    String imagePath;
    String prologue;
}
