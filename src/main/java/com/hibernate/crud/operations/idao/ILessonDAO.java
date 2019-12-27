package com.hibernate.crud.operations.idao;

import org.russianfeature.model.GroupDOO;
import org.russianfeature.model.Lesson;

import java.util.List;
import java.util.Map;

public interface ILessonDAO extends IGenericDAO<Lesson, Integer> {

    Lesson findByName(String name);

    List<Lesson> getDoubles(Map<String, String> params);
}
