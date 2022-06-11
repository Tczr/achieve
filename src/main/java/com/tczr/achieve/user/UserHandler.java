package com.tczr.achieve.user;

import com.tczr.achieve.service.Helper;
import com.tczr.achieve.shared.Handler;
import com.tczr.achieve.shared.Authorizable;
import com.tczr.achieve.shared.Crud;
import com.tczr.achieve.task.TaskHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserHandler implements Handler<User>, Authorizable<User> {
    private static final String USER_NAME_REG ="(^[^\\d]\\w+)(\\-*)([\\w\\d]*)";
    private static final String USER_EMAIL_REG = "^([^\\d]\\w+).*(\\@\\w+)(\\.\\w+)";
    private static final  String ID_REG="^\\d+";

    @Autowired
    private final TaskHandler taskHandler;
    @Autowired
    private final Crud userDAO;
    private static List<User> data=new ArrayList<>();
    private static int lastid ;


    public UserHandler(Crud userDAO, TaskHandler taskHandler)
    {
        this.userDAO=userDAO;
        this.taskHandler = taskHandler;
        data=userDAO.getList();
        Helper.quickSort(data);
        lastid=getLastId(data);

        setUserTasks();
    }

    @Override
    public User getBy(Object param) {
    return selectQueryOf((String)param);
    }

    public User getById(int id) {
        int index = getIndex(id);
        return data.get(index);
    }

    public User getByName(String name){ return searchFor(name,"userName"); }

    public User getByEmail(String email){  return searchFor(email); }

    @Override
    public List<User> getList() {  return data;}

    @Override
    public boolean update(User object) {
        int index = getIndex(object.getId());
        if(index!=-1)
            data.set(index, object);
        return userDAO.update(object);
    }

    @Override
    public boolean add(User object) {
        boolean inserted = false;
        try {
            inserted= userDAO.insert(object);
        }catch (Exception ex){ex.printStackTrace();}
        finally {
            if(inserted)
            {
                lastid++;
                object.setUserId(lastid);
                data.add(object);
            }
        }
        return inserted;
    }

    @Override
    public boolean delete(User object) {
        data.remove(object);
        return userDAO.delete(object);
    }

    @Override
    public boolean delete(int id) {
        User user = null;
        int index = getIndex(id);
        if(index != -1)
            user = data.remove(index);

        return userDAO.delete(user);
    }

    /** Authentication Section:
     *
     */
    @Override
    public Optional<User> checkInfo(Object email, Object password) throws NoSuchElementException {

        User user = searchFor((String) email,"email");

        // return either the current user or an empty user avoiding NullPointerException
        // the check is must, because the pass but if user was null then logically the email is incorrect therefore it's necessary
        return (user!=null && user.getPassword().equals((String) password)) ? Optional.ofNullable(user) : Optional.empty();
    }

    @Override
    public long getToken(User obj) throws NoSuchElementException {
        return 0;
    }

    /** Helper function Section :
     *
     *  -check : to check of userInfo specifically authentication
     *  -getIndex : to get the position of the element we are looking for
     *  -getLastElement : to get last element's id
     *  -searchFor : to  search for element by different ways other than searching for id
     *  -selectQueryFor : to select the right query for getting the object
     *
     * */

    public int getIndex(int id) {
        int start = 0, end = data.size();
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

    private int getLastId(List<User> data) {
        int index = data.size()-1;
        return data.get(data.size()-1).getId();
    }

    private User searchFor(String string, String...type ){
        //.NoSuchElementException
        if (type.equals("userName"))
                        {return data.stream()
                        .filter( (result) -> result.getUserName().equals(string) )
                        .findAny()
                        .get();}


        //default
        return data.stream()
                .filter( (result) -> result.getUserEmail().equals(string) )
                .findAny()
                .get();
    }


    private User selectQueryOf(String obj){
        if(obj.matches(ID_REG))
            return getById(Integer.parseInt(obj));

        else if(obj.matches(USER_EMAIL_REG))
            return  getByEmail(obj);
        //default
        return getByName(obj);
    }



    private void setUserTasks(){
        data.stream().forEach(
                user -> {
                   List usertasks = this.taskHandler.getUserTask(user.getId());
                   user.setTodoList(usertasks);
                }
        );
    }
}
