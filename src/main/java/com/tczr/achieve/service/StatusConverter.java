package com.tczr.achieve.service;

import com.tczr.achieve.task.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status.toString();
    }

    @Override
    public Status convertToEntityAttribute(String s) {
        return Status.convertToState(s);
    }
}
