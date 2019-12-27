package org.russianfeature.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.*;
import java.io.Serializable;
//import javax.persistence.Access;
//import javax.persistence.AccessType;

@Entity
@Table(name = "student")
@Access(AccessType.PROPERTY)
public class Student implements Serializable {

    private IntegerProperty id;
    private StringProperty firstName;
    private StringProperty secondName;
    private StringProperty lastName;
    private StringProperty comment;
    private StringProperty createDate;
    private StringProperty birthDate;
    private StringProperty lastEditDate;

    private GroupDOO groupDOO;

    private StringProperty groupName4Visual;

    public Student() {
        this.id = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty("");
        this.secondName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
        this.createDate = new SimpleStringProperty("");
        this.birthDate = new SimpleStringProperty("");
        this.lastEditDate = new SimpleStringProperty("");
        this.groupName4Visual = new SimpleStringProperty("");
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
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

    @Column(name = "firstName", nullable = false)
    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    @Column(name = "secondName", nullable = false)
    public String getSecondName() {
        return secondName.get();
    }

    public StringProperty secondNameProperty() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    @Column(name = "lastName", nullable = false)
    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
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

    @Column(name = "createDate", nullable = false)
    public String getCreateDate() {
        return createDate.get();
    }

    public StringProperty createDateProperty() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate.set(createDate);
    }

    @Column(name = "birthDate", nullable = false)
    public String getBirthDate() {
        return birthDate.get();
    }

    public StringProperty birthDateProperty() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }

    @ManyToOne
    @JoinColumn(name = "groupId", nullable = false)
    public GroupDOO getGroupDOO() {
        return groupDOO;
    }

    public void setGroupDOO(GroupDOO groupDOO) {
        this.groupDOO = groupDOO;
    }

    @Column(name = "lastEditDate", nullable = false)
    public String getLastEditDate() {
        return lastEditDate.get();
    }

    public StringProperty lastEditDateProperty() {
        return lastEditDate;
    }

    public void setLastEditDate(String lastEditDate) {
        this.lastEditDate.set(lastEditDate);
    }

    @Column(name = "groupName4Visual", nullable = true)
    public String getGroupName4Visual() {
        return groupName4Visual.get();
    }

    public StringProperty groupName4VisualProperty() {
        return groupName4Visual;
    }

    public void setGroupName4Visual(String groupName) {
        this.groupName4Visual.set(groupName);
    }
}



