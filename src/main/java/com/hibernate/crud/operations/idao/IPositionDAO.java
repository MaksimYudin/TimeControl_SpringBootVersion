package com.hibernate.crud.operations.idao;

import org.apache.commons.math3.util.MathArrays;
import org.russianfeature.model.Position;
import org.russianfeature.model.Regime;

import java.util.List;
import java.util.Map;

public interface IPositionDAO extends IGenericDAO<Position, Integer> {

    Position findByName(String name);

    List<Position> getDoubles(Map<String, String> params);
}
