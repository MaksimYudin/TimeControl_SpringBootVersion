package com.hibernate.crud.operations.idao;

import org.russianfeature.model.GroupDOO;
import org.russianfeature.model.Lesson;

import java.util.List;
import java.util.Map;

public interface ILessonManager {

    Lesson findByName(String name);

    void delete(Lesson entity);

    List<Lesson> getAll();

    Lesson findById(int id);

    void save(Lesson gt);

    void create(Lesson gt);

    List<Lesson> getDoubles(Map<String, String> params);

}
