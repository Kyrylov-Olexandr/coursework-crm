package com.crm.model;

import lombok.Builder;
import lombok.Data;


import java.sql.Timestamp;

@Builder
@Data
public class User {
    private int id;
    private String email;
    private String password;
    private String companyName;
    private String firstName;
    private String lastName;
    private Timestamp createdDate;
    private String city;
    private String phone;
    private String role;

}
enum Role {
    USER,
    ADMIN
}
