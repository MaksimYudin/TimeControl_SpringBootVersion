package com.hibernate.crud.operations.dao;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.idao.IGroupTypeDAO;
import org.hibernate.query.Query;
import org.russianfeature.model.GroupType;

import java.util.List;
import java.util.Map;

public class GroupTypeDAO extends GenericDAO<GroupType, Integer> implements IGroupTypeDAO {

    @Override
    public GroupType findByName(String groupKindName) {
        GroupType groupType = null;
        String sql = "select s from GroupType s where s.groupKindName = :groupKindName";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("groupKindName", groupKindName);
        groupType = findOne(query);
        return groupType;
    }

    @Override
    public List<GroupType> getDoubles(Map<String, String> params) {
        String groupKindName = params.get("groupKindName");
        String groupTypeName = params.get("groupTypeName");
        String id = params.get("id");

        String sql = "select s from GroupType s where s.groupKindName = :groupKindName " +
                "and s.groupTypeName = :groupTypeName and s.id <> :id";

        Query query = HibernateUtil.getSession().createQuery(sql)
                .setParameter("groupKindName", groupKindName)
                .setParameter("groupTypeName", groupTypeName)
                .setParameter("id", Integer.valueOf(id));

        List<GroupType> groupTypeDoubles = findMany(query);
        return groupTypeDoubles;
    }
}
