package Expense.tracker.backend.Service;


import Expense.tracker.backend.Repository.AuthRepository;
import Expense.tracker.backend.exceptions.EmailAlreadyExists;
import Expense.tracker.backend.exceptions.InvalidEmail;
import Expense.tracker.backend.exceptions.TokenExpired;
import Expense.tracker.backend.exceptions.UserAlready;
import Expense.tracker.backend.model.Category;
import Expense.tracker.backend.model.User;
import Expense.tracker.backend.model.User_token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class  AuthService {
    @Autowired
    AuthRepository authRepository;
    @Autowired
    private  EmailService emailService;
    public void register(User user) throws InvalidEmail {
        if(!checkIfEmailValid(user.getEmail())){
            throw new InvalidEmail();
        }
        if(authRepository.findByEmail(user.getEmail())!=null){
            throw new EmailAlreadyExists();
        }
        if(authRepository.findByUsername(user.getUsername())!=null){
            throw new UserAlready();
        }
        authRepository.save(
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                encodedPassword(user.getPassword()),
                user.getPhone(),
                user.getActiveYn()
        );
    }
    private String encodedPassword(String password){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

        return encoder.encode(password);
    }




    public boolean checkIfEmailValid(String email){
        if(email==null)  return false;

        String REGEX ="[A-Za-z0-9_+.-]+@[A-Za-z0-9_+.-]+\\.[a-z]{2,}$";

        Pattern pattern= Pattern.compile(REGEX);

        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }
    public void forgertPassword(Map<String,String> body){
        System.out.println(body);

        String email=body.get("email");
        User user=authRepository.findByEmail(email);


        if(user==null){
            throw new InvalidEmail();
        }
        tokengenerate(user.getUserid(),email);

    }
    public void tokengenerate(long userid,String email){


        System.out.println(">>> TOKEN GENERATION STARTED");
        authRepository.invalidateoldtokens(userid);
       String token=UUID.randomUUID().toString();
        //                generates random tokens
        LocalDateTime expirytime=LocalDateTime.now().plusMinutes(5);
        System.out.println(">>> TOKEN = " + token);

        authRepository.saveResetToken(token,userid,expirytime);
        System.out.println(">>> TOKEN SAVED IN DB");


        emailService.SendEmail(email,token);
        System.out.println(">>> EMAIL SENT");


    }
    public void checkiftokenvalid(Map<String,String> body){
        String token=body.get("token");
        if(token==null){
            throw new RuntimeException("token missing");
        }
        User_token user_token= authRepository.findbytoken(token);
        if (user_token == null) {
            throw new RuntimeException("Invalid token: Not found in database");
        }
        if (user_token.getUse_yn() == '1') {
            throw new RuntimeException("Link already used");
        }
        if(user_token.getExpiry_time()
                .isBefore(LocalDateTime.now())) {
            throw new TokenExpired();
        }

    }
    public void updatePassword(Map<String,String> body){
        String token = body.get("token");
        String password = body.get("password");


        User_token user_token = authRepository.findbytoken(token);

        if(user_token == null || user_token.getUse_yn() == '1'){
            throw new RuntimeException("Invalid Request");
        }



        if(!user_token.getExpiry_time().isBefore(LocalDateTime.now())){
            throw new TokenExpired();
        }

        authRepository.updateUserPassword(
                user_token.getUser_id(),
                encodedPassword(password)
        );

        authRepository.markTokenAsUsed(token);
    }




}