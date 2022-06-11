package com.tczr.achieve.shared;

import java.util.Optional;

public interface Authorizable<T> {

    Optional checkInfo(Object email, Object password) throws Exception;
    // I'm not sure about this
    long getToken(T obj) throws Exception;

}
