package com.hibernate.crud.operations.idao;

import org.russianfeature.model.Student;
import java.util.List;
import java.util.Map;

public interface IStudentDAO extends IGenericDAO<Student, Integer> {

    Student findByName(String name, String surname);

    List<Student> getDoubles(Map<String, String> params);
}
