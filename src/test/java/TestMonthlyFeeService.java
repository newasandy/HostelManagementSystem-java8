import org.example.daoInterface.MonthlyFeeDAO;
import org.example.model.MonthlyFee;
import org.example.model.StatusMessageModel;
import org.example.model.Users;
import org.example.service.MonthlyFeeService;
import org.example.utils.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestMonthlyFeeService {
    private MonthlyFeeDAO mockMonthlyFeeDAO;
    private MonthlyFeeService mockMonthlyFeeService;

    @BeforeEach
    void setup(){
        mockMonthlyFeeDAO = mock(MonthlyFeeDAO.class);
        mockMonthlyFeeService = new MonthlyFeeService(mockMonthlyFeeDAO);
    }

    @Test
    public void assignFee(){
        Users user = new Users();
        user.setFullName("test");
        user.setEmail("test@gmail.com");
        user.setPasswords(PasswordUtil.getHashPassword("testpass"));
        user.setRoles("USER");
        user.setStatus(true);
        MonthlyFee testAddFee = new MonthlyFee();
        Date date = new Date();
        Timestamp issueDate = new Timestamp(date.getTime());
        testAddFee.setStudentId(user);
        testAddFee.setFeeAmount(10000);
        testAddFee.setDue(10000);
        testAddFee.setPaid(0);
        testAddFee.setYear(2025);
        testAddFee.setMonth("April");
        testAddFee.setIssueDate(issueDate);

        when(mockMonthlyFeeDAO.add(testAddFee)).thenReturn(true);

        StatusMessageModel statusMessageModel = mockMonthlyFeeService.setStudentMonthlyFee(testAddFee);

        assertTrue(statusMessageModel.isStatus());
        assertEquals("Fee Assign Successfully",statusMessageModel.getMessage());
    }
    @Test
    public void assignFeeFailed(){
        Users user = new Users();
        user.setFullName("test");
        user.setEmail("test@gmail.com");
        user.setPasswords(PasswordUtil.getHashPassword("testpass"));
        user.setRoles("USER");
        user.setStatus(true);
        MonthlyFee testAddFee = new MonthlyFee();
        Date date = new Date();
        Timestamp issueDate = new Timestamp(date.getTime());
        testAddFee.setStudentId(user);
        testAddFee.setFeeAmount(10000);
        testAddFee.setDue(10000);
        testAddFee.setPaid(0);
        testAddFee.setYear(2025);
        testAddFee.setMonth("April");
        testAddFee.setIssueDate(issueDate);

        when(mockMonthlyFeeDAO.add(testAddFee)).thenReturn(false);

        StatusMessageModel statusMessageModel = mockMonthlyFeeService.setStudentMonthlyFee(testAddFee);

        assertFalse(statusMessageModel.isStatus());
        assertEquals("!! Not Assign Fee",statusMessageModel.getMessage());
    }

    @Test
    public void payFee(){
        Users user = new Users();
        user.setFullName("test");
        user.setEmail("test@gmail.com");
        user.setPasswords(PasswordUtil.getHashPassword("testpass"));
        user.setRoles("USER");
        user.setStatus(true);
        MonthlyFee testAddFee = new MonthlyFee();
        Date date = new Date();
        Timestamp issueDate = new Timestamp(date.getTime());
        testAddFee.setStudentId(user);
        testAddFee.setFeeAmount(10000);
        testAddFee.setDue(10000);
        testAddFee.setPaid(0);
        testAddFee.setYear(2025);
        testAddFee.setMonth("April");
        testAddFee.setIssueDate(issueDate);

        double paidAmount = 5000;

        when(mockMonthlyFeeDAO.update(testAddFee)).thenReturn(true);

        StatusMessageModel statusMessageModel = mockMonthlyFeeService.feePaid(testAddFee,paidAmount);

        assertTrue(statusMessageModel.isStatus());
        assertEquals("Paid Successfully",statusMessageModel.getMessage());
    }
}
