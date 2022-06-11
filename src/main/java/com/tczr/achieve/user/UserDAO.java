package com.tczr.achieve.user;

import com.tczr.achieve.shared.Crud;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO implements Crud<User> {
    private static final String userNameReq ="(^[^\\d]\\w+)(\\-*)([\\w\\d]*)";
    private static final String userEmailReq = "^([^\\d]\\w+).*(\\@\\w+)(\\.\\w+)";
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper rowMapper= ( res , rowNumber ) ->{
       User user = new User(res.getInt("userId"));
       user.setUserEmail(res.getString("userEmail"));
       user.setUserName(res.getString("userName"));
       user.setCreatedAt(LocalDate.parse(res.getString("createdAt")) );
//       TODO : set user info fro result set, either via setter or via constructor
       return user;
    } ;

    public UserDAO(JdbcTemplate jdbcTemplate)
    { this.jdbcTemplate = jdbcTemplate; }



    public List<User> getList() {
        return jdbcTemplate.query(
                "SELECT * FROM users",
                rowMapper
        );
    }

    @Deprecated
    @Override
    public Optional getObjectById(int id) {
        // is number or name
       // String query = getQueryOf(nameOrId);
        User newUser = (User) jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE userId=?",
                rowMapper,
                id
        );

        return Optional.ofNullable(newUser);
    }

    @Override
    public boolean insert(User object) {
        return jdbcTemplate.update(
                "INSERT INTO users(userEmail,userPassword,userName,createdAt) " +
                        "VALUES (?, ?, ?, ?)",
                object.getUserEmail(), object.getPassword(), object.getUserName(), object.getCreatedAt()
        ) == 1;
    }

    @Override
    public boolean update(User object) {

        return jdbcTemplate.update(
                "UPDATE users SET userEmail=?, userName=?, userPassword=? WHERE userId=?",
                object.getUserEmail(), object.getUserName(), object.getPassword(),

                object.getId()
        )==1;
    }

    @Override
    public boolean delete(User object) {

        return jdbcTemplate.update(
                "DELETE FROM users WHERE userId=?",
                object.getId()

        )==1;
    }

    private String getQueryOf(String nameOrId){
        //check if requested query with name
        if(nameOrId.matches(userNameReq)){
            return "SELECT * FROM users WHERE userName=?";
        }
        //default
        return "SELECT * FROM users WHERE userId=?";
    }
}
