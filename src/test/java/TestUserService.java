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

    @Test
    public void regExixtingUser(){
        Users regUser = new Users();
        regUser.setFullName("test2");
        regUser.setEmail("test@gmail.com");
        regUser.setRoles("testRoles");
        regUser.setPasswords(PasswordUtil.getHashPassword("testPass"));
        when(mockUserDAO.findByEmail("test@gmail.com")).thenReturn(regUser);
        StatusMessageModel statusMessageModel = mockUsersService.registerNewStudent(regUser);

        assertFalse(statusMessageModel.isStatus());
        assertEquals("User Already Exist",statusMessageModel.getMessage());
    }

    @Test
    public void updateUserButNotChangeEmail(){
        Users regUser = new Users();
        regUser.setFullName("test2");
        regUser.setEmail("test@gmail.com");
        regUser.setRoles("testRoles");
        regUser.setPasswords(PasswordUtil.getHashPassword("testPass"));
        when(mockUserDAO.getById(regUser.getId())).thenReturn(regUser);
        when(mockUserDAO.update(regUser)).thenReturn(true);
        when(mockAddressDAOImp.update(regUser.getAddress())).thenReturn(true);
        StatusMessageModel statusMessageModel = mockUsersService.updateUserDetails(regUser);

        assertTrue(statusMessageModel.isStatus());
        assertEquals("User Details Update Successfully",statusMessageModel.getMessage());
    }

    @Test
    public void updateUserWhenEmailNotSame(){
        Users regUser2 = new Users();
        regUser2.setFullName("test2");
        regUser2.setEmail("test2@gmail.com");
        regUser2.setRoles("test2Roles");
        regUser2.setPasswords(PasswordUtil.getHashPassword("test2Pass"));
        Users regUser = new Users();
        regUser.setFullName("test1");
        regUser.setEmail("test@gmail.com");
        regUser.setRoles("testRoles");
        regUser.setPasswords(PasswordUtil.getHashPassword("testPass"));
        when(mockUserDAO.getById(regUser.getId())).thenReturn(regUser2);
        when(mockUserDAO.findByEmail(regUser.getEmail())).thenReturn(null);
        when(mockUserDAO.update(regUser)).thenReturn(true);
        when(mockAddressDAOImp.update(regUser.getAddress())).thenReturn(true);
        StatusMessageModel statusMessageModel = mockUsersService.updateUserDetails(regUser);

        assertTrue(statusMessageModel.isStatus());
        assertEquals("User Details Update Successfully",statusMessageModel.getMessage());
    }

    @Test
    public void updateUserAlreadyExistEmail(){
        Users regUser2 = new Users();
        regUser2.setFullName("test2");
        regUser2.setEmail("test2@gmail.com");
        regUser2.setRoles("test2Roles");
        regUser2.setPasswords(PasswordUtil.getHashPassword("test2Pass"));
        Users regUser = new Users();
        regUser.setFullName("test1");
        regUser.setEmail("test@gmail.com");
        regUser.setRoles("testRoles");
        regUser.setPasswords(PasswordUtil.getHashPassword("testPass"));
        when(mockUserDAO.getById(regUser.getId())).thenReturn(regUser2);
        when(mockUserDAO.findByEmail(regUser.getEmail())).thenReturn(regUser2);

        StatusMessageModel statusMessageModel = mockUsersService.updateUserDetails(regUser);

        assertFalse(statusMessageModel.isStatus());
        assertEquals("!! User Already Exist",statusMessageModel.getMessage());
    }
}
