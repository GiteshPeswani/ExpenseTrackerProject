package Expense.tracker.backend.exceptions;

public class UserAlready extends RuntimeException {
    public UserAlready(){
        super("user hai already");
    }
}
