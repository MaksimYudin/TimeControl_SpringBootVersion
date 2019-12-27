package com.hibernate.crud.operations.manager;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.dao.CartDetailDAO;
import com.hibernate.crud.operations.idao.ICartDetailDAO;
import com.hibernate.crud.operations.idao.ICartDetailManager;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.russianfeature.model.Cart;
import org.russianfeature.model.CartDetail;

import java.util.ArrayList;
import java.util.List;

public class CartDetailManager implements ICartDetailManager {
    private ICartDetailDAO cartDetailDAO = new CartDetailDAO();
    @Override
    public CartDetail findByName(String name) {
        CartDetail gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = cartDetailDAO.findByName(name);
            HibernateUtil.commitTransaction();
        } catch (NonUniqueResultException ex) {
            System.out.println("Query returned more than one results.");
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void delete(CartDetail gt) {
        try {
            HibernateUtil.beginTransaction();
            cartDetailDAO.delete(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<CartDetail> getAll() {
        List<CartDetail> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = cartDetailDAO.findAll(Cart.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    @Override
    public CartDetail findById(int id) {
        CartDetail gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = cartDetailDAO.findByID(CartDetail.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void save(CartDetail gt) {
        try {
            HibernateUtil.beginTransaction();
            cartDetailDAO.save(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            //System.out.println(ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public void create(CartDetail gt) {
        try {
            HibernateUtil.beginTransaction();
            cartDetailDAO.create(gt);
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
