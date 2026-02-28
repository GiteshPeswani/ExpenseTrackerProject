package Expense.tracker.backend.exceptions;

public class InvalidCategoryName extends RuntimeException {
    public InvalidCategoryName() {
        super("Category name should not be null!");
    }
}
