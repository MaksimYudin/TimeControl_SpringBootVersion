package com.hibernate.crud.operations.idao;

import org.russianfeature.model.CartDetail;

import java.util.List;

public interface ICartDetailManager {

    CartDetail findByName(String name);

    void delete(CartDetail entity);

    List<CartDetail> getAll();

    CartDetail findById(int id);

    void save(CartDetail gt);

    void create(CartDetail gt);

    //List<Lesson> getDoubles(Map<String, String> params);

}
