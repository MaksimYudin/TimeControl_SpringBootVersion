package com.hibernate.crud.operations.manager;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.dao.GroupDOODAO;
import com.hibernate.crud.operations.dao.GroupTypeDAO;
import com.hibernate.crud.operations.idao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.russianfeature.model.GroupDOO;
import org.russianfeature.model.GroupType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupDOOManager implements IGroupDOOManager {
//public class GroupDOOManager<T> implements IManager<T> {
    private IGroupDOODAO groupDOODAO = new GroupDOODAO();
    @Override
    public GroupDOO findByName(String name) {
        GroupDOO gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = groupDOODAO.findByName(name);
            if (gt != null) Hibernate.initialize(gt.getStudents());
            HibernateUtil.commitTransaction();
        } catch (NonUniqueResultException ex) {
            System.out.println("Query returned more than one results.");
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void delete(GroupDOO gt) {
        try {
            HibernateUtil.beginTransaction();
            groupDOODAO.delete(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<GroupDOO> getAll() {
        List<GroupDOO> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = groupDOODAO.findAll(GroupDOO.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    @Override
    public GroupDOO findById(int id) {
        GroupDOO gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = groupDOODAO.findByID(GroupDOO.class, id);
            if (gt != null) Hibernate.initialize(gt.getStudents());
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void save(GroupDOO gt) {
        try {
            HibernateUtil.beginTransaction();
            groupDOODAO.save(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            //System.out.println(ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public void create(GroupDOO gt) {
        try {
            HibernateUtil.beginTransaction();
            groupDOODAO.create(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<GroupDOO> getDoubles(Map<String, String> params) {
        List<GroupDOO> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = groupDOODAO.getDoubles(params);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    public static ObservableList<GroupDOO> getGroupDOOObservableList() {
        GroupDOOManager manager = new GroupDOOManager();
        List<GroupDOO> list = manager.getAll();
        ObservableList<GroupDOO> oList = FXCollections.observableArrayList(list);

        return oList;
    }


}
