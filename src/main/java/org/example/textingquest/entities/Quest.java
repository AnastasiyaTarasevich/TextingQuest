package org.example.textingquest.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Quest {

    private Integer id;
    private String title;
    private String description;
    private String imagePath;
    private String prologue;
}
