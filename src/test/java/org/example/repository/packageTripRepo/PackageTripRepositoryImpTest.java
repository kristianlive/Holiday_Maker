package org.example.repository.packageTripRepo;

import org.example.db.Database;
import org.example.entity.PackageTrip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PackageTripRepositoryImpTest {

    @InjectMocks
    PackageTripRepositoryImp packageTripRepository;

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
    void getReturnsPackageTrip() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getLong("package_trips_id")).thenReturn(1L);

        PackageTrip result = packageTripRepository.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getReturnsNullWhenNotFound() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        PackageTrip result = packageTripRepository.get(1L);

        assertNull(result);
    }

    @Test
    void getAllPackageTrips() throws SQLException {
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getLong("id")).thenReturn(1L);
        when(rs.getString("destination")).thenReturn("destination");
        // ... mock other fields ...

        List<PackageTrip> result = packageTripRepository.getAllPackageTrips();

        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getId());
    }

    // You may also want to add tests for SQL exception scenarios.
}
