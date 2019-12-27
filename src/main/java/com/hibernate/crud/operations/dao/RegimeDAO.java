package com.hibernate.crud.operations.dao;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.idao.IGroupDOODAO;
import com.hibernate.crud.operations.idao.IRegimeDAO;
import org.hibernate.query.Query;
import org.russianfeature.model.GroupDOO;
import org.russianfeature.model.Regime;

import java.util.List;
import java.util.Map;

public class RegimeDAO extends GenericDAO<Regime, Integer> implements IRegimeDAO {

    @Override
    public Regime findByName(String groupName) {
        Regime gt = null;
        String sql = "select s from Regime s where s.groupName = :name";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("name", groupName);
        gt = findOne(query);
        return gt;
    }

    @Override
    public List<Regime> getDoubles(Map<String, String> params) {
        String name = params.get("mame");
        String id = params.get("id");

        String sql = "select s from Regime s where s.name = :name " +
                "and s.id <> :id";

        Query query = HibernateUtil.getSession().createQuery(sql)
                .setParameter("name", name)
                .setParameter("id", Integer.valueOf(id));

        List<Regime> doubles = findMany(query);
        return doubles;
    }
}
