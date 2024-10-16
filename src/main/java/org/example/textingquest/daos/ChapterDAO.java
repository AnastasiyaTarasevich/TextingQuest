package org.example.textingquest.daos;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.textingquest.entities.Chapter;
import org.example.textingquest.entities.Quest;
import org.example.textingquest.entities.User;
import org.example.textingquest.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChapterDAO implements DAO<Long, Chapter> {

    private static final ChapterDAO INSTANCE = new ChapterDAO();


    private static final String GET_BY_ID_SQL=
            "SELECT * FROM chapters WHERE id = ?";
    private static final String GET_BY_ID_AND_CHAPTER =
            "SELECT * FROM chapters WHERE quest_id = ? AND chapter_number = ?";
    private static final String GET_NEXT =
            "SELECT * FROM chapters WHERE quest_id = ? AND chapter_number > ? ORDER BY chapter_number ASC LIMIT 1";

    public static ChapterDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Chapter o) {
        return false;
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @SneakyThrows
    @Override
    public Chapter findById(Integer id) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {
            preparedStatement.setString(1, String.valueOf(id));
            var resultSet = preparedStatement.executeQuery();

            Chapter chapter = null;
            if (resultSet.next()) {
                chapter = buildEntity(resultSet);
            }
            return chapter;
        }
    }

    @Override
    public Object save(Chapter o) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
    @SneakyThrows
    public Optional<Chapter> findNextChapter(Integer questId, Integer currentChapterNumber) {

        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(GET_NEXT)) {

            preparedStatement.setInt(1, questId);
            preparedStatement.setInt(2, currentChapterNumber);
            var resultSet = preparedStatement.executeQuery();

            Chapter chapter = null;
            if (resultSet.next()) {
                chapter = buildEntity(resultSet);
            }

            return Optional.ofNullable(chapter);
        }
    }
    @SneakyThrows
    public Optional<Chapter> findByIdAndChapterId(Integer id, Integer chapter_number) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(GET_BY_ID_AND_CHAPTER)) {
            System.out.println("ID: " + id);  // для отладки
            System.out.println("Chapter Number: " + chapter_number);  // для отладки
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, chapter_number);
            var resultSet = preparedStatement.executeQuery();

            Chapter chapter = null;
            if (resultSet.next()) {
                chapter = buildEntity(resultSet);
            }
            return Optional.ofNullable(chapter);
        }

    }

    private Chapter buildEntity(ResultSet resultSet) throws SQLException {

        return Chapter.builder()
                .id(resultSet.getObject("id", Integer.class))
                .quest_id(resultSet.getObject("quest_id", Integer.class))
                .title(resultSet.getObject("title", String.class))
                .chapter_number(resultSet.getObject("chapter_number", Integer.class))
                .content(resultSet.getObject("content", String.class))
                .build();
    }
}
