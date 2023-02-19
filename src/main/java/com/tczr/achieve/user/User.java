package com.tczr.achieve.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tczr.achieve.shared.RegularProcedure;
import com.tczr.achieve.task.Task;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class User implements RegularProcedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String userName;
    @Column(name = "userEmail")
    private String email;
    @Column(name = "userPassword")
    private String password;
    private LocalDate createdAt;
    @Transient
    private List<Task> todoList;
    public User(int id){this.userId=id;}
    public User(int id, String name){this.userName=name; this.userId=id;};


}
