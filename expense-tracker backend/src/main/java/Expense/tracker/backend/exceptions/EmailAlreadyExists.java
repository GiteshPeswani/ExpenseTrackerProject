    package Expense.tracker.backend.exceptions;

    public class EmailAlreadyExists extends  RuntimeException{
        public EmailAlreadyExists(){
            super("Email Alreay exists");
        }
    }
