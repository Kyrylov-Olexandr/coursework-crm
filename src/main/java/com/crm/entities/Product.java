package com.crm.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "product")
@Data
@Entity
public class Product extends BaseEntity{
    @Id
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer amount;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

}

