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
@Table(name = "groupDOO")
@Access(AccessType.PROPERTY)
public class GroupDOO implements Serializable {

    private IntegerProperty id;
    private StringProperty createDate;
    private StringProperty groupName;
    private StringProperty comment;
    private StringProperty lastEditDate;
    private IntegerProperty editUserId;
    private StringProperty groupTypeName;

    private Set<Student> students = new HashSet<>();

    private GroupType groupType;

    public GroupDOO() {
        this.id = new SimpleIntegerProperty();
        this.createDate = new SimpleStringProperty("");
        this.groupName = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
        this.lastEditDate = new SimpleStringProperty("");
        this.editUserId = new SimpleIntegerProperty();
        this.groupTypeName = new SimpleStringProperty("");
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

    @Column(name = "groupName", nullable = false)
    public String getGroupName() {
        return groupName.get();
    }

    public StringProperty groupNameProperty() {
        return groupName;
    }

    public void setGroupName(String firstName) {
        this.groupName.set(firstName);
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

    @Column(name = "lastEditDate", nullable = false)
    public String getLastEditDate() {
        return lastEditDate.get();
    }

    public StringProperty lastEditDateProperty() {
        return lastEditDate;
    }

    public void setLastEditDate(String birthDate) {
        this.lastEditDate.set(birthDate);
    }


    @Column(name = "editUsetId")
    public int getEditUserId() {
        return editUserId.get();
    }

    public IntegerProperty editUserIdProperty() {
        return editUserId;
    }

    public void setEditUserId(int editUserId) {
        this.editUserId.set(editUserId);
    }

    @OneToMany(mappedBy = "groupDOO", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @ManyToOne
    @JoinColumn(name = "groupTypeId", nullable = false)
    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    @Column(name = "groupTypeName", nullable = true)
    public String getGroupTypeName() {
        return groupTypeName.get();
    }

    public void setGroupTypeName(String gtName) {
        groupTypeName.set(gtName);
    }

    public StringProperty groupTypeNameProperty() {
        return groupTypeName;
    }
}



