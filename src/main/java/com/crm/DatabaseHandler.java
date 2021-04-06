package com.crm;


import com.crm.model.User;

import java.sql.*;

public class DatabaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

//    public User getUser(User user) {
//        ResultSet resSet = null;
//        try (PreparedStatement prSt = getDbConnection().prepareStatement(DbQueries.SELECT_USER.query)) {
//            prSt.setString(1, user.getEmail());
//            prSt.setString(2, user.getPassword());
//
//            resSet = prSt.executeQuery();
//
//            if (resSet != null) {
//                return true;
//            }
//        }
//    }
}
