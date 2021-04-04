package main.java.com.crm.dbcolumns;

public enum UserTable {
    USER_TABLE("user"),
    ID("id"),
    EMAIL("email"),
    PASS("password"),
    COMPANY_NAME("company_name"),
    FIRST_NAME("first_name"),
    LAST_NAME("last_name"),
    CREATED_DATE("created_date"),
    ROLE("role"),
    CITY("city"),
    PHONE("phone");

    UserTable(String value) {
    }


}
