package org.example.textingquest.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionManager {
private static final String URL_KEY="db.url";
private static final String USERNAME_KEY="db.username";
private static final String PASSWORD_KEY="db.password";



public static Connection open() throws SQLException, ClassNotFoundException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
            PropertiesUtil.get(USERNAME_KEY),
            PropertiesUtil.get(PASSWORD_KEY));
}
}
