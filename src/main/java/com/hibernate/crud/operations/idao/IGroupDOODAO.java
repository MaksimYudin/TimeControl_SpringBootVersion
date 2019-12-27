package com.hibernate.crud.operations.idao;

import org.russianfeature.model.GroupDOO;
import org.russianfeature.model.GroupType;

import java.util.List;
import java.util.Map;

public interface IGroupDOODAO extends IGenericDAO<GroupDOO, Integer> {

    GroupDOO findByName(String name);

    List<GroupDOO> getDoubles(Map<String, String> params);
}
