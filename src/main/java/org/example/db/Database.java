package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    Connection conn = null;

    public Connection connectToDb() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://161.97.144.27:8013/holiday-maker?user=root&password=yellowlionyells&serverTimezone=UTC");
            String query = "CREATE TABLE IF NOT EXISTS users (\n" +
                    "    id INT PRIMARY KEY,\n" +
                    "    first_name VARCHAR(255) NOT NULL,\n" +
                    "    last_name VARCHAR(255) NOT NULL,\n" +
                    "    email VARCHAR(255) NOT NULL,\n" +
                    "    password VARCHAR(255) NOT NULL\n" +
                    ");";

//            conn.createStatement().executeUpdate(query)
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);

//            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
//            while (rs.next()) {
//                System.out.println(rs.getString("first_name"));
//            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return conn;
    }
}
