package com.tczr.achieve.shared;

import java.util.List;
import java.util.Optional;

public interface Crud<T>{

    List<T> getList();
    Optional<T> getObjectById(int id);

    boolean insert(T object);

    boolean update(T object);

    boolean delete(T object);


}
