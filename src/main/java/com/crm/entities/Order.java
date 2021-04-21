package com.crm.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;



@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "orders")
@Entity
@SecondaryTable(name = "order_product", pkJoinColumns = @PrimaryKeyJoinColumn(name = "order_id"))
public class Order extends BaseEntity{
    @Id
    private int id;

    @Column(name = "user_id")
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable=false, updatable=false)
    private User user;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Column(table = "order_product")
    private Integer quantity;

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

}
