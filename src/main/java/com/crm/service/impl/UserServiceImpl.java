package com.crm.service.impl;

import com.crm.DatabaseHandler;
import com.crm.DbQueries;
import com.crm.model.User;
import com.crm.service.UserService;
import com.crm.tables.UserTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final DatabaseHandler DB_HANDLER = new DatabaseHandler();
    @Override
    public boolean login(String email, String password) {
        ResultSet resSet;
        try (PreparedStatement statement = DB_HANDLER.getDbConnection().prepareStatement(DbQueries.LOGIN_USER)) {
            statement.setString(1, email);
            statement.setString(2, password);
            resSet = statement.executeQuery();
            if (resSet.next()) {
                return true;
//                return User.builder()
//                        .id(resSet.getInt(UserTable.ID))
//                        .email(resSet.getString(UserTable.EMAIL))
//                        .password(resSet.getString(UserTable.PASS))
//                        .firstName(resSet.getString(UserTable.FIRST_NAME))
//                        .lastName(resSet.getString(UserTable.LAST_NAME))
//                        .city(resSet.getString(UserTable.CITY))
//                        .phone(resSet.getString(UserTable.PHONE))
//                        .companyName(resSet.getString(UserTable.COMPANY_NAME))
//                        .role(resSet.getString(UserTable.ROLE))
//                        .build();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean register(User user) {
        return false;
    }
}
