package com.tczr.achieve.service.scheduling;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDate;

/*@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "scheduler")
@JsonSubTypes.Type(value = Schedule.class, name = "scheduler")*/
public interface Scheduler {
    void setCalender(LocalDate start, LocalDate end);
    void dailyTimeRepetition();
    LocalDate getStart();
    LocalDate getEndDate();
    long getDailyRepetition();



}
