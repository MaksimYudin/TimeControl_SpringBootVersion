package org.russianfeature.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
//import javax.persistence.Access;
//import javax.persistence.AccessType;

@Entity
@Table(name = "groupType")
@Access(AccessType.PROPERTY)
public class GroupType implements Serializable {

    private IntegerProperty id;
    private StringProperty createDate;
    private StringProperty groupKindName;
    private StringProperty groupTypeName;
    private StringProperty comment;
    private StringProperty endDate;
    private StringProperty lastEditDate;
    private IntegerProperty lastEditUserId;

    private Set<GroupDOO> groups = new HashSet<>();

    public GroupType() {
        this.id = new SimpleIntegerProperty();
        this.createDate = new SimpleStringProperty("");
        this.groupKindName = new SimpleStringProperty("");
        this.groupTypeName = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
        this.endDate = new SimpleStringProperty("");
        this.lastEditDate = new SimpleStringProperty("");
        this.lastEditUserId = new SimpleIntegerProperty(1);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique=true, nullable=false)
    public Integer getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    @Column(name = "groupKindName")
    public String getGroupKindName() {
        return groupKindName.get();
    }

    public StringProperty groupKindNameProperty() {
        return groupKindName;
    }

    public void setGroupKindName(String firstName) {
        this.groupKindName.set(firstName);
    }

    @Column(name = "groupTypeName")
    public String getGroupTypeName() {
        return groupTypeName.get();
    }

    public StringProperty groupTypeNameProperty() {
        return groupTypeName;
    }

    public void setGroupTypeName(String secondName) {
        this.groupTypeName.set(secondName);
    }

    @Column(name = "endDate")
    public String getEndDate() {
        return endDate.get();
    }

    public StringProperty endDateProperty() {
        return endDate;
    }

    public void setEndDate(String lastName) {
        this.endDate.set(lastName);
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

    @OneToMany(mappedBy = "groupType", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public Set<GroupDOO> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupDOO> groups) {
        this.groups = groups;
    }
}



