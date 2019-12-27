package com.hibernate.crud.operations.dao;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.idao.ILessonDAO;
import com.hibernate.crud.operations.idao.IUserDAO;
import org.hibernate.query.Query;
import org.russianfeature.model.Lesson;
import org.russianfeature.model.User;

import java.util.List;
import java.util.Map;

public class UserDAO extends GenericDAO<User, Integer> implements IUserDAO {

    @Override
    public User findByName(String name) {
        User gt = null;
        String sql = "select s from User s where s.name = :name";
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
