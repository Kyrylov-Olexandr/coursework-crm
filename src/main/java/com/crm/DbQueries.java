package com.crm;

import com.crm.tables.UserTable;

public class DbQueries {
    public static final String CREATE_USER =
            "INSERT INTO " + UserTable.TABLE + "(" + UserTable.EMAIL + "," +
            UserTable.PASS + "," + UserTable.FIRST_NAME + "," + UserTable.LAST_NAME + "," +
            UserTable.CITY + "," + UserTable.CREATED_DATE + ") VALUE (?,?,?,?,?,?)";
    public static final String LOGIN_USER =
            "SELECT * FROM " + UserTable.TABLE + " WHERE " +
            UserTable.EMAIL + " = ? AND " + UserTable.PASS + " = ?;";
    public static final String GET_USER_BY_ID =
            "SELECT * FROM " + UserTable.TABLE + " WHERE " + UserTable.ID + " = ?;";
    public static final String GET_USER_BY_EMAIL =
            "SELECT * FROM " + UserTable.TABLE + " WHERE " + UserTable.EMAIL + " = ?;";

}
