package com.crm.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "product")
@Data
@SecondaryTable(name = "stock", pkJoinColumns = @PrimaryKeyJoinColumn(name = "product_id"))
public class Product extends BaseEntity{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private BigDecimal price;

    private String measure;

    @Column(name = "img")
    private String imgUrl;

    @Transient
    private File imgFile = null;

    @Column(table = "stock", name = "quantity")
    private Integer quantityInStock;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + id +
                ", name=" + name +
                ", description=" + description +
                ", measure=" + measure +
                ", price=" + price +
                '}';
    }
}

