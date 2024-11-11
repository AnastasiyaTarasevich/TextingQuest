package org.example.textingquest.controllers;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.textingquest.entities.Answer;
import org.example.textingquest.entities.Chapter;
import org.example.textingquest.entities.Question;
import org.example.textingquest.responses.QuestResponse;
import org.example.textingquest.services.QuestService;

import java.io.IOException;

import java.util.Optional;



@WebServlet("/startQuest")
public class StartQuestServlet extends HttpServlet {


    private final QuestService questService=QuestService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Integer questId = questService.parseQuestId(req);
        Integer currentChapterNumber = questService.getCurrentChapterNumber(req);
        Integer currentQuestionId = questService.getCurrentQuestionId(req);

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
        req.setAttribute("newChapter", questResponse.getNewChapter());
         req.setAttribute("isNewChapter", questResponse.isNewChapterFlag());
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

            String answerIdParam = req.getParameter("answerId");

            if (answerIdParam != null) {
                Integer answerId = Integer.parseInt(answerIdParam);
                Answer selectedAnswer = questService.getAnswerById(answerId);

                if (selectedAnswer != null) {

                    if (selectedAnswer.getNext_question_id() != null) {
                        processNextQuestion(req, resp, selectedAnswer, currentChapterNumber);
                    } else {

                        endQuest(req, resp, selectedAnswer);
                    }
                    return;
                }
            }

            goToNextChapter(req, resp, questId, currentChapterNumber);
        }


        // Метод для обработки перехода на следующий вопрос
        private void processNextQuestion(HttpServletRequest req, HttpServletResponse resp, Answer selectedAnswer, Integer currentChapterNumber) throws ServletException, IOException {
            Optional<Question> nextQuestion = questService.getNextQuestionByAnswer(selectedAnswer);
            Integer nextQuestionChapterId = nextQuestion.get().getChapter_id();

            if (!nextQuestionChapterId.equals(currentChapterNumber)) {
                Chapter nextChapter = questService.getChapterById(nextQuestionChapterId);
                req.setAttribute("newChapter", nextChapter);
                req.setAttribute("isNewChapter", true);
            }
            req.setAttribute("previousAnswerDescription", selectedAnswer.getDescription());
            req.getSession().setAttribute("currentQuestionId", selectedAnswer.getNext_question_id());
            doGet(req, resp);
        }


        private void endQuest(HttpServletRequest req, HttpServletResponse resp, Answer selectedAnswer) throws ServletException, IOException {
            req.setAttribute("previousAnswerDescription", selectedAnswer.getDescription());
            req.getRequestDispatcher("endOfQuest.jsp").forward(req, resp);
        }


        private void goToNextChapter(HttpServletRequest req, HttpServletResponse resp, Integer questId, Integer currentChapterNumber) throws IOException, ServletException {
            Optional<Chapter> nextChapter = questService.findNextChapter(questId, currentChapterNumber);

            if (nextChapter.isPresent()) {
                req.getSession().setAttribute("questId", questId);
                req.getSession().setAttribute("currentChapterNumber", nextChapter.get().getChapter_number());
                doGet(req, resp);
            } else {
                resp.sendRedirect("endOfQuest.jsp");
            }
        }

    }




