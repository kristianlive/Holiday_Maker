package org.example.repository.userRepo;

import org.junit.jupiter.api.Test;
import org.example.db.Database;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

public class UserRepositoryImpTest {

    @Test
    public void testConnectionAndTableExistence() {
        Database db = new Database();
        try (Connection conn = db.connectToDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'users'")) {

            // Check connection validity
            assertTrue(conn.isValid(1), "Connection is not valid");

            // Check if 'users' table exists
            assertTrue(rs.next(), "Users table does not exist");

        } catch (SQLException ex) {
            fail("Failed to execute test due to: " + ex.getMessage());
        }
    }
}

