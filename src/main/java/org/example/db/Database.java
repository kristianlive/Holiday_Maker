package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    Connection conn = null;

    public void connectToDb(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://161.97.144.27:8013/holiday-maker?user=root&password=yellowlionyells&serverTimezone=UTC");
            System.out.println("Connected" + conn);
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}
