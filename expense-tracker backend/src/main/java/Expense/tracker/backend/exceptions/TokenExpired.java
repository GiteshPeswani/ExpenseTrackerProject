package Expense.tracker.backend.exceptions;

public class TokenExpired extends RuntimeException {
    public TokenExpired() {
        super("Token has been expired");
    }
}
