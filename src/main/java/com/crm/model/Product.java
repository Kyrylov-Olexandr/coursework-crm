package com.crm.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Product {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Measure measure;
}
enum Measure {
    PCS("шт"),
    L("л"),
    KG("кг"),
    M("м"),
    T("т"),
    M2("м2"),
    M3("м3");

    Measure(String value) {
    }
}
