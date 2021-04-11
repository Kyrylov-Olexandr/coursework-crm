package com.crm.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Table(name = "product")
@Data
@Entity
public class Product {
    @Id
    private int id;
    private String name;
    private String description;
    private BigDecimal price;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

}

