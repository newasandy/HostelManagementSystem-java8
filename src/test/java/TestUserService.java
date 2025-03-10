import org.example.daoImplementation.AddressDAOImp;
import org.example.daoInterface.UserDAO;
import org.example.model.StatusMessageModel;
import org.example.model.Users;
import org.example.service.UsersService;
import org.example.utils.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUserService {

    private UserDAO mockUserDAO;
    private AddressDAOImp mockAddressDAOImp;
    private UsersService mockUsersService;

    @BeforeEach
    void setup(){
        mockUserDAO = mock(UserDAO.class);
        mockAddressDAOImp = mock(AddressDAOImp.class);
        mockUsersService = new UsersService(mockUserDAO, mockAddressDAOImp);
    }

    @Test
    public void registerUser(){
        Users regUser = new Users();
        regUser.setFullName("test2");
        regUser.setEmail("test@gmail.com");
        regUser.setRoles("testRoles");
        regUser.setPasswords(PasswordUtil.getHashPassword("testPass"));
        when(mockUserDAO.findByEmail("test@gmail.com")).thenReturn(null);
        StatusMessageModel statusMessageModel = mockUsersService.registerNewStudent(regUser);

        assertTrue(statusMessageModel.isStatus());
        assertEquals("User Register Successfully", statusMessageModel.getMessage());
    }
}
