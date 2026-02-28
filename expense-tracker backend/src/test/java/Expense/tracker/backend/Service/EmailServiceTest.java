package Expense.tracker.backend.Service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest {
    @Autowired
    EmailService emailService;
    @Test
    public void testEmail(){
        emailService.SendEmail("giteshpeswani881@gmail.com","a9F3kP7Xq2LZ8mR4TnY0BvC5EwH");

    }

}