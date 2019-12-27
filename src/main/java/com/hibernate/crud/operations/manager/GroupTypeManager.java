package com.hibernate.crud.operations.manager;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.dao.GroupTypeDAO;
import com.hibernate.crud.operations.dao.StudentDAO;
import com.hibernate.crud.operations.idao.IGroupTypeDAO;
import com.hibernate.crud.operations.idao.IGroupTypeManager;
import com.hibernate.crud.operations.idao.IStudentDAO;
import com.hibernate.crud.operations.idao.IStudentManger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.russianfeature.model.GroupDOO;
import org.russianfeature.model.GroupType;
import org.russianfeature.model.Student;
import org.russianfeature.model.StudentLoadInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GroupTypeManager implements IGroupTypeManager {
    private IGroupTypeDAO groupTypeDAO = new GroupTypeDAO();
    @Override
    public GroupType findByName(String name) {
        GroupType gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = groupTypeDAO.findByName(name);
            HibernateUtil.commitTransaction();
        } catch (NonUniqueResultException ex) {
            System.out.println("Query returned more than one results.");
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void delete(GroupType gt) {
        try {
            HibernateUtil.beginTransaction();
            groupTypeDAO.delete(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<GroupType> getAll() {
        List<GroupType> gtList = new ArrayList<GroupType>();
        try {
            HibernateUtil.beginTransaction();
            gtList = groupTypeDAO.findAll(GroupType.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    @Override
    public GroupType findById(int id) {
        GroupType gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = groupTypeDAO.findByID(GroupType.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void save(GroupType gt) {
        try {
            HibernateUtil.beginTransaction();
            groupTypeDAO.save(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            //System.out.println(ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public void create(GroupType gt) {
        try {
            HibernateUtil.beginTransaction();
            groupTypeDAO.create(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<GroupType> getDoubles(Map<String, String> params) {
        List<GroupType> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = groupTypeDAO.getDoubles(params);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    public static ObservableList<GroupType> getGroupTypeObservableList() {
        GroupTypeManager manager = new GroupTypeManager();
        List<GroupType> list = manager.getAll();
        ObservableList<GroupType> oList = FXCollections.observableArrayList(list);

        return oList;
    }


}
