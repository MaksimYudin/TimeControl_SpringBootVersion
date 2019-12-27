package com.hibernate.crud.operations.dao;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.idao.IGroupDOODAO;
import com.hibernate.crud.operations.idao.ILessonDAO;
import org.hibernate.query.Query;
import org.russianfeature.model.GroupDOO;
import org.russianfeature.model.Lesson;

import java.util.List;
import java.util.Map;

public class LessonDAO extends GenericDAO<Lesson, Integer> implements ILessonDAO {

    @Override
    public Lesson findByName(String name) {
        Lesson gt = null;
        String sql = "select s from Lesson s where s.name = :name";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("name", name);
        gt = findOne(query);
        return gt;
    }

    @Override
    public List<Lesson> getDoubles(Map<String, String> params) {
        String name = params.get("name");
        String id = params.get("id");

        String sql = "select s from Lesson s where s.name = :name " +
                "and s.id <> :id";

        Query query = HibernateUtil.getSession().createQuery(sql)
                .setParameter("name", name)
                .setParameter("id", Integer.valueOf(id));

        List<Lesson> gtDoubles = findMany(query);
        return gtDoubles;
    }
}
