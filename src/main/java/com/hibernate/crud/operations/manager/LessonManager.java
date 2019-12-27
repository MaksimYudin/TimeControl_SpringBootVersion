package com.hibernate.crud.operations.manager;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.dao.LessonDAO;
import com.hibernate.crud.operations.dao.PositionDAO;
import com.hibernate.crud.operations.idao.ILessonDAO;
import com.hibernate.crud.operations.idao.ILessonManager;
import com.hibernate.crud.operations.idao.IPositionDAO;
import com.hibernate.crud.operations.idao.IPositionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.russianfeature.model.GroupType;
import org.russianfeature.model.Lesson;
import org.russianfeature.model.Position;
import org.russianfeature.model.Regime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LessonManager implements ILessonManager {
    private ILessonDAO lessonDAO = new LessonDAO();
    @Override
    public Lesson findByName(String name) {
        Lesson gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = lessonDAO.findByName(name);
            HibernateUtil.commitTransaction();
        } catch (NonUniqueResultException ex) {
            System.out.println("Query returned more than one results.");
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void delete(Lesson gt) {
        try {
            HibernateUtil.beginTransaction();
            lessonDAO.delete(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<Lesson> getAll() {
        List<Lesson> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = lessonDAO.findAll(Lesson.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    @Override
    public Lesson findById(int id) {
        Lesson gt = null;
        try {
            HibernateUtil.beginTransaction();
            gt = lessonDAO.findByID(Lesson.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gt;
    }

    @Override
    public void save(Lesson gt) {
        try {
            HibernateUtil.beginTransaction();
            lessonDAO.save(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            //System.out.println(ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public void create(Lesson gt) {
        try {
            HibernateUtil.beginTransaction();
            lessonDAO.create(gt);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<Lesson> getDoubles(Map<String, String> params) {
        List<Lesson> gtList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            gtList = lessonDAO.getDoubles(params);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return gtList;
    }

    public static ObservableList<Lesson> getLessonObservableList() {
        LessonManager manager = new LessonManager();
        List<Lesson> list = manager.getAll();
        ObservableList<Lesson> oList = FXCollections.observableArrayList(list);

        return oList;
    }

}
