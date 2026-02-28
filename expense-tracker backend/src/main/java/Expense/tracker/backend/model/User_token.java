package Expense.tracker.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class User_token {
    Long token_id;
    Long user_id;
    String token;
    char use_yn;
    LocalDateTime expiry_time;
}
