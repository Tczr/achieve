
/** Extra fixed
 *
 * problem :: 1. when user gets updated the tasks its independent due to the user has its own list embedded list
 *
 * possible solution :
 *          1. to make a new Component called Repository, and it gets created whenever spring container starts,
 *              hold two references i.User list ii.Task list. as result, we only need to deal with the repo
 *          2. to make it simple without storing it in a list and get it from database directly
 *                 this has cons : i.It will make a lot of calls to database in each step we get new copy
 *
 *
 */

package com.tczr.achieve.task;
import com.tczr.achieve.shared.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskHandler {

    @Autowired
    private final TaskRepo taskrepo;
    public TaskHandler(TaskRepo taskRepo){
        this.taskrepo=taskRepo;
    }
    
     
    public Task getById(int taskId) {
        return taskrepo.findById(taskId).get();
    }


     
    public List<Task> getTasksOf(int userId) {
        return taskrepo.getAllByUserId(userId);
    }
    public List<Task> getTasksSorted(Sort sortMethod, int userId){
        return taskrepo.getAllByUserId(userId, sortMethod);
    }

     
    public boolean update(Task object) {
        try {taskrepo.save(object);}
        //if there was an error return false
        catch (IllegalArgumentException exception){return false;}
        // else return true
        return true;
    }

     
    public boolean add(Task object) {
        return update(object);
    }

     
    public boolean delete(Task object) {
        try {taskrepo.delete(object);}
        //if there was an error return false
        catch (IllegalArgumentException exception){return false;}
        // else return true
        return true;
    }

     
    public boolean delete(int id) {
        try {taskrepo.deleteById(id);}
        //if there was an error return false
        catch (IllegalArgumentException exception){return false;}
        // else return true
        return true;
    }
}
