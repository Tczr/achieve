package com.tczr.achieve.service.scheduling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@ToString
public class Schedule implements Scheduler{
    private LocalDate start;
    private LocalDate end;
    private long period;

    public Schedule()
    {
        /*start = LocalDate.now();
        end= LocalDate.MAX;
        dailyTimeRepetition();*/
    }
    public Schedule(LocalDate start,LocalDate end)
    {
        setCalender(start, end);
        dailyTimeRepetition();
    }

/*
    @JsonCreator
    public Schedule(@JsonProperty("start") String start,@JsonProperty("endDate") String end)
    {
        this.start= LocalDate.parse(start);
        this.end=LocalDate.parse(end);
        dailyTimeRepetition();
    }
*/

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
