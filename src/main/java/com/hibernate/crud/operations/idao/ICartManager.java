package com.hibernate.crud.operations.idao;

import org.russianfeature.model.Cart;

import java.util.List;

public interface ICartManager {

    Cart findByName(String name);

    void delete(Cart entity);

    List<Cart> getAll();

    Cart findById(int id);

    void save(Cart gt);

    void create(Cart gt);

    //List<Lesson> getDoubles(Map<String, String> params);

}
