package com.tczr.achieve.shared;
import com.tczr.achieve.task.Task;
import com.tczr.achieve.user.User;
import java.util.*;

@org.springframework.stereotype.Repository
public class Repository {
    final Map<Integer, User> UserMap = new HashMap<>();
    List<Task> tasks;
    List<User> users;

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<User> getUsers() {
        return users;
    }

    private int getLastUserID(){return users.get((users.size()-1)).getId();}
    private int getLastTaskId(){return tasks.get((tasks.size()-1)).getId(); }


    void add(Object object){
        if(isItUser(object))
        {
            ((User)object).setUserId(getLastUserID());
            System.out.println(object);
            users.add((User)object);
        }
        else if(isItTask(object)) {
            ((Task) object).setId(getLastTaskId());
            System.out.println(object);
            tasks.add((Task)object);
        }

    }



    boolean isItUser(Object obj) {  return obj instanceof User;  }
    boolean isItTask(Object obj) {  return obj instanceof Task ; }
}
