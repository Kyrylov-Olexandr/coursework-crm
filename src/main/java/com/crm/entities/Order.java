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
public class Order extends BaseEntity{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
    }

    //    @ManyToMany(cascade = CascadeType.ALL)
    //    @JoinTable(
    //            name = "order_item",
    //            joinColumns = @JoinColumn(name = "order_id"),
    //            inverseJoinColumns = @JoinColumn(name = "product_id")
    //    )
    //    private List<Product> products;

}
