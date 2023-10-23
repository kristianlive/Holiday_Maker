package org.example.repository.bookingRepo;

import org.example.db.Database;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class BookingRepositoryImpTest {
    @InjectMocks
    private BookingRepositoryImp bookingRepository;

    @Mock
    private Database db;

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement preparedStatement;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(db.connectToDb()).thenReturn(conn);
    }
/*
    @Test
    public void save_Booking_Success() throws SQLException {
        Bookings booking = new Bookings(1L, new Trip(2L, ...), new User(3L, ...)); // Replace '...' with appropriate Trip and User constructors as needed
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        bookingRepository.save(booking);

        verify(preparedStatement).setLong(1, booking.getTripId().getId());
        verify(preparedStatement).setLong(2, booking.getUserId().getId());
    }

    @Test
    public void update_Booking_Success() throws SQLException {
        Bookings booking = new Bookings(1L, new Trip(2L, ...), new User(3L, ...));
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        bookingRepository.update(booking);

        verify(preparedStatement).setLong(1, booking.getTripId().getId());
        verify(preparedStatement).setLong(2, booking.getUserId().getId());
        verify(preparedStatement).setLong(3, booking.getId());
    }

    @Test
    public void remove_Booking_Success() throws SQLException {
        Bookings booking = new Bookings(1L, new Trip(2L, ...), new User(3L, ...));
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        bookingRepository.remove(booking);

        verify(preparedStatement).setLong(1, booking.getId());
    }
 */
}

