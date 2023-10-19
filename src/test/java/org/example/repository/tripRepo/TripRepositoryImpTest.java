package org.example.repository.tripRepo;

import org.example.db.Database;
import org.example.entity.Trip;
import org.example.entity.TypeOfTrip;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TripRepositoryImpTest {

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

        Trip expectedTrip = new Trip();
        expectedTrip.setId(1L);
        expectedTrip.setName("Test Trip");
        expectedTrip.setType(TypeOfTrip.PACKAGETRIP); // Replace SOME_TYPE with an actual type
        expectedTrip.setPrice(100.0);

        when(resultSet.getLong("trip_id")).thenReturn(expectedTrip.getId());
        when(resultSet.getString("name")).thenReturn(expectedTrip.getName());
        when(resultSet.getString("type")).thenReturn(expectedTrip.getType().toString());
        when(resultSet.getDouble("price")).thenReturn(expectedTrip.getPrice());

        Trip result = tripRepository.get(1L);

        assertEquals(expectedTrip, result);
    }

    @Test
    public void add_withValidTrip_insertsTrip() throws SQLException {
        Trip trip = new Trip();
        trip.setName("Test Trip");
        trip.setType(TypeOfTrip.PACKAGETRIP); // Replace SOME_TYPE with an actual type
        trip.setPrice(100.0);

        when(conn.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        tripRepository.add(trip);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void update_withValidTrip_updatesTrip() throws SQLException {
        Trip trip = new Trip();
        trip.setId(1L);
        trip.setName("Updated Trip");
        trip.setType(TypeOfTrip.PACKAGETRIP); // Replace SOME_TYPE with an actual type
        trip.setPrice(150.0);

        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        tripRepository.update(trip);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void remove_withValidTrip_removesTrip() throws SQLException {
        Trip trip = new Trip();
        trip.setId(1L);
        trip.setName("Test Trip");
        trip.setType(TypeOfTrip.PACKAGETRIP); // Replace SOME_TYPE with an actual type
        trip.setPrice(100.0);

        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        tripRepository.remove(trip);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void getAllTrips_returnsListOfTrips() throws SQLException {
        when(conn.createStatement()).thenReturn(statement); // Assuming you have mocked Statement as 'statement'
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        Trip trip1 = new Trip();
        trip1.setId(1L);
        trip1.setName("Trip One");
        trip1.setType(TypeOfTrip.CUSTOMTRIP); // Replace SOME_TYPE with an actual type
        trip1.setPrice(100.0);

        Trip trip2 = new Trip();
        trip2.setId(2L);
        trip2.setName("Trip Two");
        trip2.setType(TypeOfTrip.PACKAGETRIP); // Replace SOME_OTHER_TYPE with an actual type
        trip2.setPrice(150.0);

        when(resultSet.next()).thenReturn(true, true, false); // Simulate two rows in result set
        when(resultSet.getLong("trip_id")).thenReturn(trip1.getId(), trip2.getId());
        when(resultSet.getString("name")).thenReturn(trip1.getName(), trip2.getName());
        when(resultSet.getString("type")).thenReturn(trip1.getType().toString(), trip2.getType().toString());
        when(resultSet.getDouble("price")).thenReturn(trip1.getPrice(), trip2.getPrice());

        List<Trip> result = tripRepository.getAllTrips();

        assertEquals(2, result.size());
        assertEquals(trip1, result.get(0));
        assertEquals(trip2, result.get(1));
    }
}