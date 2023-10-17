package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    Connection conn = null;
    private static Database instance = null;

    public Connection connectToDb() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://161.97.144.27:8013/holiday-maker?user=root&password=yellowlionyells&serverTimezone=UTC");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return conn;
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
}
