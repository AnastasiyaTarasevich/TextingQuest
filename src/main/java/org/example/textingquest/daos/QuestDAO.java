package org.example.textingquest.daos;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.textingquest.entities.Quest;
import org.example.textingquest.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestDAO implements DAO<Long, Quest> {


    private static final QuestDAO INSTANCE = new QuestDAO();

    private static final String GET_ALL_SQL =
            "SELECT id, title, description, image_path, prologue FROM quests";

    private static final String GET_BY_ID_SQL=
            "SELECT * FROM quests WHERE id = ?";

    public static QuestDAO getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public Optional findById(Integer id) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {
            preparedStatement.setString(1, String.valueOf(id));
            var resultSet = preparedStatement.executeQuery();

            Quest quest = null;
            if (resultSet.next()) {
                quest = buildEntity(resultSet);
            }
            return Optional.ofNullable(quest);
        }
    }
    @Override
    public boolean update(Quest o) {
        return false;
    }


    @SneakyThrows
    @Override
    public List findAll() {

        List<Quest> quests = new ArrayList<>();

        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(GET_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            Quest quest=null;
            while (resultSet.next()) {
                quest=buildEntity(resultSet);
                quests.add(quest);
            }
        }

        return quests;

    }

    private Quest buildEntity(ResultSet resultSet) throws SQLException {

        return Quest.builder()
                .id(resultSet.getObject("id",Integer.class))
                .title(resultSet.getObject("title",String.class))
                .description(resultSet.getObject("description",String.class))
                .imagePath(resultSet.getObject("image_path",String.class))
                .prologue(resultSet.getObject("prologue",String.class))
                .build();
    }



    @Override
    public Quest save(Quest o) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
