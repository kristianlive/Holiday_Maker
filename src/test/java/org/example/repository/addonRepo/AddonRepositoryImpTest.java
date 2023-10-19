package org.example.repository.addonRepo;

import org.example.db.Database;
import org.example.entity.Addon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddonRepositoryImpTest {

    @InjectMocks
    private AddonRepositoryImp addonRepository;

    @Mock
    private Database db;

    @Mock
    private Connection conn;

    @Mock
    private Statement stmt;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(db.connectToDb()).thenReturn(conn);
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(resultSet);
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    public void findById_ReturnsAddon_WhenFound() throws SQLException {
        Long id = 1L;
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getString("title")).thenReturn("Test Addon");
        when(resultSet.getDouble("price")).thenReturn(99.99);

        Addon addon = addonRepository.findById(id);

        assertNotNull(addon);
        assertEquals(id, addon.getId());
        assertEquals("Test Addon", addon.getTitle());
        assertEquals(99.99, addon.getPrice());
    }

    @Test
    public void create_InsertsAddon_Successfully() throws SQLException {
        Addon addon = Addon.builder()
                .title("Test Addon")
                .price(99.99)
                .build();

        addonRepository.create(addon);

        verify(preparedStatement).setString(1, addon.getTitle());
        verify(preparedStatement).setDouble(2, addon.getPrice());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void update_UpdatesAddon_Successfully() throws SQLException {
        Addon addon = Addon.builder()
                .id(1L)
                .title("Updated Addon")
                .price(89.99)
                .build();

        addonRepository.update(addon);

        verify(preparedStatement).setString(1, addon.getTitle());
        verify(preparedStatement).setDouble(2, addon.getPrice());
        verify(preparedStatement).setLong(3, addon.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void remove_DeletesAddon_Successfully() throws SQLException {
        Addon addon = Addon.builder()
                .id(1L)
                .title("Test Addon")
                .price(99.99)
                .build();

        addonRepository.remove(addon);

        verify(stmt).executeUpdate(eq("DELETE FROM addon WHERE id = " + addon.getId()));
    }

    @Test
    public void getAllAddon_ReturnsAddonList_Successfully() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false); // Two addons in the list
        when(resultSet.getLong("id")).thenReturn(1L, 2L);
        when(resultSet.getString("title")).thenReturn("Addon 1", "Addon 2");
        when(resultSet.getDouble("price")).thenReturn(99.99, 89.99);

        List<Addon> addons = addonRepository.getAllAddon();

        assertEquals(2, addons.size());
        assertEquals("Addon 1", addons.get(0).getTitle());
        assertEquals("Addon 2", addons.get(1).getTitle());
    }
}