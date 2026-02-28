package Expense.tracker.backend.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
     int categoryid;
     Long userid;
     String name;
     String description;
     String icon_url;
     String type;
}
