package com.hibernate.crud.operations.idao;

import org.russianfeature.model.Lesson;
import org.russianfeature.model.User;

import java.util.List;
import java.util.Map;

public interface IUserDAO extends IGenericDAO<User, Integer> {

    User findByName(String name);

    //List<User> getDoubles(Map<String, String> params);
}
