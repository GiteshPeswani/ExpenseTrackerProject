package Expense.tracker.backend.Service;

import Expense.tracker.backend.Repository.AuthRepository;
import Expense.tracker.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    AuthRepository authRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

                //user jo springsecurity ko call kara hai .build() wala that is the method
//        why did we use this fullly qualified name? because we already have the model of the same name that is User
        User user=authRepository.findByEmail(email);
        if(user==null){
            return null;
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .disabled(user.getActiveYn()==0)
                .build();


    }
    // we use the builder here kyuki usko userdetails main  return karya


//    @Component is written over our class that is custom class eg(RestController,Service) humara class

//    @bean we use over funcction which will be called at the time of startup it tells you how to create the

//    object and returns a new object which will be then stored in application context of IOC aur jab i need the object we will still use @Autowired

//    if at all we write @bean we need to declare class as a  @configuration



}
