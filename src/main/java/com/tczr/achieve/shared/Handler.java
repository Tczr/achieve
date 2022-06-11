package com.tczr.achieve.shared;

import java.util.List;

public interface Handler<T> {

    T getBy(Object nameOrId);
    List<T> getList();
    boolean update(T object);
    boolean add(T object);
    boolean delete(T object);
    boolean delete(int id);


}
