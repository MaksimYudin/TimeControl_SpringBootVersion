package com.hibernate.crud.operations.idao;

import org.russianfeature.model.Student;

import java.util.List;
import java.util.Map;

public interface IStudentManger {

    Student findByName(String name, String surname);

    void delete(Student entity);

    List<Student> getAll();

    Student findById(int id);

    void save(Student entity);

    void create(Student entity);

    List<Student> getDoubles(Map<String, String> params);

}
