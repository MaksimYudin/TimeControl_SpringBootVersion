package com.hibernate.crud.operations.idao;

import org.hibernate.query.Query;
import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T, ID extends Serializable> {
    void save(T entity);

    void create(T entity);

    void merge(T entity);

    void delete(T entity);

    List<T> findMany(Query query);

    T findOne(Query query);

    List findAll(Class clazz);

    T findByID(Class clazz, int id);
}
