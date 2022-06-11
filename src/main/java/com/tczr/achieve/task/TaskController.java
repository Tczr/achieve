package com.tczr.achieve.task;

import com.tczr.achieve.user.User;
import com.tczr.achieve.user.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= "api/v1/task")
public class TaskController {
    @Autowired
    private final TaskHandler taskHandler;
    @Autowired
    private final UserHandler userHandler;

    public TaskController(TaskHandler taskHandler, UserHandler userHandler){
        this.taskHandler = taskHandler;
        this.userHandler = userHandler;
    }

    @GetMapping(path = {"/get","/get/"})
    public ResponseEntity<List<Task>> getTask()
    {
        List<Task> tasks = taskHandler.getList();
        return tasks != null ? new ResponseEntity<List<Task>>(tasks,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Task> getTask(@PathVariable(name = "id") int id)
    {
        Task task = taskHandler.getBy(id);
        return task != null ? new ResponseEntity<>(task,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    //implement the transformer object here
    @PostMapping(path = "/add")
    // retrieve the data from post request
    public ResponseEntity<Void> addTodo(@RequestBody Task task)
    {

        if(taskHandler.add(task))
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/get/user-tasks/{id}")
    public ResponseEntity<List<Task>> getUserTask(@PathVariable(name="id") int userid)
    {

        return new ResponseEntity<>(
                taskHandler.getUserTask(userid),
                 HttpStatus.OK);
    }

    //implement the transformer object here replace it with this old method
    @PutMapping(path = "/edit")
    public ResponseEntity<Task> edit(@RequestBody Task task)
    {
        return taskHandler.update(task) ?
                new ResponseEntity<>(task, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);

    }

    @DeleteMapping(path = "/delete/{id}")
    public  ResponseEntity<Void>  delete(@PathVariable int id)
    {
        return
                taskHandler.delete(id) ?
                    new ResponseEntity<>(HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

/*    public void search(String name){}*/



    public List<Task> getTaskUser(int userid){
        return  userHandler.getBy(userid).getTodoList();
    }

    public void addTask(int userid, Task task){

        User user = userHandler.getBy(userid);

        user.getTodoList().add(task);

    }
    public void doFilter(String by){}
}
