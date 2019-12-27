package com.hibernate.crud.operations.idao;

import org.russianfeature.model.Student;
import org.russianfeature.model.Teacher;

import java.util.List;
import java.util.Map;

public interface ITeacherManager {

    Teacher findByName(String name, String surname);

    void delete(Teacher entity);

    List<Teacher> getAll();

    Teacher findById(int id);

    void save(Teacher entity);

    void create(Teacher entity);

    List<Teacher> getDoubles(Map<String, String> params);

}
