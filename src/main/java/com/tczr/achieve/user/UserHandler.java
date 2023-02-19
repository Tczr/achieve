package com.tczr.achieve.user;

import com.tczr.achieve.shared.Handler;
import com.tczr.achieve.shared.Authorizable;
import com.tczr.achieve.task.TaskHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserHandler implements  Authorizable<User> {
    private static final String USER_NAME_REG ="(^[^\\d]\\w+)(\\-*)([\\w\\d]*)";
    private static final String USER_EMAIL_REG = "^([^\\d]\\w+).*(\\@\\w+)(\\.\\w+)";
    private static final  String ID_REG="^\\d+";
    @Autowired
    private final UserRepo userRepo;

    public UserHandler(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    public User getBy(Object param) {
    return selectQueryOf((String)param);
    }

    public User getById(int id) {
        return userRepo.findById(id).get();
    }

    public User getByName(String name){ return userRepo.getUserByUserName(name); }

    public User getByEmail(String email){  return userRepo.getUserByEmail(email); }

    public boolean update(User object) {

        try{
            userRepo.save(object);
        }catch (IllegalArgumentException exception){exception.printStackTrace();
            return false; }
        return true;

    }

     
    public boolean add(User object) {
        boolean inserted = false;
        try {
            inserted= Optional.of(userRepo.save(object)).isPresent()  ;
        }catch (Exception ex){ex.printStackTrace();}

        return inserted;
    }

     
    public boolean delete(User object) {
        try{
            userRepo.delete(object);
        }catch (IllegalArgumentException exception){
            return false;
        };
        return true;
    }

     
    public boolean delete(int id) {
        try{
            userRepo.deleteById(id);
        }catch (IllegalArgumentException exception){
            return false;
        }
        return true;
    }

    /** Authentication Section:
     *
     */

    public Optional<User> checkInfo(Object email, Object password) throws NoSuchElementException {

//        User user = searchFor((String) email,"email");
        User user = userRepo.getUserByEmail((String)email);
        // return either the current user or an empty user avoiding NullPointerException
        // the check is must, because the pass but if user was null then logically the email is incorrect therefore it's necessary
        return ( user!=null && user.getPassword().equals(password) ) ? Optional.ofNullable(user) : Optional.empty();
    }

     
    public long getToken(User obj) throws NoSuchElementException {
        return 0;
    }
    private User selectQueryOf(String obj){
        if(obj.matches(ID_REG))
            return getById(Integer.parseInt(obj));

        else if(obj.matches(USER_EMAIL_REG))
            return  getByEmail(obj);
        //default
        return getByName(obj);
    }
}
