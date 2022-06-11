package com.tczr.achieve.user;

import com.tczr.achieve.shared.Handler;
import com.tczr.achieve.shared.Authorizable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private Handler<User> userHandler;

    public UserController(Handler userHandler)
    {
        this.userHandler=userHandler;
        System.out.println("---------------\n"+userHandler);

    }

    @GetMapping(path = "/get/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userHandler.getList();
        return (users!=null && users.size()>0)?
                new ResponseEntity<>(users, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping(path = "/get/{obj}")
    public ResponseEntity<User> getUserByName(@PathVariable Object obj)
    {
        User user=null;
        try {
            user = userHandler.getBy(obj);

        }catch (NoSuchElementException exception){ return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PostMapping(path="/login")
    public ResponseEntity<User> login(@RequestBody Map<String, Object> mapper){
        userHandler = (UserHandler)userHandler;
        Authorizable authentication= (Authorizable) userHandler;
        Optional user=Optional.empty();
        try {
             user =authentication.checkInfo(mapper.get("email"),mapper.get("password"));
        }catch (Exception ex){
            System.out.println("no such element");
        }finally {
            if(!user.isEmpty()) {
                System.out.println(user.get());
                return new ResponseEntity<>((User) user.get(), HttpStatus.OK);
            }
            else
            {
                System.out.println("you need an account in order to log-in");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }
    @PostMapping(path = "/add")
    public ResponseEntity<Void> create(@RequestBody User user)
    {
        return (userHandler.add(user))?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



    @PutMapping(path = "/edit")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return userHandler.update(user)?
                new ResponseEntity<>(user, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") int id)
    {
        return userHandler.delete(id) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
