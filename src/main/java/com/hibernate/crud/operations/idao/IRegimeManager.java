package com.hibernate.crud.operations.idao;

import org.russianfeature.model.GroupType;
import org.russianfeature.model.Regime;

import java.util.List;
import java.util.Map;

public interface IRegimeManager {

    Regime findByName(String name);

    void delete(Regime entity);

    List<Regime> getAll();

    Regime findById(int id);

    void save(Regime gt);

    void create(Regime gt);

    List<Regime> getDoubles(Map<String, String> params);

}
