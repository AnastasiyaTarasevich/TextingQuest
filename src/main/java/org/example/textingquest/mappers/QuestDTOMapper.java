package org.example.textingquest.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.dtos.QuestDTO;

import org.example.textingquest.entities.Quest;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestDTOMapper implements Mapper<Quest, QuestDTO> {

    private static final QuestDTOMapper INSTANCE=new QuestDTOMapper();

    public static QuestDTOMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public Quest mapFrom(QuestDTO questDTO) {
        return Quest.builder()
                .id(questDTO.getId())
                .title(questDTO.getTitle())
                .description(questDTO.getDescription())
                .imagePath(questDTO.getImagePath())
                .prologue(questDTO.getPrologue())
                .build();
    }


}
