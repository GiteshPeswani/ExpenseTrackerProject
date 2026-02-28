package Expense.tracker.backend.Repository;

import Expense.tracker.backend.Service.AuthService;
import Expense.tracker.backend.model.Category;
import Expense.tracker.backend.model.User;
import Expense.tracker.backend.model.User_token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class AuthRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public void add(String name,String username,String email,String password,String phone){
//        String query="INSERT INTO expensetracker(name ,username,email,password,phone) VALUES(?,?,?,?,?)";
//        jdbcTemplate.update(query,name,username,email,password,phone);
//
//    }
public User findByEmail(String email) {

    String query = "SELECT userid,name,username,email,password,phone,active_yn "+"FROM USER WHERE email= ?";
try{
    User user = jdbcTemplate.queryForObject(
            query,
            (ResultSet resultSet, int rowNum) -> {
                User u = new User();
                u.setUserid(resultSet.getLong("userid"));
                u.setName(resultSet.getString("name"));
                u.setUsername(resultSet.getString("username"));
                u.setEmail(resultSet.getString("email"));
                u.setPhone(resultSet.getString("phone"));
                u.setPassword(resultSet.getString("password"));
                u.setActiveYn(resultSet.getInt("active_yn"));
                return u;
            },email);
    return user;
} catch (EmptyResultDataAccessException e) {
    return null;
}
}
    public User findByUsername(String username) {

        String query = "SELECT userid,name,username,email,password,phone,active_yn "+"FROM USER WHERE username = ?";
        try{
            User user = jdbcTemplate.queryForObject(
                    query,
                    (ResultSet resultSet, int rowNum) -> {
                        User u = new User();
                        u.setUserid(resultSet.getLong("userid"));
                        u.setName(resultSet.getString("name"));
                        u.setUsername(resultSet.getString("username"));
                        u.setEmail(resultSet.getString("email"));
                        u.setPhone(resultSet.getString("phone"));
                        u.setPassword(resultSet.getString("password"));
                        u.setActiveYn(resultSet.getInt("active_yn"));
                       return u;
                    },username);
            return user;
        }catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public void save(String name, String email, String username, String password, String phone, int active_yn) {
        String query = "INSERT INTO USER " + "(name, email, username, password, phone, active_yn) " + "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, name, email, username, password, phone, active_yn);
    }
    public void saveResetToken(String token, Long userid, LocalDateTime expirytime) {

        String query = """
        INSERT INTO user_token (token, user_id, expiry_time, use_yn)
        VALUES (?, ?, ?, '0')
    """;

        jdbcTemplate.update(query, token, userid, expirytime);
    }

    public User_token findbytoken(String token) {

        String query =
                "SELECT token_id, user_id,token, use_yn, expiry_time " +
                        "FROM user_token WHERE token = ?";

        try {
            User_token userToken = jdbcTemplate.queryForObject(
                    query,
                    (ResultSet resultSet, int rowNum) -> {
                        User_token user_token = new User_token();
                        user_token.setToken_id(resultSet.getLong("token_id"));
                        user_token.setUser_id(resultSet.getLong("user_id"));
                        user_token.setToken(resultSet.getString("token"));
                        user_token.setUse_yn(resultSet.getString("use_yn").charAt(0));
                        user_token.setExpiry_time(
                                resultSet.getTimestamp("expiry_time").toLocalDateTime()
                                );
                        return user_token;
                    },
                    token
                    );
            return userToken;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        }
    // 1. Password update karne ke liye method
    public void updateUserPassword(Long userId, String newPassword) {
        String query = "UPDATE USER SET password = ? WHERE userid = ?";
        jdbcTemplate.update(query, newPassword, userId);
    }

    // 2. Token use hone ke baad use_yn ko '1' karne ke liye
    public void markTokenAsUsed(String token) {
        String query = "UPDATE user_token SET use_yn = '1' WHERE token = ?";
        jdbcTemplate.update(query, token);
    }
    public void invalidateoldtokens(Long userId){
        String query = "UPDATE user_token SET use_yn = '1' WHERE user_id = ?";
        jdbcTemplate.update(query, userId);

    }

    public void savecategory(Long userid,String name,String description,String icon_url,String type){
    String query="INSERT INTO Category"+"(userid,name,description,icon_url,type) " + "VALUES(?,?,?,?,?)";
    jdbcTemplate.update(query,userid,name,description,icon_url,type);
    }
    public List<Category> findbytype(String type){
    String query="Select categoryid,userid,name,description,icon_url,type FROM Category WHERE type=?";

       return jdbcTemplate.query(query,(ResultSet resultSet,int rowNum)->{
            Category category2=new Category();
            category2.setCategoryid(resultSet.getInt("categoryid"));
            category2.setUserid(resultSet.getLong("userid"));
            category2.setName(resultSet.getString("name"));
            category2.setDescription(resultSet.getString("description"));
            category2.setIcon_url(resultSet.getString("icon_url"));
            category2.setType(resultSet.getString("type"));
            return category2;
        },type);
    }
    public List<Category> findbyuserid(Long userid){
        String query="Select categoryid,userid,name,description,icon_url,type FROM Category WHERE userid=?";

        return jdbcTemplate.query(query,(ResultSet resultSet,int rowNum)->{
            Category category2=new Category();
            category2.setCategoryid(resultSet.getInt("categoryid"));
            category2.setUserid(resultSet.getLong("userid"));
            category2.setName(resultSet.getString("name"));
            category2.setDescription(resultSet.getString("description"));
            category2.setIcon_url(resultSet.getString("icon_url"));
            category2.setType(resultSet.getString("type"));
            return category2;
        },userid);
    }
    
    }













//token valid hai toh wohi password confirm password pe
//    invalid hai toh redirect
//if the token is right then you have to again validate and then store it and it should show that password  updated update the usedyn also