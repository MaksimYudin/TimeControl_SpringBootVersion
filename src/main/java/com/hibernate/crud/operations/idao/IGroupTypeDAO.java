package com.hibernate.crud.operations.idao;

import org.russianfeature.model.GroupType;
import org.russianfeature.model.Student;

import java.util.List;
import java.util.Map;

public interface IGroupTypeDAO extends IGenericDAO<GroupType, Integer> {

    GroupType findByName(String name);

    List<GroupType> getDoubles(Map<String, String> params);
}
