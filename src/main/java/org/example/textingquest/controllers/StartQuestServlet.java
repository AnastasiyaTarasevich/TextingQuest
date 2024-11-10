package org.example.textingquest.controllers;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.textingquest.daos.AnswerDAO;
import org.example.textingquest.daos.ChapterDAO;
import org.example.textingquest.daos.QuestionDAO;
import org.example.textingquest.entities.Answer;
import org.example.textingquest.entities.Chapter;
import org.example.textingquest.entities.Question;
import org.example.textingquest.responses.QuestResponse;
import org.example.textingquest.services.QuestService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;



@WebServlet("/startQuest")
public class StartQuestServlet extends HttpServlet {

    private final ChapterDAO chapterDAO = ChapterDAO.getInstance();

    private final QuestionDAO questionDAO = QuestionDAO.getInstance();

    private final AnswerDAO answerDAO=AnswerDAO.getInstance();

    private final QuestService questService=QuestService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Integer questId = questService.parseQuestId(req);
        Integer currentChapterNumber = questService.getCurrentChapterNumber(req);
        Integer currentQuestionId = questService.getCurrentQuestionId(req);

        // Получение данных квеста через сервис
        QuestResponse questResponse = questService.processQuest(questId, currentChapterNumber, currentQuestionId);


        setupRequestAttributes(req, questResponse);
        if (questResponse.isIntroductoryChapter())
        {
            req.getRequestDispatcher("startQuest.jsp").forward(req, resp);
        }
        if (questResponse.isTheEnd()) {
            resp.sendRedirect("endOfQuest.jsp");
        } else {
            req.getRequestDispatcher("startQuest.jsp").forward(req, resp);
        }
    }



    private void setupRequestAttributes(HttpServletRequest req, QuestResponse questResponse) {
        req.setAttribute("currentChapter", questResponse.getCurrentChapter());
        req.setAttribute("currentQuestion", questResponse.getCurrentQuestion());
        req.setAttribute("isIntroductoryChapter", questResponse.isIntroductoryChapter());
        String previousAnswerDescription = (String) req.getAttribute("previousAnswerDescription");
                    req.setAttribute("previousAnswerDescription", previousAnswerDescription);

        // Обновление параметров сессии
        req.getSession().setAttribute("questId", questResponse.getQuestId());
        req.getSession().setAttribute("currentChapterNumber", questResponse.getCurrentChapter().getChapter_number());
        if (!questResponse.isIntroductoryChapter()) {
            req.getSession().setAttribute("currentQuestionId", questResponse.getCurrentQuestion().getId());
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Integer questId = (Integer) req.getSession().getAttribute("questId");
        Integer currentChapterNumber = (Integer) req.getSession().getAttribute("currentChapterNumber");


        // Проверка, есть ли параметры ответа
        String answerIdParam = req.getParameter("answerId");

        if (answerIdParam != null) {

                Integer answerId = Integer.parseInt(answerIdParam);
                Answer selectedAnswer = answerDAO.findById(answerId);

            if (selectedAnswer != null) {
                // Проверка на наличие следующего вопроса
                if (selectedAnswer.getNext_question_id() != null) {
                    // Обновляем текущий вопрос в сессии на следующий вопрос
                    Optional<Question> nextQuestion = questionDAO.findById(selectedAnswer.getNext_question_id());
                    Integer nextQuestionChapterId = nextQuestion.get().getChapter_id();

                    Chapter nextChapter = chapterDAO.findById(nextQuestionChapterId);
                    if (!nextQuestionChapterId.equals(currentChapterNumber))
                    {
                        req.setAttribute("newChapter", nextChapter); // Описание новой главы
                        req.setAttribute("isNewChapter", true); // Флаг перехода в новую главу
                        req.setAttribute("previousAnswerDescription", selectedAnswer.getDescription());
                        req.getSession().setAttribute("currentQuestionId", selectedAnswer.getNext_question_id());
                        doGet(req, resp); // Перенаправляем на следующий вопрос
                    }else {
                        req.setAttribute("previousAnswerDescription", selectedAnswer.getDescription());
                        req.getSession().setAttribute("currentQuestionId", selectedAnswer.getNext_question_id());
                        doGet(req, resp); // Перенаправляем на следующий вопрос
                    }
                    return;
                } else {
                    // Если у ответа нет следующего вопроса, считаем квест завершённым
                    req.setAttribute("previousAnswerDescription", selectedAnswer.getDescription());
                    req.getRequestDispatcher("endOfQuest.jsp").forward(req, resp);  // Перенаправляем на страницу завершения квеста
                    return;
                }
            }

        }
            Optional<Chapter> nextChapter = chapterDAO.findNextChapter(questId, currentChapterNumber);

            if (nextChapter.isPresent()) {
                req.getSession().setAttribute("questId", questId);
                req.getSession().setAttribute("currentChapterNumber", nextChapter.get().getChapter_number());
                doGet(req, resp);
            } else {
                resp.sendRedirect("endOfQuest.jsp"); // Если следующей главы нет
            }
        }
    }




