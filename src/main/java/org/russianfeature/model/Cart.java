package org.russianfeature.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
@Access(AccessType.PROPERTY)
//@Transactional
public class Cart implements Serializable {

    private IntegerProperty cartId;
    private StringProperty cartName;
    private Set<CartDetail> cartDetails = new HashSet<>();

    public Cart() {
        cartName = new SimpleStringProperty("");
        cartId = new SimpleIntegerProperty(0);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", unique=true, nullable=false)
    public Integer getCartId() {
        return cartId.get();
    }

    public void setCartId(Integer id) {
        cartId.set(id);
    }

    public IntegerProperty cartIdProperty(){
        return cartId;
    }

    @Column(name = "cart_name")
    public String getCartName() {
        return cartName.get();
    }

    public void setCartName(String name) {
        cartName.set(name);
    }

    public StringProperty cartNameProperty() {
        return cartName;
    }

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<CartDetail> getCartDetails() {
        return this.cartDetails;
    }

    public void setCartDetails(Set<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

}



