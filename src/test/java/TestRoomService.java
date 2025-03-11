import org.example.daoInterface.RoomAllocationDAO;
import org.example.daoInterface.RoomDAO;
import org.example.model.Rooms;
import org.example.model.StatusMessageModel;
import org.example.service.RoomsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestRoomService {
    private RoomDAO mockRoomDAO;
    private RoomAllocationDAO mockRoomAllocationDAO;
    private RoomsService mockRoomService;

    @BeforeEach
    void setup(){
        mockRoomDAO = mock(RoomDAO.class);
        mockRoomAllocationDAO = mock(RoomAllocationDAO.class);
        mockRoomService = new RoomsService(mockRoomDAO,mockRoomAllocationDAO);
    }

    @Test
    public void addRoom(){
        Rooms testRoom = new Rooms();
        testRoom.setRoomNumber(101);
        testRoom.setCapacity(2);

        when(mockRoomDAO.findByRoomNumber(101)).thenReturn(null);
        when(mockRoomDAO.add(testRoom)).thenReturn(true);

        StatusMessageModel statusMessageModel = mockRoomService.addNewRoomService(testRoom);
        assertTrue(statusMessageModel.isStatus());
        assertEquals("Room Added Successfully",statusMessageModel.getMessage());
    }
    @Test
    public void addRoomNotSuccess(){
        Rooms testRoom = new Rooms();
        testRoom.setRoomNumber(101);
        testRoom.setCapacity(2);

        when(mockRoomDAO.findByRoomNumber(101)).thenReturn(null);
        when(mockRoomDAO.add(testRoom)).thenReturn(false);

        StatusMessageModel statusMessageModel = mockRoomService.addNewRoomService(testRoom);
        assertFalse(statusMessageModel.isStatus());
        assertEquals("!! Room Not Added",statusMessageModel.getMessage());
    }

    @Test
    public void addRoomAlreadyExist(){
        Rooms testRoom = new Rooms();
        testRoom.setRoomNumber(101);
        testRoom.setCapacity(2);

        when(mockRoomDAO.findByRoomNumber(101)).thenReturn(testRoom);
        StatusMessageModel statusMessageModel = mockRoomService.addNewRoomService(testRoom);
        assertFalse(statusMessageModel.isStatus());
        assertEquals("!! Room Already Exist",statusMessageModel.getMessage());
    }
}
