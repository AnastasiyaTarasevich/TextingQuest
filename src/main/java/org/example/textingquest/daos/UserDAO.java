package org.example.textingquest.daos;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.textingquest.entities.Role;
import org.example.textingquest.entities.User;
import org.example.textingquest.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class UserDAO implements DAO<Long, User> {

    private static final UserDAO INSTANCE=new UserDAO();

    private static final String SAVE_SQL=
            "INSERT INTO user (nickname, password, email, role) VALUES (?, ?, ?, ?)";

    private static final String GET_BY_NICKNAME_AND_PASSWORD_SQL=
            "SELECT * FROM user WHERE nickname = ? AND password = ?";


    public static UserDAO getInstance() {
        return INSTANCE;
    }


    @Override
    public boolean update(User o) {
        return false;
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public Optional findById(User id) {
        return Optional.empty();
    }

    @Override
    public Object save(User o) {
        try(var connection= ConnectionManager.open()) {
            var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, o.getNickname());
            preparedStatement.setObject(2, o.getPassword());
            preparedStatement.setObject(3, o.getEmail());
            preparedStatement.setObject(4, o.getRole().name());

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return o;
    }

    @Override
    public boolean delete(User id) {
        return false;
    }

    @SneakyThrows
    public Optional <User> findByNicknameAndPassword(String nickname, String password)
    {
        try(var connection= ConnectionManager.open();
            var preparedStatement = connection.prepareStatement(GET_BY_NICKNAME_AND_PASSWORD_SQL)) {
            preparedStatement.setString(1, nickname);
            preparedStatement.setString(2, password);


            var resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }


            return Optional.ofNullable(user);
        }


    }

    private User buildEntity(ResultSet resultSet) throws SQLException {

        return User.builder()
                .id(resultSet.getObject("id",Integer.class))
                .nickname(resultSet.getObject("nickname",String.class))
                .email(resultSet.getObject("email",String.class))
                .password(resultSet.getObject("password",String.class))
                .role(Role.find(resultSet.getObject("role",String.class)).orElse(null))
                .build();
    }
}
