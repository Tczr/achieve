package com.tczr.achieve.service;

import javax.mail.Session;
import java.util.Properties;

public interface EmailService {
    Properties config();
    Session authenticate();
    String send(Object receiver,String message,String title);
}
