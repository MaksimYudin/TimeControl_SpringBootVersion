package com.hibernate.crud.operations.dao;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.idao.IGroupDOODAO;
import com.hibernate.crud.operations.idao.IGroupTypeDAO;
import org.hibernate.query.Query;
import org.russianfeature.model.GroupDOO;
import org.russianfeature.model.GroupType;

import java.util.List;
import java.util.Map;

public class GroupDOODAO extends GenericDAO<GroupDOO, Integer> implements IGroupDOODAO {

    @Override
    public GroupDOO findByName(String groupName) {
        GroupDOO gt = null;
        String sql = "select s from GroupDOO s where s.groupName = :groupName";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("groupName", groupName);
        gt = findOne(query);
        return gt;
    }

    @Override
    public List<GroupDOO> getDoubles(Map<String, String> params) {
        String groupName = params.get("groupName");
        String id = params.get("id");

        String sql = "select s from GroupDOO s where s.groupName = :groupName " +
                "and s.id <> :id";

        Query query = HibernateUtil.getSession().createQuery(sql)
                .setParameter("groupName", groupName)
                .setParameter("id", Integer.valueOf(id));

        List<GroupDOO> gtDoubles = findMany(query);
        return gtDoubles;
    }
}
