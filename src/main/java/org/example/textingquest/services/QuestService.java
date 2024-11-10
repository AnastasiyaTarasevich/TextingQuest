package org.example.textingquest.services;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.daos.AnswerDAO;
import org.example.textingquest.daos.ChapterDAO;
import org.example.textingquest.daos.QuestDAO;
import org.example.textingquest.daos.QuestionDAO;
import org.example.textingquest.entities.Answer;
import org.example.textingquest.entities.Chapter;
import org.example.textingquest.entities.Question;
import org.example.textingquest.mappers.QuestDTOMapper;
import org.example.textingquest.responses.QuestResponse;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestService {
    
    private static final QuestService INSTANCE=new QuestService();
    private static final ChapterDAO chapterDAO=ChapterDAO.getInstance();
    private static final QuestDTOMapper questDTOMapper=QuestDTOMapper.getInstance();
    private static final QuestionDAO questionDAO=QuestionDAO.getInstance();
    private static  final AnswerDAO answerDAO=AnswerDAO.getInstance();

    private final QuestDAO questDAO=QuestDAO.getInstance();

    public static QuestService getInstance() {
        return INSTANCE;
    }

    public Optional findById(Integer id) {

        return questDAO.findById(id);
    }

    public QuestResponse processQuest(Integer questId, Integer currentChapterNumber,
                                      Integer currentQuestionId)
    {
        QuestResponse questResponse=new QuestResponse();
        questResponse.setQuestId(questId);
        Optional<Chapter> chapter = chapterDAO.findByIdAndChapterId(questId, currentChapterNumber);

        if (!chapter.isPresent()) {
            questResponse.setTheEnd(true);
            return questResponse;
        }
        questResponse.setCurrentChapter(chapter.get());

        List<Question> questions = questionDAO.getQuestionsByChapterId(chapter.get().getId());
        if (questions.isEmpty()) {
            questResponse.setIntroductoryChapter(true);
        }else {
            setCurrentQuestionAndAnswers(questResponse, questions, currentQuestionId);
        }
        return questResponse;
    }
    private void setCurrentQuestionAndAnswers(QuestResponse response, List<Question> questions, Integer currentQuestionId) {
        Question currentQuestion;

        if (currentQuestionId != null) {
            // Ищем вопрос по ID
            Optional<Question> optionalQuestion = questionDAO.findById(currentQuestionId)
                    .map(Question.class::cast); // Преобразуем результат в Optional<Question>
            currentQuestion = optionalQuestion.orElse(questions.get(0));
        } else {
            // Если ID вопроса отсутствует, берем первый вопрос
            currentQuestion = questions.get(0);
        }

        response.setCurrentQuestion(currentQuestion);

        // Получаем ответы для текущего вопроса
        List<Answer> answers = answerDAO.getAnswersByQuestionId(currentQuestion.getId());
        currentQuestion.setAnswers(answers);
    }
    public Integer parseQuestId(HttpServletRequest req) {
        String questIdParam = req.getParameter("questId");
        return questIdParam != null ? Integer.parseInt(questIdParam) : (Integer) req.getSession().getAttribute("questId");
    }

    public Integer getCurrentChapterNumber(HttpServletRequest req) {
        Integer chapterNumber = (Integer) req.getSession().getAttribute("currentChapterNumber");
        return chapterNumber != null ? chapterNumber : 1;
    }

    public Integer getCurrentQuestionId(HttpServletRequest req) {
        return (Integer) req.getSession().getAttribute("currentQuestionId");
    }

}
