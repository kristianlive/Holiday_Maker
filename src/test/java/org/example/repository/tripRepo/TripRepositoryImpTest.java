package org.example.repository.tripRepo;

import org.example.db.Database;
import org.example.entity.CustomTrip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TripRepositoryImpTest {

    @InjectMocks
    TripRepositoryImp tripRepository;

    @Mock
    Database db;

    @Mock
    Connection conn;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    Statement statement;

    @Mock
    ResultSet rs;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(db.connectToDb()).thenReturn(conn);
    }

    @Test
    void getReturnsCustomTrip() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        CustomTrip result = tripRepository.get(1L);

        assertNotNull(result);
    }

    @Test
    void getReturnsNullWhenNotFound() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        CustomTrip result = tripRepository.get(1L);

        assertNull(result);
    }

    @Test
    void addCustomTrip() throws SQLException {
        when(conn.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getLong(1)).thenReturn(1L);

        CustomTrip customTrip = new CustomTrip();
        tripRepository.add(customTrip);

        assertNotNull(customTrip.getId());
        assertEquals(1L, customTrip.getId());
    }

    @Test
    void addBooking() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);

        tripRepository.addBooking(1, 1L);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void updateCustomTrip() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        CustomTrip customTrip = new CustomTrip();
        customTrip.setId(1L);
        tripRepository.update(customTrip);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void removeCustomTrip() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        CustomTrip customTrip = new CustomTrip();
        customTrip.setId(1L);
        tripRepository.remove(customTrip);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void getAllTrips() throws SQLException {
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);

        List<CustomTrip> result = tripRepository.getAllTrips();

        assertFalse(result.isEmpty());
    }

    // You can add more tests, especially for error scenarios and testing the extraction of more complex data.
}