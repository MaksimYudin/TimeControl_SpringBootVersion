package com.hibernate.crud.operations.idao;

import org.russianfeature.model.GroupDOO;
import org.russianfeature.model.GroupType;

import java.util.List;
import java.util.Map;

public interface IGroupDOOManager {

    GroupDOO findByName(String name);

    void delete(GroupDOO entity);

    List<GroupDOO> getAll();

    GroupDOO findById(int id);

    void save(GroupDOO gt);

    void create(GroupDOO gt);

    List<GroupDOO> getDoubles(Map<String, String> params);

}
