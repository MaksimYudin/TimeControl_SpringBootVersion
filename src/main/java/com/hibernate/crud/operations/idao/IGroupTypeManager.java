package com.hibernate.crud.operations.idao;

import org.russianfeature.model.GroupType;
import org.russianfeature.model.Student;

import java.util.List;
import java.util.Map;

public interface IGroupTypeManager {

    GroupType findByName(String name);

    void delete(GroupType student);

    List<GroupType> getAll();

    GroupType findById(int id);

    void save(GroupType gt);

    void create(GroupType gt);

    List<GroupType> getDoubles(Map<String, String> params);

}
