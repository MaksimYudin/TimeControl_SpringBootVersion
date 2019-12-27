package com.hibernate.crud.operations.manager;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.dao.GroupDOODAO;
import com.hibernate.crud.operations.dao.RegimeDAO;
import com.hibernate.crud.operations.idao.IGroupDOODAO;
import com.hibernate.crud.operations.idao.IGroupDOOManager;
import com.hibernate.crud.operations.idao.IRegimeDAO;
import com.hibernate.crud.operations.idao.IRegimeManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.russianfeature.model.GroupDOO;
import org.russianfeature.model.GroupType;
import org.russianfeature.model.Position;
import org.russianfeature.model.Regime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegimeManager implements IRegimeManager {
    private IRegimeDAO regimeDAO = new RegimeDAO();
    @Override
    public Regime findByName(String name) {
        Regime gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = regimeDAO.findByName(name);
            HibernateUtil.commitTransaction();
        } catch (NonUniqueResultException ex) {
            System.out.println("Query returned more than one results.");
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void delete(Regime gt) {
        try {
            HibernateUtil.beginTransaction();
            regimeDAO.delete(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<Regime> getAll() {
        List<Regime> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = regimeDAO.findAll(Regime.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    @Override
    public Regime findById(int id) {
        Regime gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = regimeDAO.findByID(Regime.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void save(Regime gt) {
        try {
            HibernateUtil.beginTransaction();
            regimeDAO.save(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            //System.out.println(ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public void create(Regime gt) {
        try {
            HibernateUtil.beginTransaction();
            regimeDAO.create(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<Regime> getDoubles(Map<String, String> params) {
        List<Regime> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = regimeDAO.getDoubles(params);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    public static ObservableList<Regime> getRegimeObservableList() {
        RegimeManager manager = new RegimeManager();
        List<Regime> list = manager.getAll();
        ObservableList<Regime> oList = FXCollections.observableArrayList(list);

        return oList;
    }

}
