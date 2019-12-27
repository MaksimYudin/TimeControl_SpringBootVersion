package com.hibernate.crud.operations.dao;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.idao.ICartDAO;
import com.hibernate.crud.operations.idao.IUserDAO;
import org.hibernate.query.Query;
import org.russianfeature.model.Cart;

public class CartDAO extends GenericDAO<Cart, Integer> implements ICartDAO {

    @Override
    public Cart findByName(String name) {
        Cart gt = null;
        String sql = "select s from Cart s where s.name = :name";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("name", name);
        gt = findOne(query);
        return gt;
    }
    /*
    @Override
    public List<User> getDoubles(Map<String, String> params) {
        String name = params.get("name");
        String id = params.get("id");

        String sql = "select s from User s where s.name = :name " +
                "and s.id <> :id";

        Query query = HibernateUtil.getSession().createQuery(sql)
                .setParameter("name", name)
                .setParameter("id", Integer.valueOf(id));

        List<User> gtDoubles = findMany(query);
        return gtDoubles;
    }
    */
}
