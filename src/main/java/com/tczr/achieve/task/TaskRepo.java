package com.tczr.achieve.task;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface TaskRepo extends CrudRepository<Task, Integer> {

    List<Task> getAllByUserId(int userId, Sort sort);
    List<Task> getAllByUserId(int userId);

}
