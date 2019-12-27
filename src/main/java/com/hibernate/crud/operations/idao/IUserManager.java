package com.hibernate.crud.operations.idao;

import org.russianfeature.model.Lesson;
import org.russianfeature.model.User;

import java.util.List;
import java.util.Map;

public interface IUserManager {

    User findByName(String name);

    void delete(User entity);

    List<User> getAll();

    User findById(int id);

    void save(User gt);

    void create(User gt);

    //List<Lesson> getDoubles(Map<String, String> params);

}
