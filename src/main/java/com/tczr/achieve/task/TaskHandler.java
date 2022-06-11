
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

import com.tczr.achieve.service.Helper;
import com.tczr.achieve.shared.Handler;
import com.tczr.achieve.shared.Crud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TaskHandler implements Handler<Task> {

    private final Crud taskDAO;
    private List<Task> data;
    private static int lastId;

    @Autowired
    public TaskHandler(Crud taskDAO)
    {
        this.taskDAO=taskDAO;
        data=taskDAO.getList();
        Helper.quickSort(data);
        lastId = getLastId();

    }

    @Override
    public Task getBy(Object id) {
     return getBy((int)id);
    }


    private Task getBy(Integer id) {
        // problem when inserting, I will be dealing with the list of mine not with the db
        // for speed and calling purposes so when adding new task it not going to be auto Increment,
        // so whe should probably make a static id increasing at last index, and when remove don't decrease it to make it realistic;
        int index = search(id);

        return index != -1 ? data.get(index) : null;
    }

    @Override
    public List<Task> getList() {
        return data;
    }

    @Override
    public boolean update(Task object) {
        //to be in parallel
        int index = search(object.getId());
        if(index != -1);
            data.set(index, object);

        return taskDAO.update(object);
    }

    @Override
    public boolean add(Task object) {
        //to be in parallel
        lastId++;
        object.setId(lastId);
        data.add(object);

        return taskDAO.insert(object);
    }

    @Override
    public boolean delete(Task object) {
        //to be in parallel
        data.remove(object);

        return taskDAO.delete(object);
    }

    @Override
    public boolean delete(int id) {
        Task task = null;
        int index = search(id);

        if(index != -1)
            task = data.remove(index);



        return taskDAO.delete(task);

    }

    public List<Task> getUserTask(int userid)
    {
        return searchForUserTasks(userid);
    }

    private int search(int id) {
        int start = 0, end = data.size()-1;
        while (start<=end) {
            int mid = start+(end-start)/2;

            if(data.get(mid).getId() > id)
                end=mid-1;
            else if(data.get(mid).getId() < id)
                start=mid+1;
            else
                return mid;
        }
        return -1;
    }

    private List<Task> searchForUserTasks(int id)
    {
        List<Task> userTasks= new ArrayList<>();
        for(Task task : data)
        {
            if(task.getUserId()==id)
                userTasks.add(task);
        }

        return userTasks;
    }

    private int getLastId() {
        int index = data.size()-1;
        return data.get(index).getId();
    }
}
