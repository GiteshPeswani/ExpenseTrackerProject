package Expense.tracker.backend.Controller;


import Expense.tracker.backend.Service.AuthService;
import Expense.tracker.backend.Service.CategoryService;
import Expense.tracker.backend.exceptions.*;
import Expense.tracker.backend.model.Category;
import Expense.tracker.backend.model.User;
import org.apache.el.parser.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:4200") // Yeh line frontend ko allow karegi
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    CategoryService categoryService;
        private static Logger logger= LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody User user){


        try {
            authService.register(user);
            return ResponseEntity.ok().body(Map.of("body","User registered Successfully"));
        }
        catch (InvalidEmail e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));

        } catch (EmailAlreadyExists e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));

        } catch (UserAlready e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));

        } catch (Exception e) {
            logger.error("some error occured");
            return ResponseEntity.badRequest().body(Map.of("body","Some error occured"));
        }
    }
        @PostMapping("/forgot-password")
        public ResponseEntity<?> forgotpassword(@RequestBody Map<String,String> body){
            try{
                authService.forgertPassword(body);
                return ResponseEntity.ok(Map.of("body", "Reset link sent to your email"));
            }
            catch(InvalidEmail e){
                return ResponseEntity.badRequest().body(Map.of("body","Email not registered"));
            }
            catch(Exception e){
                return ResponseEntity.badRequest().body(Map.of("body","Something went wrong"));
            }
        }

        @PostMapping("/reset-password-final")
        public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
            try {
                authService.updatePassword(body);
                return ResponseEntity.ok().body(Map.of("body", "Password updated successfully"));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of("body", e.getMessage()));
            }
        }

            @PostMapping("/tokenvalidation")
        public ResponseEntity<?> validatetoken(@RequestBody  Map<String,String>body){
           try{
               authService.checkiftokenvalid(body);
               return ResponseEntity.ok().body(Map.of("body","token is valid"));
           }
           catch(TokenExpired e){
               logger.info(e.getMessage());
               return  ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
           }


        }
    @PostMapping("/category")
    public ResponseEntity<Map<String,String>>categoryenter(@RequestBody Category category, Authentication authentication){
//          authentication leke ayega peche se email
        System.out.println(authentication.getName());
        try{
            categoryService.createCategory(category,authentication.getName());
//            categoryService.savecategory(category,authentication.getName());
            return ResponseEntity.ok().body(Map.of("body","Category entired Successfully"));
        }
        catch(InvalidCategoryName e){
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
        catch(TypeNotNull e){
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
        catch(Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body","Some error occured"));
        }
    }
    @GetMapping("/category/{type}")
    public  ResponseEntity<?>fetchbytype(@PathVariable(name="type")String type){

        try {
            List<Category> categories=categoryService.findbytype(type);
            return ResponseEntity.ok(categories);
        }
        catch (TypeNotNull e){
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body","Type null hai"));
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body","Some error occured"));
        }
    }
    @GetMapping("/category/user/{userid}")
    public  ResponseEntity<?>fetchbyid(@PathVariable(name="userid")Long userid){

        try {
            List<Category> categories=categoryService.findbyuserid(userid);
            return ResponseEntity.ok(categories);
        }
        catch (RuntimeException e){
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body","user null hai"));
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body","Some error occured"));
        }
    }




}
