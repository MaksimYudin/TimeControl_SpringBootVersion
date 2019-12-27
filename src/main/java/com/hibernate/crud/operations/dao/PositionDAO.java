package com.hibernate.crud.operations.dao;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.idao.IPositionDAO;
import com.hibernate.crud.operations.idao.IRegimeDAO;
import org.hibernate.query.Query;
import org.russianfeature.model.Position;
import org.russianfeature.model.Regime;

import java.util.List;
import java.util.Map;

public class PositionDAO extends GenericDAO<Position, Integer> implements IPositionDAO {

    @Override
    public Position findByName(String groupName) {
        Position gt = null;
        String sql = "select s from Position s where s.groupName = :name";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("name", groupName);
        gt = findOne(query);
        return gt;
    }

    @Override
    public List<Position> getDoubles(Map<String, String> params) {
        String name = params.get("mame");
        String id = params.get("id");

        String sql = "select s from Position s where s.name = :name " +
                "and s.id <> :id";

        Query query = HibernateUtil.getSession().createQuery(sql)
                .setParameter("name", name)
                .setParameter("id", Integer.valueOf(id));

        List<Position> doubles = findMany(query);
        return doubles;
    }
}
