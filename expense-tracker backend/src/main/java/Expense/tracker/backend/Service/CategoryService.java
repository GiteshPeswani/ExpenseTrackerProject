package Expense.tracker.backend.Service;


import Expense.tracker.backend.Repository.AuthRepository;
import Expense.tracker.backend.exceptions.InvalidCategoryName;
import Expense.tracker.backend.exceptions.TokenExpired;
import Expense.tracker.backend.exceptions.TypeNotNull;
import Expense.tracker.backend.model.Category;
import Expense.tracker.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    AuthRepository authRepository;
    public void savecategory(Category category){
        if(category.getUserid()==null){
            throw new RuntimeException("null hai user");
        }
        if(category.getName()=="" || category.getName()==null){
            throw new InvalidCategoryName();
        }
        if(category.getType()=="" || category.getType()==null){
            throw new TypeNotNull();
        }
        authRepository.savecategory(
                category.getUserid(),
                category.getName(),
                category.getDescription(),
                category.getIcon_url(),
                category.getType()
        );


    }
    public List<Category> findbytype(String type){
        if (type==""){
            throw new TypeNotNull();
        }
        return authRepository.findbytype(type);
    }
    public void createCategory(Category category,String email){
        User user=authRepository.findByEmail(email);
        category.setUserid(user.getUserid());
        authRepository.savecategory(user.getUserid(), category.getName(), category.getDescription(), category.getIcon_url(), category.getType());
    }
    public List<Category> findbyuserid(Long userid){
        if (userid==null){
            throw new RuntimeException("null hai user");
        }
        return authRepository.findbyuserid(userid);
    }



}
