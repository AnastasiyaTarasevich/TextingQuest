package org.example.textingquest.daos;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.textingquest.entities.Answer;
import org.example.textingquest.entities.Question;
import org.example.textingquest.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerDAO implements DAO<Long,Answer>{


    private static final AnswerDAO INSTANCE=new AnswerDAO();

    private final String GET_ANSWERS_BY_QUESTION=
            "SELECT * FROM answers WHERE question_id = ?";

    private final String GET_BY_ID=
            "SELECT * FROM answers WHERE id = ?";
    public static AnswerDAO getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public List<Answer> getAnswersByQuestionId(Integer questionId) {
        List<Answer> answers = new ArrayList<>();


        try (var conn = ConnectionManager.open();
             var preparedStatement = conn.prepareStatement(GET_ANSWERS_BY_QUESTION)) {
            preparedStatement.setInt(1, questionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Answer answer=null;
            while (resultSet.next()) {
                answer=buildEntity(resultSet);
                answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }
    private Answer buildEntity(ResultSet resultSet) throws SQLException {

        return Answer.builder()
                .id(resultSet.getObject("id",Integer.class))
                .question_id(resultSet.getObject("question_id",Integer.class))
                .answer_text(resultSet.getObject("answer_text",String.class))
                .next_question_id(resultSet.getObject("next_question_id", Integer.class))
                .description(resultSet.getObject("description",String.class))
                .build();
    }

    @Override
    public boolean update(Answer o) {
        return false;
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @SneakyThrows
    @Override
    public Answer findById(Integer id) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setString(1, String.valueOf(id));
            var resultSet = preparedStatement.executeQuery();

            Answer answer = null;
            if (resultSet.next()) {
                answer = buildEntity(resultSet);
            }
            return answer;
        }
    }

    @Override
    public Object save(Answer o) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
