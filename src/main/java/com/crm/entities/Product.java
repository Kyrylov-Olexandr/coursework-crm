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


    @Override
    public String toString() {
        return "Product{" +
                "productId=" + id +
                ", name=" + name +
                ", description=" + description +
                ", price=" + price +
                '}';
    }
}

