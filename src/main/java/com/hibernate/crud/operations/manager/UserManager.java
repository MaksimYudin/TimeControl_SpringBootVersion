package com.hibernate.crud.operations.manager;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.dao.UserDAO;
import com.hibernate.crud.operations.idao.IUserDAO;
import com.hibernate.crud.operations.idao.IUserManager;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.russianfeature.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements IUserManager {
    private IUserDAO userDAO = new UserDAO();
    @Override
    public User findByName(String name) {
        User gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = userDAO.findByName(name);
            HibernateUtil.commitTransaction();
        } catch (NonUniqueResultException ex) {
            System.out.println("Query returned more than one results.");
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void delete(User gt) {
        try {
            HibernateUtil.beginTransaction();
            userDAO.delete(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<User> getAll() {
        List<User> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = userDAO.findAll(User.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    @Override
    public User findById(int id) {
        User gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = userDAO.findByID(User.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void save(User gt) {
        try {
            HibernateUtil.beginTransaction();
            userDAO.save(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            //System.out.println(ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public void create(User gt) {
        try {
            HibernateUtil.beginTransaction();
            userDAO.create(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    /*
    @Override
    public List<Lesson> getDoubles(Map<String, String> params) {
        List<Lesson> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = userDAO.getDoubles(params);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }
    */

}
