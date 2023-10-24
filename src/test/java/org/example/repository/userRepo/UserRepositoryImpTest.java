package org.example.repository.userRepo;

import java.util.List;
import org.example.db.Database;
import org.example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImpTest {
    private UserRepositoryImp userRepository;
    private Database dbMock;
    private Connection connMock;
    private PreparedStatement preparedStatementMock;
    private ResultSet resultSetMock;

    @BeforeEach
    void setUp() {
        dbMock = mock(Database.class);
        connMock = mock(Connection.class);
        preparedStatementMock = mock(PreparedStatement.class);
        resultSetMock = mock(ResultSet.class);

        userRepository = new UserRepositoryImp();
        userRepository.db = dbMock; // Assuming you can access the fields directly, or else use setters

        when(dbMock.connectToDb()).thenReturn(connMock);
    }

    @Test
    void get() throws SQLException {
        when(connMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt("id")).thenReturn(1);
        // ... add the rest of the mock return values ...

        User result = userRepository.get(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        // ... add more assertions ...
    }

    @Test
    void add() throws SQLException {
        when(connMock.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt(1)).thenReturn(1);

        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("securepassword")
                .build();


        int result = userRepository.add(user);

        assertEquals(1, result);
    }

    @Test
    void update() throws SQLException {
        when(connMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);

        User user = User.builder()
                // ... set the user properties ...
                .build();

        userRepository.update(user);

        verify(preparedStatementMock).executeUpdate();
    }

    @Test
    void remove() throws SQLException {
        Statement stmtMock = mock(Statement.class);
        when(connMock.createStatement()).thenReturn(stmtMock);

        User user = User.builder()
                // ... set the user properties ...
                .build();

        userRepository.remove(user);

        verify(stmtMock).executeUpdate(anyString());
    }

    @Test
    void getAllUsers() throws SQLException {
        Statement stmtMock = mock(Statement.class);
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true, false);
        // ... mock the resultSet.getXXX methods ...

        List<User> result = userRepository.getAllUsers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        // ... add more assertions ...
    }
}



