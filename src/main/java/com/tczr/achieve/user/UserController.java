package com.tczr.achieve.user;

import com.tczr.achieve.shared.Handler;
import com.tczr.achieve.shared.Authorizable;

import com.tczr.achieve.task.TaskHandler;
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
class UserController {

    @Autowired
    private final UserHandler userHandler;
    @Autowired
    private final TaskHandler taskHandler;

    public UserController(UserHandler userHandler, TaskHandler taskHandler)
    {
        this.userHandler=userHandler;
        this.taskHandler=taskHandler;
        System.out.println("---------------\n"+userHandler);

    }

    @GetMapping(path = "/get/{obj}")
    public ResponseEntity<User> getUserBy(@PathVariable Object obj)
    {
        User user=null;
        try {
            user = userHandler.getBy(obj);
        }catch (NoSuchElementException exception)
        {exception.printStackTrace(); return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
        finally {
            if (user != null) {
                user.setTodoList(
                        taskHandler.getTasksOf(user.getUserId())
                );
                return new ResponseEntity<>(user,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping(path="/login")
    public ResponseEntity<User> login(@RequestBody Map<String, Object> mapper){
        Authorizable authentication= (Authorizable) userHandler;
        Optional user=Optional.empty();
        try {
             user = authentication.checkInfo(mapper.get("email"),mapper.get("password"));
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("no such element");
        }finally {
            if(!user.isEmpty()) {
                 User user1 = (User)user.get();
                 user1.setTodoList(
                         taskHandler.getTasksOf(user1.getUserId())
                 );
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
