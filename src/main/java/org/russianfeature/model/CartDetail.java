package org.russianfeature.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cart_detail")
@Access(AccessType.PROPERTY)
public class CartDetail implements Serializable {

    private IntegerProperty cartDetailId;
    private StringProperty productName;
    private IntegerProperty qty;
    private Cart cart;

    public CartDetail() {
        this.cartDetailId = new SimpleIntegerProperty(0);
        this.productName = new SimpleStringProperty("");
        this.qty = new SimpleIntegerProperty();

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_detail_id", unique=true, nullable=false)
    public Integer getCartDetailId() {
        return cartDetailId.get();
    }

    public void setCartDetailId(Integer cart_detail_id) {
        cartDetailId.set(cart_detail_id);
    }

    public IntegerProperty CartDetailIdProperty() {
        return cartDetailId;
    }

    @Column(name = "product_name")
    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String product_name) {
        this.productName.set(product_name);
    }

    public StringProperty ProductNameProperty() {
        return productName;
    }

    @Column(name = "qty")
    public Integer getQty() {
        return qty.get();
    }

    public void setQty(Integer qty) {
        this.qty.set(qty);
    }

    public IntegerProperty QtyProperty() {
        return qty;
    }

    @ManyToOne
    @JoinColumn(name = "cart_id")
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


}



