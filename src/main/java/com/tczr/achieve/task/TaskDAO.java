package com.tczr.achieve.task;

import com.tczr.achieve.service.scheduling.Schedule;
import com.tczr.achieve.shared.Crud;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskDAO implements Crud<Task> {
    private static  final String taskTitleReq ="(^[^\\d]\\w+).*";
    private final JdbcTemplate jdbcTemplate;
   /* private final static String name = "Task";*/

    private final RowMapper rowMapper = (res, rowNumber)->{
        /*
        TODO: make less code with builder
           (resultSet to fetch data from the builder)
            and can be generic to build any type of object
        */
        Task task = new Task( res.getInt("userId") );
        task.setId( res.getInt("taskId") );
        task.setName( res.getString("taskName") );
        task.setStatus( Status.convertToState(res.getString("taskStatus")) );
        task.setReminder( res.getInt("taskReminder")  == 1 ? true : false  );

        try {
            Schedule schedule = checkSchedule(res);
            task.setSchedule(schedule);
        }catch (Exception e){System.out.println(e.getMessage());}

        task.setCreatedAt( LocalDate.parse(
                res.getString("createdAt"))
            );

        return  task;

    };

    public TaskDAO(JdbcTemplate jdbcTemplate) { this.jdbcTemplate=jdbcTemplate; }

    private final Schedule checkSchedule(ResultSet res) throws SQLException {

       String start = res.getString("scheduled_start"),
                end = res.getString("scheduled_end");


        if(start != null && end != null)
           return new Schedule(
                    LocalDate.parse(start), LocalDate.parse(end) );
        return null;
    }

    public List<Task> getList()
    {

        return  jdbcTemplate.query(
                "SELECT * FROM todoTasks",
                rowMapper);
    }


    @Deprecated
    @Override
    public Optional<Task> getObjectById(int id)
    {
        //String query = getQueryOf(nameOrId);
        Task task = (Task) jdbcTemplate.queryForObject(
               "SELECT * FROM todoTasks WHERE taskId=?",
                rowMapper,
                id
        );
        return Optional.ofNullable(task);
    }

    @Override
    public boolean insert(Task task)
    {
        return jdbcTemplate.update(
                "INSERT INTO todoTasks (userId, taskName," +
                        " taskStatus, taskReminder," +
                        " scheduled_start, scheduled_end, createdAt) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                task.getUserId(), task.getName(),
                task.getStatus().toString().toLowerCase(), task.isReminding(),
                task.getSchedule().getStart(), task.getSchedule().getEndDate(),

                task.getCreatedAt()

        ) == 1;
    }

    @Override
    public boolean update(Task task) {

        return jdbcTemplate.update(
                "UPDATE todoTasks SET taskName=?," +
                        " taskStatus=?, taskReminder=?," +
                        " scheduled_start=?, scheduled_end=? WHERE taskId=? ",
                task.getName(), task.getStatus().toString(),
                task.isReminding(), task.getSchedule().getStart(),
                task.getSchedule().getEndDate(),
                //the above shows updated fields where id equals to the task id
                task.getId()
        )==1;
    }

    @Override
    public boolean delete(Task task) {
        return jdbcTemplate.update(
                "DELETE FROM todoTasks WHERE taskId=?",
                task.getId()
        )==1;
    }

   /* public String getName(){ return  name; }*/

    private String getQueryOf(String nameOrId){
        //check if requested query with name
        if(nameOrId.matches(taskTitleReq)){
            return "SELECT * FROM todoTasks WHERE taskName=?";
        }
        //default
        return "SELECT * FROM todoTasks WHERE taskId=?";
    }
}
