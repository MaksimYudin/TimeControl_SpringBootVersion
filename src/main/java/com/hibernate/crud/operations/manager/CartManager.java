package com.hibernate.crud.operations.manager;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.dao.CartDAO;
import com.hibernate.crud.operations.idao.ICartDAO;
import com.hibernate.crud.operations.idao.ICartManager;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.russianfeature.model.Cart;
import org.russianfeature.model.CartDetail;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CartManager implements ICartManager {
    private ICartDAO cartDAO = new CartDAO();
    @Override
    public Cart findByName(String name) {
        Cart gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = cartDAO.findByName(name);
            HibernateUtil.commitTransaction();
        } catch (NonUniqueResultException ex) {
            System.out.println("Query returned more than one results.");
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void delete(Cart gt) {
        try {
            HibernateUtil.beginTransaction();
            cartDAO.delete(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<Cart> getAll() {
        List<Cart> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = cartDAO.findAll(Cart.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    @Override
    public Cart findById(int id) {
        Cart gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = cartDAO.findByID(Cart.class, id);
            Hibernate.initialize(gt.getCartDetails());
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    public Cart findByIdOpenSession(int id) {
        Cart gt = null;
        try {
            Session session = HibernateUtil.getOpenSession();
            gt = (Cart) session.get(Cart.class, id);
            //gt = (Cart) session.load(Cart.class, id);
            Hibernate.initialize(gt.getCartDetails());
            session.close();
            int t1 = 0;
            //HibernateUtil.beginTransaction();
            //gt = cartDAO.findByID(Cart.class, id);
            //HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    public Cart findByIdCurrentSessionQuery(int id) {
        Cart gt = null;
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            String sql = "select s from Cart s join fetch s.cartDetails where s.cartId = :cartId";
            Query query = HibernateUtil.getSession().createQuery(sql).setParameter("cartId", 29);
            gt = (Cart) query.uniqueResult();
            session.getTransaction().commit();
            int t1 = 0;
        } catch (HibernateException ex) {
            System.out.println(ex);
        }

        return gt;
    }

    public Cart test(int id) {
        Cart gt = null;
        try {
            Session session = HibernateUtil.getOpenSession();

            Cart loadCart = (Cart) session.load(Cart.class, id);
            System.out.println(loadCart.getClass().getName());

            Cart getCart = (Cart) session.get(Cart.class, id);
            System.out.println(getCart.getClass().getName());

            int t = 0;
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    //@Transactional
    public Set<CartDetail> getCartDetail(Cart cart) {
        Set<CartDetail> cartDetail = null;
        try {
            Session session = HibernateUtil.getOpenSession();
            //session.beginTransaction();
            //cartDetail = cart.getCartDetails();
            //session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            System.out.printf(e.toString());
        }
        return cartDetail;
    }

    @Override
    public void save(Cart gt) {
        try {
            HibernateUtil.beginTransaction();
            cartDAO.save(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            //System.out.println(ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public void create(Cart gt) {
        try {
            HibernateUtil.beginTransaction();
            cartDAO.create(gt);
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
