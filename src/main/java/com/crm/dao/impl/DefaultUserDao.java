package com.crm.dao.impl;

import com.crm.DatabaseHandler;
import com.crm.DbQueries;
import com.crm.model.User;
import com.crm.dao.UserDao;
import com.crm.tables.UserTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultUserDao implements UserDao {
    private final DatabaseHandler DB_HANDLER = new DatabaseHandler();

    @Override
    public boolean add(User user) {
        try (PreparedStatement statement = DB_HANDLER.getDbConnection().prepareStatement(DbQueries.CREATE_USER)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getCity());
            statement.setTimestamp(6, user.getCreatedDate());
            return statement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User get(int id) {
        ResultSet resSet;
        try (PreparedStatement statement = DB_HANDLER.getDbConnection().prepareStatement(DbQueries.GET_USER_BY_ID)) {
            statement.setInt(1, id);
            resSet = statement.executeQuery();
            if (resSet.next()) {
                return User.builder()
                        .email(resSet.getString(UserTable.EMAIL))
                        .password(resSet.getString(UserTable.PASS))
                        .firstName(resSet.getString(UserTable.FIRST_NAME))
                        .lastName(resSet.getString(UserTable.LAST_NAME))
                        .city(resSet.getString(UserTable.CITY))
                        .phone(resSet.getString(UserTable.PHONE))
                        .companyName(resSet.getString(UserTable.COMPANY_NAME))
                        .role(resSet.getString(UserTable.ROLE))
                        .build();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    //    @Override
//    public boolean remove(User user) {
//
//    }
//
//    @Override
//    public boolean update(User user) {
//
//    }
//

}
