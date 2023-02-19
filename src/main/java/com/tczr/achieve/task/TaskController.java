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

class TaskController {
    @Autowired
    private final TaskHandler taskHandler;


    public TaskController(TaskHandler taskHandler, UserHandler userHandler){
        this.taskHandler = taskHandler;
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Task> getTask(@PathVariable(name = "id") int id)
    {

        Task task = taskHandler.getById(id);
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
}
