package com.hibernate.crud.operations.idao;

import org.russianfeature.model.Cart;

public interface ICartDAO extends IGenericDAO<Cart, Integer> {

    Cart findByName(String name);

    //List<User> getDoubles(Map<String, String> params);
}
