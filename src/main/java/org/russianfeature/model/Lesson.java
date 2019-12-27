package org.russianfeature.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "lesson")
@Access(AccessType.PROPERTY)
public class Lesson implements Serializable {

    private IntegerProperty id;
    private StringProperty createDate;
    private StringProperty name;
    private StringProperty comment;
    private StringProperty lastEditDate;
    private IntegerProperty lastEditUserId;

    public Lesson() {
        this.id = new SimpleIntegerProperty();
        this.createDate = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
        this.lastEditDate = new SimpleStringProperty("");
        this.lastEditUserId = new SimpleIntegerProperty(1);
    }

    @Id
    @Column(name = "id", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    @Column(name = "name")
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String firstName) {
        this.name.set(firstName);
    }

    @Column(name = "comment")
    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    @Column(name = "createDate")
    public String getCreateDate() {
        return createDate.get();
    }

    public StringProperty createDateProperty() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate.set(createDate);
    }

    @Column(name = "lastEditDate")
    public String getLastEditDate() {
        return lastEditDate.get();
    }

    public StringProperty lastEditDateProperty() {
        return lastEditDate;
    }

    public void setLastEditDate(String birthDate) {
        this.lastEditDate.set(birthDate);
    }

    @Column(name = "lastEditUserId")
    public Integer getLastEditUserId() {
        return lastEditUserId.get();
    }

    public IntegerProperty lastEditUserIdProperty() {
        return lastEditUserId;
    }

    public void setLastEditUserId(int id) {
        this.lastEditUserId.set(id);
    }

}



