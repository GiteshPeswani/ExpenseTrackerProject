package Expense.tracker.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// we added the @Getter setter karke aur annotations on kardiya jaake setting main aur usse humko getter setter banane ki zarurt nhi
public class User {
    Long userid;
    String name;
    String username;
    String email;
    String password;
    String phone;
    int activeYn;
}