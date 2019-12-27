package com.hibernate.crud.operations.idao;

import org.russianfeature.model.Teacher;

import java.util.List;
import java.util.Map;

public interface ITeacherDAO extends IGenericDAO<Teacher, Integer> {

    Teacher findByName(String name, String surname);

    List<Teacher> getDoubles(Map<String, String> params);
}