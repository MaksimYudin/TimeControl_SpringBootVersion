package com.hibernate.crud.operations.idao;

import org.russianfeature.model.CartDetail;

public interface ICartDetailDAO extends IGenericDAO<CartDetail, Integer> {

    CartDetail findByName(String name);

    //List<User> getDoubles(Map<String, String> params);
}
