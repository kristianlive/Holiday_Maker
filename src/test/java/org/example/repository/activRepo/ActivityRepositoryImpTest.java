package org.example.repository.activRepo;

import org.example.db.Database;
import org.example.entity.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityRepositoryImpTest {


    @InjectMocks
    private ActivityRepositoryImp activityRepository;

    @Mock
    private Database db;

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(db.connectToDb()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void get_ReturnsActivity_WhenFound() throws SQLException {
        Long id = 1L;
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getString("title")).thenReturn("Test Activity");
        when(resultSet.getDouble("price")).thenReturn(99.99);

        Activity activity = activityRepository.get(id);

        assertNotNull(activity);
        assertEquals(id, activity.getId());
        assertEquals("Test Activity", activity.getTitle());
        assertEquals(99.99, activity.getPrice());
    }

    @Test
    public void add_InsertsActivity_Successfully() throws SQLException {
        Activity activity = Activity.builder()
                .title("Test Activity")
                .price(99.99)
                .build();

        activityRepository.add(activity);

        verify(preparedStatement).setString(1, activity.getTitle());
        verify(preparedStatement).setDouble(2, activity.getPrice());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void update_UpdatesActivity_Successfully() throws SQLException {
        Activity activity = Activity.builder()
                .id(1L)
                .title("Updated Activity")
                .price(89.99)
                .build();

        activityRepository.update(activity);

        verify(preparedStatement).setString(1, activity.getTitle());
        verify(preparedStatement).setDouble(2, activity.getPrice());
        verify(preparedStatement).setLong(3, activity.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void remove_DeletesActivity_Successfully() throws SQLException {
        Activity activity = Activity.builder()
                .id(1L)
                .title("Test Activity")
                .price(99.99)
                .build();

        activityRepository.remove(activity);

        verify(preparedStatement).setLong(1, activity.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void getAllActivity_ReturnsActivityList_Successfully() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false); // Two activities in the list
        when(resultSet.getLong("id")).thenReturn(1L, 2L);
        when(resultSet.getString("title")).thenReturn("Activity 1", "Activity 2");
        when(resultSet.getDouble("price")).thenReturn(99.99, 89.99);

        List<Activity> activities = activityRepository.getAllActivity();

        assertEquals(2, activities.size());
        assertEquals("Activity 1", activities.get(0).getTitle());
        assertEquals("Activity 2", activities.get(1).getTitle());
    }
}
