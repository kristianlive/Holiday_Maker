package org.example.repository.tripRepo;

import org.example.db.Database;
import org.example.entity.CustomTrip;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomTripRepositoryImpTest {

    @InjectMocks
    private TripRepositoryImp tripRepository;

    @Mock
    private Database db;

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(db.connectToDb()).thenReturn(conn);
    }


    @Test
    public void get_withValidId_returnsTrip() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        CustomTrip expectedCustomTrip = new CustomTrip();
        expectedCustomTrip.setId(1L);
        expectedCustomTrip.setName("Test Trip");
        expectedCustomTrip.setPrice(100.0);

        when(resultSet.getLong("trip_id")).thenReturn(expectedCustomTrip.getId());
        when(resultSet.getString("name")).thenReturn(expectedCustomTrip.getName());
        when(resultSet.getDouble("price")).thenReturn(expectedCustomTrip.getPrice());

        CustomTrip result = tripRepository.get(1L);

        assertEquals(expectedCustomTrip, result);
    }

    @Test
    public void add_withValidTrip_insertsTrip() throws SQLException {
        CustomTrip customTrip = new CustomTrip();
        customTrip.setName("Test Trip");
        customTrip.setPrice(100.0);

        when(conn.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        tripRepository.add(customTrip);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void update_withValidTrip_updatesTrip() throws SQLException {
        CustomTrip customTrip = new CustomTrip();
        customTrip.setId(1L);
        customTrip.setName("Updated Trip");
        customTrip.setPrice(150.0);

        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        tripRepository.update(customTrip);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void remove_withValidTrip_removesTrip() throws SQLException {
        CustomTrip customTrip = new CustomTrip();
        customTrip.setId(1L);
        customTrip.setName("Test Trip");
        customTrip.setPrice(100.0);

        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        tripRepository.remove(customTrip);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void getAllTrips_returnsListOfTrips() throws SQLException {
        when(conn.createStatement()).thenReturn(statement); // Assuming you have mocked Statement as 'statement'
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        CustomTrip customTrip1 = new CustomTrip();
        customTrip1.setId(1L);
        customTrip1.setName("Trip One");
        customTrip1.setPrice(100.0);

        CustomTrip customTrip2 = new CustomTrip();
        customTrip2.setId(2L);
        customTrip2.setName("Trip Two");
        customTrip2.setPrice(150.0);

        when(resultSet.next()).thenReturn(true, true, false); // Simulate two rows in result set
        when(resultSet.getLong("trip_id")).thenReturn(customTrip1.getId(), customTrip2.getId());
        when(resultSet.getString("name")).thenReturn(customTrip1.getName(), customTrip2.getName());
        when(resultSet.getDouble("price")).thenReturn(customTrip1.getPrice(), customTrip2.getPrice());

        List<CustomTrip> result = tripRepository.getAllTrips();

        assertEquals(2, result.size());
        assertEquals(customTrip1, result.get(0));
        assertEquals(customTrip2, result.get(1));
    }
}