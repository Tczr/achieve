package com.tczr.achieve.task;

public enum Status {
    ACTIVE,
    DELAYED,
    FINISHED;
    Status(){};
//    convertor basic-> we want to achieve this in a smart way
    public static Status convertToState(String text)
    {
        if(text.equalsIgnoreCase(DELAYED.toString())) return DELAYED;

        else if (text.equalsIgnoreCase(FINISHED.toString())) return  FINISHED;

        //default value
        return ACTIVE;

    }
}
