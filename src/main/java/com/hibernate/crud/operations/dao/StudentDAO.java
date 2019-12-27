package com.hibernate.crud.operations.dao;

import com.hibernate.crud.operations.HibernateUtil;
import com.hibernate.crud.operations.idao.IStudentDAO;
import org.hibernate.query.Query;
import org.russianfeature.model.Student;
import java.util.List;
import java.util.Map;

public class StudentDAO extends GenericDAO<Student, Integer> implements IStudentDAO {

    @Override
    public Student findByName(String name, String surname) {
        Student student = null;
        String sql = "select s from Student s where s.firstName = :name and s.secondName = :surname";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("name", name).setParameter("surname", surname);
        student = findOne(query);
        return student;
    }

    @Override
    public List<Student> getDoubles(Map<String, String> params) {
        String firstName = params.get("firstName");
        String secondName = params.get("secondName");
        String lastName = params.get("lastName");
        String birthDate = params.get("birthDate");
        String id = params.get("id");
        String checkId = params.get("checkId");
        Integer idParameter;
        /*if (checkId == "t")
            idParameter = Integer.valueOf(id);
        else
            idParameter = 0;*/

        String sql = "select s from Student s where s.firstName = :firstName " +
                "and s.secondName = :secondName and s.lastName = :lastName " +
                "and s.birthDate = :birthDate and s.id <> :id";

        Query query = HibernateUtil.getSession().createQuery(sql)
                .setParameter("firstName", firstName)
                .setParameter("secondName", secondName)
                .setParameter("lastName", lastName)
                .setParameter("birthDate", birthDate)
                .setParameter("id", Integer.valueOf(id));

        List<Student> studentDoubles = findMany(query);
        return studentDoubles;
    }
}
