package com.hibernate.crud.operations.dao;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.idao.IGenericDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDAO<T, ID extends Serializable> implements IGenericDAO<T, ID> {

    protected Session getSession() {
        return HibernateUtil.getSession();
    }

    protected Session getOpenSession() {
        return HibernateUtil.getOpenSession();
    }

    public void save(T entity) {
        Session session = getSession();
        session.saveOrUpdate(entity);
    }

    public void create(T entity) {
        Session session = getSession();
        session.persist(entity);
    }

    public void merge(T entity) {
        Session session = getSession();
        session.merge(entity);
    }

    public void delete(T entity) {
        Session session = getSession();
        session.refresh(entity);
        session.delete(entity);
    }

    public List<T> findMany(Query query) {
        List<T> list;
        list = (List<T>) query.list();
        return list;
    }

    public T findOne(Query query) {
        T t;
        t = (T) query.uniqueResult();
        return t;
    }

    public T findByID(Class clazz, int id) {
        Session session = getSession();
        T t = (T) session.get(clazz, id);
        return t;
    }

    public T findByIDOpenSession(Class clazz, int id) {
        Session session = getOpenSession();
        T t = (T) session.get(clazz, id);
        return t;
    }


    public List findAll(Class clazz) {
        Session session = getSession();
        //session.beginTransaction();
        Query query = session.createQuery("from " + clazz.getName());
        List t = query.list();

        return t;
    }

}
