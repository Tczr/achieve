package com.tczr.achieve.service;

import javax.mail.Session;
import java.util.Properties;

public class SMTPgoogle implements EmailService{
    private final static String ADMIN_EMAIL ;
    private final static String ADMIN_PASSWORD ;

    static {
        // TODO: fetching information from properties file
        ADMIN_EMAIL = null;
        ADMIN_PASSWORD = null;
    }

    @Override
    public Properties config() {
//        TODO: configuration of google host
        return null;
    }

    @Override
    public Session authenticate() {
//        TODO: Authenticate the ADMIN registration
        return null;
    }

    @Override
    public String send(Object receiver, String message, String title) {
//        TODO: Send an email to receiver
        return null;
    }
}
