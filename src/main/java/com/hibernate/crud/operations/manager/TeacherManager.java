package com.hibernate.crud.operations.manager;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.dao.TeacherDAO;
import com.hibernate.crud.operations.idao.ITeacherDAO;
import com.hibernate.crud.operations.idao.ITeacherManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.russianfeature.model.Student;
import org.russianfeature.model.Teacher;
import org.russianfeature.model.TeacherLoadInfo;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TeacherManager implements  ITeacherManager {
    private ITeacherDAO teacherDAO = new TeacherDAO();

    @Override
    public Teacher findByName(String name, String surname) {
        Teacher teacher = null;
        try {
            HibernateUtil.beginTransaction();
            teacher = teacherDAO.findByName(name, surname);
            HibernateUtil.commitTransaction();
        } catch (NonUniqueResultException ex) {
            System.out.println("Query returned more than one results.");
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return teacher;
    }

    @Override
    public void delete(Teacher teacher) {
        try {
            HibernateUtil.beginTransaction();
            teacherDAO.delete(teacher);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<Teacher> getAll() {
        List<Teacher> teacherList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            teacherList = teacherDAO.findAll(Teacher.class);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return teacherList;
    }

    @Override
    public Teacher findById(int id) {
        Teacher teacher = null;
        try {
            HibernateUtil.beginTransaction();
            teacher = teacherDAO.findByID(Teacher.class, id);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return teacher;
    }

    @Override
    public void save(Teacher teacher) {
        try {
            HibernateUtil.beginTransaction();
            teacherDAO.save(teacher);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            //System.out.println(ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public void create(Teacher teacher) {
        try {
            HibernateUtil.beginTransaction();
            teacherDAO.create(teacher);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
            HibernateUtil.rollbackTransactiom();
        }
    }

    @Override
    public List<Teacher> getDoubles(Map<String, String> params) {
        List<Teacher> teacherList = new ArrayList<>();
        try {
            HibernateUtil.beginTransaction();
            teacherList = teacherDAO.getDoubles(params);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println(ex);
        }
        return teacherList;
    }

    public ObservableList<TeacherLoadInfo> getTeacherLoadList(List<TeacherLoadInfo> teacherInfoList) {
        Session session = HibernateUtil.beginTransaction();
        // Delete all rows from temp table
        Query queryDeleteAll = session.createSQLQuery("delete from teacher_tmp");
        queryDeleteAll.executeUpdate();

        // Insert rows into temp table
        Query queryInsert = session.createSQLQuery("INSERT INTO teacher_tmp " +
                "(firstName, secondName, lastName, birthDate, startWorkDate, endWorkDate) " +
                "VALUES (:val1, :val2, :val3, :val4, :val5, :val6)");
        for (TeacherLoadInfo tInfo : teacherInfoList) {
            queryInsert.setParameter("val1", tInfo.getFirstName());
            queryInsert.setParameter("val2", tInfo.getSecondName());
            queryInsert.setParameter("val3", tInfo.getLastName());
            queryInsert.setParameter("val4", tInfo.getBirthDate());
            queryInsert.setParameter("val5", tInfo.getStartWorkDate());
            queryInsert.setParameter("val6", tInfo.getEndWorkDate());
            queryInsert.executeUpdate();
        }
        HibernateUtil.commitTransaction();

        // Find dublicates
        session = HibernateUtil.beginTransaction();
        Query queryDublicates = session.createSQLQuery(
                "SELECT\n" +
                        "\tst.firstName, st.secondName, st.lastName, st.birthDate, st.startWorkDate, st.endWorkDate,\n" +
                        "\tCASE\n" +
                        "\t\tWHEN s.firstName is not null then 1\n" +
                        "\t\telse 0\n" +
                        "\tend as isDublicate\n" +
                        "from teacher_tmp as st\n" +
                        "left join teacher as s\n" +
                        "\ton st.firstName = s.firstName\n" +
                        "\tand st.secondName = s.secondName\n" +
                        "\tand st.lastName = s.lastName\n" +
                        "\tand st.birthDate = s.birthDate\n"
                //+"where s.firstName is null"
        );

        ObservableList<TeacherLoadInfo> teacherInfoObsList = FXCollections.observableArrayList();
        List<Object[]> rows = queryDublicates.list();
        HibernateUtil.commitTransaction();
        for (Object[] row : rows) {
            TeacherLoadInfo tInfo = new TeacherLoadInfo();
            tInfo.setFirstName(row[0].toString());
            tInfo.setSecondName(row[1].toString());
            tInfo.setLastName(row[2].toString());

            DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");
            try {
                String birthDate = row[3].toString();
                Date date = sourceFormat.parse(birthDate);
                tInfo.setBirthDate(sourceFormat.format(date));
            } catch (ParseException e) {
                tInfo.setErrorText("Не верный формат даты рождения.");
                e.printStackTrace();
            }

            String startWorkDate = row[4].toString();
            if (!startWorkDate.isEmpty()) {
                try {
                    Date date = sourceFormat.parse(startWorkDate);
                    tInfo.setStartWorkDate(sourceFormat.format(date));
                } catch (ParseException e) {
                    tInfo.setErrorText("Не верный формат даты начал.");
                    e.printStackTrace();
                }
            }

            String endWorkDate = row[5].toString();
            if (!endWorkDate.isEmpty()) {
                try {
                    Date date = sourceFormat.parse(endWorkDate);
                    tInfo.setEndWorkDate(sourceFormat.format(date));
                } catch (ParseException e) {
                    tInfo.setErrorText("Не верный формат даты начала работы.");
                    e.printStackTrace();
                }
            }

            tInfo.setLoadFlag(true);
            if (row[6].toString().equals("1")) {
                tInfo.setErrorText("Найден дубль");
                tInfo.setDublicateFlag(true);
                tInfo.setLoadFlag(false);
            }

            teacherInfoObsList.add(tInfo);
        }

        return teacherInfoObsList;
    }

    public static ObservableList<Teacher> getTeacherObservableList() {
        TeacherManager manager = new TeacherManager();
        List<Teacher> list = manager.getAll();
        ObservableList<Teacher> oList = FXCollections.observableArrayList(list);

        return oList;
    }

}
