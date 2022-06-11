package com.tczr.achieve.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.tczr.achieve.service.scheduling.Schedule;
import com.tczr.achieve.service.scheduling.Scheduler;
import com.tczr.achieve.shared.RegularProcedure;
import lombok.*;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Task implements TodoTemplate, RegularProcedure {
    private int userId;
    private int id;
    private String name;
    private Status status;
    private boolean reminder;
    private Schedule schedule;
    private LocalDate createdAt;

    public Task(int userId) { this.userId=userId;}
    public Task(int userId, String name, String status, boolean reminder)
    {
        this.userId=userId;
        this.name = name;
        this.status = Status.convertToState(status);
        this.reminder = reminder;
    }

    public Task(
            int userId, int id,  String name,
            Status status,boolean reminder,
           Schedule schedule)
    {
        this.userId=userId;
        this.id = id;
        this.name = name;
        this.status = status;
        this.reminder = reminder;
        this.schedule = schedule;

    }

    public int isReminding()
    {
        return reminder ? 1 : 0;
    }

    /*public void Scheduler(Scheduler scheduler){ this.scheduler = scheduler;

        System.out.println("from set scheduler--"+ scheduler.getStart());}*/

}
