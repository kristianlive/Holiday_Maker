package org.example.repository.accomodationRepo;

import org.example.db.Database;
import org.example.entity.Accomodation;
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

class AccomodationRepositoryImpTest {

    @InjectMocks
    private AccomodationRepositoryImp accomodationRepository;

    @Mock
    private Database db;

    @Mock
    private Connection conn;

    @Mock
    private Statement stmt;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet rs;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(db.connectToDb()).thenReturn(conn);
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
    }

    @Test
    public void get_returnsAccomodation_whenFound() throws SQLException {
        Long id = 1L;
        when(rs.next()).thenReturn(true);
        when(rs.getLong("id")).thenReturn(id);
        when(rs.getString("type")).thenReturn("Hotel");
        when(rs.getString("address")).thenReturn("123 Street");
        when(rs.getDouble("price")).thenReturn(100.0);

        Accomodation accomodation = accomodationRepository.get(id);

        assertNotNull(accomodation);
        assertEquals(id, accomodation.getId());
        assertEquals("Hotel", accomodation.getType());
        assertEquals("123 Street", accomodation.getAddress());
        assertEquals(100.0, accomodation.getPrice());
    }

    @Test
    public void add_insertsAccomodationIntoDb() throws SQLException {
        Accomodation accomodation = new Accomodation();
        accomodation.setType("Hotel");
        accomodation.setAddress("123 Street");
        accomodation.setPrice(100.0);

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(conn.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        accomodationRepository.add(accomodation);

        verify(mockPreparedStatement).setString(1, "Hotel");
        verify(mockPreparedStatement).setString(2, "123 Street");
        verify(mockPreparedStatement).setDouble(3, 100.0);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void update_accomodation_success() throws SQLException {
        Accomodation accomodation = new Accomodation(1L, "Hotel", "123 Street", 100.0);

        String updateQuery = "UPDATE accomodations SET type = ?, address = ?, price = ? WHERE id = ?";
        when(conn.prepareStatement(updateQuery)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulating 1 row affected

        accomodationRepository.update(accomodation);

        verify(preparedStatement).setString(1, accomodation.getType());
        verify(preparedStatement).setString(2, accomodation.getAddress());
        verify(preparedStatement).setDouble(3, accomodation.getPrice());
        verify(preparedStatement).setLong(4, accomodation.getId());
    }

    @Test
    public void remove_accomodation_success() throws SQLException {
        Accomodation accomodation = new Accomodation(1L, "Hotel", "123 Street", 100.0);

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(anyString())).thenReturn(1); // Simulating 1 row affected

        accomodationRepository.remove(accomodation);

        verify(stmt).executeUpdate("DELETE FROM accomodations WHERE id = " + accomodation.getId());
    }

    @Test
    public void getAllAccomodations_returnsList() throws SQLException {
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);

        when(rs.next()).thenReturn(true, true, false); // Simulate two rows in the result set
        when(rs.getLong("id")).thenReturn(1L, 2L);
        when(rs.getString("type")).thenReturn("Hotel", "Motel");
        when(rs.getString("address")).thenReturn("123 Street", "456 Lane");
        when(rs.getDouble("price")).thenReturn(100.0, 150.0);

        List<Accomodation> accomodations = accomodationRepository.getAllAccomodations();

        assertNotNull(accomodations);
        assertEquals(2, accomodations.size());

        assertEquals(1L, accomodations.get(0).getId());
        assertEquals("Hotel", accomodations.get(0).getType());
        assertEquals("123 Street", accomodations.get(0).getAddress());
        assertEquals(100.0, accomodations.get(0).getPrice());

        assertEquals(2L, accomodations.get(1).getId());
        assertEquals("Motel", accomodations.get(1).getType());
        assertEquals("456 Lane", accomodations.get(1).getAddress());
        assertEquals(150.0, accomodations.get(1).getPrice());
    }
}