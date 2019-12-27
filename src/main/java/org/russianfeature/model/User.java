package org.russianfeature.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Table(name = "user")
@Access(AccessType.PROPERTY)
public class User implements Serializable {

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty comment;
    private StringProperty lastEditDate;
    private StringProperty createDate;



    private Set<GroupDOO> groupDOOSet = new HashSet<GroupDOO>();

    //@OneToMany(mappedBy = "editUserId", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<GroupDOO> getGroupDOO() {
        return this.groupDOOSet;
    }


    public User() {
        this.id = new SimpleIntegerProperty();
        this.createDate = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
        this.lastEditDate = new SimpleStringProperty("");
    }

    @Id
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

    @OneToMany(mappedBy = "createUser")
    public Set<GroupDOO> getGroupDOOSet() {
        return groupDOOSet;
    }

}



