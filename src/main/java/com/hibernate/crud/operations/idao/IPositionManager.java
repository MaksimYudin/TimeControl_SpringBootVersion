package com.hibernate.crud.operations.idao;

import org.russianfeature.model.Position;
import org.russianfeature.model.Regime;

import java.util.List;
import java.util.Map;

public interface IPositionManager {

    Position findByName(String name);

    void delete(Position entity);

    List<Position> getAll();

    Position findById(int id);

    void save(Position gt);

    void create(Position gt);

    List<Position> getDoubles(Map<String, String> params);

}
