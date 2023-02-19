package com.tczr.achieve.task;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.tczr.achieve.service.scheduling.Schedule;
import com.tczr.achieve.service.scheduling.Scheduler;
import com.tczr.achieve.shared.RegularProcedure;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tczr.achieve.service.StatusConverter;
import com.tczr.achieve.service.scheduling.Schedule;
import com.tczr.achieve.shared.RegularProcedure;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "todoTasks")
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Task implements TodoTemplate, RegularProcedure {
    private int userId;
    @Id
    @GeneratedValue
    @Column(name="taskId")
    private int id;
    @Column(name = "taskName")
    private String text;

    @Column(name = "taskStatus")
    @Convert(converter = StatusConverter.class)
    private Status status;
    @Column(name = "taskReminder")
    private int reminder;
    @Embedded
    private Schedule schedule;

    private LocalDate createdAt;

    public Task(int userId) { this.userId=userId;}
    public Task(int userId, String text, String status, int reminder)
    {
        this.userId=userId;
        this.text = text;
        this.status = Status.convertToState(status);
        this.reminder = reminder;
    }


    public int isReminding()
    {
        return reminder>0 ? 1 : 0;
    }

    /*public void Scheduler(Scheduler scheduler){ this.scheduler = scheduler;

        System.out.println("from set scheduler--"+ scheduler.getStart());}*/

}
