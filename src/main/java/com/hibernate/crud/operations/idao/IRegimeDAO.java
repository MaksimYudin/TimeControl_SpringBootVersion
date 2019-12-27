package com.hibernate.crud.operations.idao;

import org.russianfeature.model.GroupType;
import org.russianfeature.model.Regime;

import java.util.List;
import java.util.Map;

public interface IRegimeDAO extends IGenericDAO<Regime, Integer> {

    Regime findByName(String name);

    List<Regime> getDoubles(Map<String, String> params);
}
