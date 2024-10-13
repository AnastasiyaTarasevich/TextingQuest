package org.example.textingquest.daos;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.textingquest.entities.Answer;
import org.example.textingquest.entities.Chapter;
import org.example.textingquest.entities.Quest;
import org.example.textingquest.entities.Question;
import org.example.textingquest.utils.ConnectionManager;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionDAO implements DAO<Long, Question>{
    
    private static final QuestionDAO INSTANCE=new QuestionDAO();

    public static QuestionDAO getInstance() {
        return INSTANCE;
    }


    private static final String GET_BY_ID_SQL=
            "SELECT * FROM questions WHERE id = ?";
    private static final String GET_ALL_BY_CHAPTER=
            "SELECT * FROM questions WHERE chapter_id = ?";
    @Override
    public boolean update(Question o) {
        return false;
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @SneakyThrows
    @Override
    public Optional findById(Integer id) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {
            preparedStatement.setString(1, String.valueOf(id));
            var resultSet = preparedStatement.executeQuery();

            Question question = null;
            if (resultSet.next()) {
                question = buildEntity(resultSet);
            }
            return Optional.ofNullable(question);
        }
    }

    @Override
    public Object save(Question o) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @SneakyThrows
    public List getQuestionsByChapterId(Integer id)
    {

        List<Question> questions = new ArrayList<>();
        try(var connection= ConnectionManager.open();
            var preparedStatement=connection.prepareStatement(GET_ALL_BY_CHAPTER))
        {
            preparedStatement.setString(1, String.valueOf(id));
            var resultSet=preparedStatement.executeQuery();

            Question question=null;
            while (resultSet.next()) {
                question=buildEntity(resultSet);
                questions.add(question);
            }
            return questions;
        }
    }
    private Question buildEntity(ResultSet resultSet) throws SQLException {

        return Question.builder()
                .id(resultSet.getObject("id",Integer.class))
                .chapter_id(resultSet.getObject("chapter_id",Integer.class))
                .question_text(resultSet.getObject("question_text",String.class))
                .build();
    }




}
