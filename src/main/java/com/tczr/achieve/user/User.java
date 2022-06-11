package com.tczr.achieve.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tczr.achieve.shared.RegularProcedure;
import com.tczr.achieve.task.Task;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor

public class User implements RegularProcedure {
    private int userId;
    private String userName;
    private String userEmail;
    private String password;
    private LocalDate createdAt;
    private List<Task> todoList;
    public User(int id){this.userId=id;}
    public User(int id, String name){this.userName=name; this.userId=id;};

    @Override
    public int getId() {
        return getUserId();
    }
}
