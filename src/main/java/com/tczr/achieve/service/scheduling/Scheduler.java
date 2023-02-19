package com.tczr.achieve.service.scheduling;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDate;

/*@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "scheduler")
<<<<<<< HEAD
@JsonSubTypes.Type(value = Schedule.class, name = "scheduler")*/
=======
@JsonSubTypes.Type(value = Schedule.class, text = "scheduler")*/
>>>>>>> 5df7255 (refactor the whole project)
public interface Scheduler {
    void setCalender(LocalDate start, LocalDate end);
    void dailyTimeRepetition();
    LocalDate getStart();
    LocalDate getEndDate();
    long getDailyRepetition();



}
