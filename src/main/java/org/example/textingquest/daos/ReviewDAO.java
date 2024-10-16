package org.example.textingquest.daos;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.textingquest.dtos.ReviewDTO;
import org.example.textingquest.entities.Quest;
import org.example.textingquest.entities.Review;
import org.example.textingquest.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDAO implements DAO<Long,Review>{
    
    private static final ReviewDAO INSTANCE=new ReviewDAO();

    private final String SAVE_SQL=
            "INSERT INTO reviews (user_id, name, rate, description, quest_id) VALUES (?, ?, ?, ?, ?)";
    private final String EXIST_REVIEW_SQL =
            "SELECT COUNT(*) FROM reviews WHERE user_id = ? AND quest_id = ?";

    private static final String GET_ALL_SQL =
            "SELECT r.id, r.name, r.description, r.rate, r.user_id, r.quest_id, " +
                    "u.nickname AS userNickname, q.title AS questName " +
                    "FROM reviews r " +
                    "JOIN user u ON r.user_id = u.id " +
                    "JOIN quests q ON r.quest_id = q.id";


    @Override
    public Object save(Review o) {
        try(var connection= ConnectionManager.open()) {
            var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, o.getUser_id());
            preparedStatement.setObject(2, o.getName());
            preparedStatement.setObject(3, o.getRate());
            preparedStatement.setObject(4, o.getDescription());
            preparedStatement.setObject(5,o.getQuest_id());

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return o;
    }

    @SneakyThrows
    public boolean existsReviewForQuest(int userId, int questId) {

        try (var connection = ConnectionManager.open();
             var statement = connection.prepareStatement(EXIST_REVIEW_SQL)) {

            statement.setInt(1, userId);
            statement.setInt(2, questId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // Возвращаем true, если существует хотя бы один отзыв
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Если возникла ошибка или не найдено ни одного отзыва
    }

    public static ReviewDAO getInstance() {
        return INSTANCE;
    }
    @Override
    public boolean update(Review o) {
        return false;
    }

    @SneakyThrows
    @Override
    public List findAll() {
        List<ReviewDTO> reviews = new ArrayList<>();

        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(GET_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            ReviewDTO review=null;
            while (resultSet.next()) {
                review=buildEntity(resultSet);
                reviews.add(review);
            }
        }

        return reviews;

    }

    private ReviewDTO buildEntity(ResultSet resultSet) throws SQLException {

        return ReviewDTO.builder()
                .id(resultSet.getObject("id",Integer.class))
                .userNickname(resultSet.getString("userNickname"))
                .questName(resultSet.getString("questName"))
                .name(resultSet.getObject("name",String.class))
                .user_id(resultSet.getObject("user_id",Integer.class))
                .description(resultSet.getObject("description",String.class))
                .quest_id(resultSet.getObject("quest_id",Integer.class))
                .rate(resultSet.getObject("rate",Integer.class))
                .build();
    }

    @Override
    public Object findById(Integer id) {
        return null;
    }



    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
