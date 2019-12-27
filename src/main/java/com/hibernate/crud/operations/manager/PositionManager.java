package com.hibernate.crud.operations.manager;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.dao.PositionDAO;
import com.hibernate.crud.operations.dao.RegimeDAO;
import com.hibernate.crud.operations.idao.IPositionDAO;
import com.hibernate.crud.operations.idao.IPositionManager;
import com.hibernate.crud.operations.idao.IRegimeDAO;
import com.hibernate.crud.operations.idao.IRegimeManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.russianfeature.model.Lesson;
import org.russianfeature.model.Position;
import org.russianfeature.model.Regime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PositionManager implements IPositionManager {
    private IPositionDAO positionDAO = new PositionDAO();
    @Override
    public Position findByName(String name) {
        Position gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = positionDAO.findByName(name);
            HibernateUtil.commitTransaction();
        } catch (NonUniqueResultException ex) {
            System.out.println("Query returned more than one results.");
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void delete(Position gt) {
        try {
            HibernateUtil.beginTransaction();
            positionDAO.delete(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<Position> getAll() {
        List<Position> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = positionDAO.findAll(Position.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    @Override
    public Position findById(int id) {
        Position gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = positionDAO.findByID(Regime.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void save(Position gt) {
        try {
            HibernateUtil.beginTransaction();
            positionDAO.save(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            //System.out.println(ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public void create(Position gt) {
        try {
            HibernateUtil.beginTransaction();
            positionDAO.create(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<Position> getDoubles(Map<String, String> params) {
        List<Position> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = positionDAO.getDoubles(params);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    public static ObservableList<Position> getPositionObservableList() {
        PositionManager manager = new PositionManager();
        List<Position> list = manager.getAll();
        ObservableList<Position> oList = FXCollections.observableArrayList(list);

        return oList;
    }

}
