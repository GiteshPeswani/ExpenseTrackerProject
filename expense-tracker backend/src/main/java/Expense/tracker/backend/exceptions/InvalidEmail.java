package Expense.tracker.backend.exceptions;

public class InvalidEmail extends RuntimeException{
    public InvalidEmail(){
        super("Email sahi nhi hai");
    }
}
