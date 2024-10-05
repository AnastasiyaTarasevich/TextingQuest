package org.example.textingquest.daos;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.entities.User;
import org.example.textingquest.utils.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class UserDAO implements DAO<Long, User> {

    private static final UserDAO INSTANCE=new UserDAO();

    private static final String SAVE_SQL=
            "INSERT INTO user (nickname, password, email, role) VALUES (?, ?, ?, ?)";


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
}
