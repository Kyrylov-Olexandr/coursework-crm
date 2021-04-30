package com.crm.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Table
@Entity
public class User extends BaseEntity{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
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
    private Timestamp createdDate =  new Timestamp(System.currentTimeMillis());

    private String city = "";

    private String phone = "";

    private String role = "";

    @OneToMany(mappedBy = "user",  fetch = FetchType.EAGER)
    private List<Order> orders;

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", email=" + email +
                ", password=" + password +
                ", companyName=" + companyName +
                ", firstName=" + firstName +
                ", lastName=" + lastName+
                ", createdDate=" + createdDate.toString() +
                ", city=" + city+
                ", phone=" + phone +
                ", role=" + role +
                '}';
    }
}
