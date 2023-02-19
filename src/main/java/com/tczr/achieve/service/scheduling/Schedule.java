package com.tczr.achieve.service.scheduling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@ToString
@Embeddable
public class Schedule implements Scheduler{
    @Column(name = "scheduled_start")
    private LocalDate start;
    @Column(name = "scheduled_end")
    private LocalDate end;
    @Transient
    private long period;

    public Schedule()
    {}
    public Schedule(LocalDate start,LocalDate end)
    {
        setCalender(start, end);
        dailyTimeRepetition();
    }
    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
        setPeriod();
    }

    public void  setPeriod(){
        dailyTimeRepetition();
    }

    @Override
    public void setCalender(LocalDate start, LocalDate end) {
        this.start=start; this.end = end;
    }

    @Override
    public void dailyTimeRepetition() {
        period =  DAYS.between(start,end);
    }

    @Override
    public LocalDate getStart() {
        return start;
    }

    @Override
    public LocalDate getEndDate() {
        return end;
    }

    @Override
    public long getDailyRepetition() {

        return period;
    }

}
