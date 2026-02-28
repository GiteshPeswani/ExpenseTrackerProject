package Expense.tracker.backend.exceptions;

public class TypeNotNull extends RuntimeException {
    public TypeNotNull() {
        super("Category type should not be null");
    }
}
