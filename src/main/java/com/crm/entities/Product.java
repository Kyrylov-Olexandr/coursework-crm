package com.crm.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "product")
@Data
public class Product extends BaseEntity{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

//    @Transient
//    private int quantity;

//    private List<Order> orders;

//    @ManyToOne
//    @JoinColumn(name="id")
//    private OrderItem orderItem;


}

