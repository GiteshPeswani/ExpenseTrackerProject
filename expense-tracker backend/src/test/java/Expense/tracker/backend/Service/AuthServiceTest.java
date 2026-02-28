package Expense.tracker.backend.Service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Test
    public void testPassword(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
       String encodedpassword=bCryptPasswordEncoder.encode("password");
       System.out.println(encodedpassword);
       // yeha pe we are using bcryptpasswordEncoder class ka .encode
    }

}