package com.tczr.achieve.user;

import org.springframework.data.repository.CrudRepository;

interface UserRepo extends CrudRepository<User, Integer>{

    User getUserByUserName(String userName);
    User getUserByEmail(String email);
//    boolean updateUserById(int id, User user);

}
