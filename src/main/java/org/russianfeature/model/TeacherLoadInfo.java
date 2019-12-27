package org.russianfeature.model;

import javafx.beans.property.*;

import java.io.Serializable;

public class TeacherLoadInfo implements Serializable {

    private IntegerProperty id;
    private StringProperty firstName;
    private StringProperty secondName;
    private StringProperty lastName;
    private StringProperty comment;
    private StringProperty createDate;
    private StringProperty birthDate;
    private StringProperty startWorkDate;
    private StringProperty endWorkDate;
    private StringProperty errorText;
    private BooleanProperty dublicateFlag;
    private BooleanProperty loadFlag;

    public TeacherLoadInfo() {
        this.id = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty("");
        this.secondName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
        this.createDate = new SimpleStringProperty("");
        this.birthDate = new SimpleStringProperty("");
        this.errorText = new SimpleStringProperty("");
        this.dublicateFlag = new SimpleBooleanProperty(false);
        this.loadFlag = new SimpleBooleanProperty(false);
        this.startWorkDate = new SimpleStringProperty("");
        this.endWorkDate = new SimpleStringProperty("");
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getSecondName() {
        return secondName.get();
    }

    public StringProperty secondNameProperty() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public String getCreateDate() {
        return createDate.get();
    }

    public StringProperty createDateProperty() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate.set(createDate);
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public StringProperty birthDateProperty() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }

    public String getErrorText() {
        return errorText.get();
    }

    public StringProperty errorTextProperty() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText.set(errorText);
    }

    public boolean isDublicateFlag() {
        return dublicateFlag.get();
    }

    public BooleanProperty dublicateFlagProperty() {
        return dublicateFlag;
    }

    public void setDublicateFlag(boolean loadFlag) {
        this.dublicateFlag.set(loadFlag);
    }

    public boolean isLoadFlag() {
        return loadFlag.get();
    }

    public BooleanProperty loadFlagProperty() {
        return loadFlag;
    }

    public void setLoadFlag(boolean loadFlag) {
        this.loadFlag.set(loadFlag);
    }

    public String getStartWorkDate() {
        return startWorkDate.get();
    }

    public StringProperty startWorkDateProperty() {
        return startWorkDate;
    }

    public void setStartWorkDate(String startWorkDate) {
        this.startWorkDate.set(startWorkDate);
    }

    public String getEndWorkDate() {
        return endWorkDate.get();
    }

    public StringProperty endWorkDateProperty() {
        return endWorkDate;
    }

    public void setEndWorkDate(String endWorkDate) {
        this.endWorkDate.set(endWorkDate);
    }
}



