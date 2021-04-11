package com.crm.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data
@Table
@Entity
public class User {
    @Id
    private int id;
    private String email;
    private String password;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "created_date")
    private Timestamp createdDate;
    private String city;
    private String phone;
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    private User(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.companyName = builder.companyName;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.createdDate = builder.createdDate;
        this.city = builder.city;
        this.phone = builder.phone;
        this.role = builder.role;
    }

    public static class Builder {
        private final String email;
        private final String password;
        private final String firstName;
        private final String lastName;
        private final String city;
        private final Timestamp createdDate;

        private String phone = "-";
        private String role = "USER";
        private String companyName = "-";


        public Builder(String email, String password, String firstName,
                       String lastName, String city, Timestamp createdDate) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.createdDate = createdDate;
            this.city = city;
        }

        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }

}
