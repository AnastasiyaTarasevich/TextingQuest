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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/startQuest")
public class StartQuestServlet extends HttpServlet {

    private final ChapterDAO chapterDAO = ChapterDAO.getInstance();

    private final QuestionDAO questionDAO = QuestionDAO.getInstance();

    private final AnswerDAO answerDAO=AnswerDAO.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // Пытаемся получить questId из параметра запроса
        String questIdParam = req.getParameter("questId");
        Integer questId;

        // Если параметр questId не передан, пробуем получить его из сессии
        if (questIdParam != null) {
            questId = Integer.parseInt(questIdParam);
        } else {
            questId = (Integer) req.getSession().getAttribute("questId");
        }
        Integer currentChapterNumber = (Integer) req.getSession().getAttribute("currentChapterNumber");

        // Если текущий номер главы не установлен, начинаем с первой
        if (currentChapterNumber == null) {
            currentChapterNumber = 1;
        }

        // Находим текущую главу
        Optional<Chapter> chapter = chapterDAO.findByIdAndChapterId(questId, currentChapterNumber);

        if (chapter.isPresent()) {
            // Находим вопросы текущей главы
            List<Question> questions = questionDAO.getQuestionsByChapterId(chapter.get().getId());

            // Если вопросов нет, перейти к следующей главе
            if (questions.isEmpty()) {
                // Если вопросов нет, то это ознакомительная глава
                req.setAttribute("currentChapter", chapter.get());
                req.setAttribute("isIntroductoryChapter", true); // Флаг для JSP
                req.getSession().setAttribute("questId", questId);
                req.getSession().setAttribute("currentChapterNumber", chapter.get().getChapter_number());
                // Отправляем пользователя на страницу с главой и кнопкой "Следующая глава"
                req.getRequestDispatcher("startQuest.jsp").forward(req, resp);
            } else {
                //
                // Отправляем данные на страницу для отображения
                Integer currentQuestionId = (Integer) req.getSession().getAttribute("currentQuestionId");
                if (currentQuestionId == null) {
                    currentQuestionId = questions.get(0).getId(); // Начинаем с первого вопроса
                    req.getSession().setAttribute("currentQuestionId", currentQuestionId);
                }

                Optional<Question> currentQuestion = questionDAO.findById(currentQuestionId);

                if (currentQuestion.isPresent()) {
                    List<Answer> answers = answerDAO.getAnswersByQuestionId(currentQuestionId);
                    currentQuestion.get().setAnswers(answers);
                    req.setAttribute("currentChapter", chapter.get());
                    req.setAttribute("currentQuestion", currentQuestion.get());
                    String previousAnswerDescription = (String) req.getAttribute("previousAnswerDescription");
                    req.setAttribute("previousAnswerDescription", previousAnswerDescription);
                    req.getRequestDispatcher("startQuest.jsp").forward(req, resp);
                }
            }
        } else {
            // Завершение квеста
            resp.sendRedirect("endOfQuest.jsp");
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




