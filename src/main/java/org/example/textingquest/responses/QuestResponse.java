package org.example.textingquest.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.textingquest.entities.Answer;
import org.example.textingquest.entities.Chapter;
import org.example.textingquest.entities.Question;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class QuestResponse {
    private Integer questId;
    private Integer currentChapterNumber;
    private Chapter currentChapter;
    private Chapter newChapter;
    private Question currentQuestion;
    private String previousAnswerDescription;
    private Question nextQuestion;
    private boolean isIntroductoryChapter;
    private boolean isNewChapterFlag;
    private boolean isTheEnd;
}
