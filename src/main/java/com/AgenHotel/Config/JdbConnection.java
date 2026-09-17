package com.AgenHotel.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbConnection {

    private static final String URL = "jdbc:mysql://mysql:3306/agenhotel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "agenhotel_user";
    private static final String PASSWORD = "agenhotel123";

    private static JdbConnection instance;
    private JdbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL NÃO ENCONTRADO NO PROJETO", e);
        }
    }

    public static synchronized JdbConnection getInstance() {
        if (instance == null) {
            instance = new JdbConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

