package main.java.com.crm;

import main.java.com.crm.dbcolumns.UserTable;
import main.java.com.crm.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void registerUser(User user) {
        String insert = "INSERT INTO user(email,password,first_name,last_name,city,created_date) VALUE (?,?,?,?,?,?);";
        try (PreparedStatement statement = getDbConnection().prepareStatement(insert)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getCity());
            statement.setTimestamp(6, user.getCreatedDate());
            statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
