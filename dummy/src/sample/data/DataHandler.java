package sample.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.MembersModel;

import java.sql.*;

public class DataHandler {

    private Connection getConnection() throws SQLException {
        String connectionString = "jdbc:sqlite:_config/devthrone.db";
        return DriverManager.getConnection(connectionString);
    }

    public Boolean authenticate(String password, String email) {
        try {
            String sql = "SELECT * FROM `members` WHERE `email`='" + email + "' AND `password`='" + password + "'";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean insert(MembersModel model) {
        try {
            String sql = "INSERT INTO `members`(`name`,`email`,`password`) VALUES('" + model.getName() + "'," +
                    "'" + model.getEmail() + "'," +
                    "'" + model.getPassword() + "')";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<MembersModel> getAllMembers() {
        ObservableList<MembersModel> modelObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM `members`";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id, name, email, password;
                id = String.valueOf(resultSet.getInt(1));
                name = resultSet.getString(2);
                email = resultSet.getString(3);
                password = resultSet.getString(4);

                MembersModel model = new MembersModel(id, name, email, password);
                modelObservableList.add(model);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelObservableList;
    }

    public MembersModel searchMemberById(String mId) {
        MembersModel model = null;
        try {
            String sql = "SELECT * FROM `members` WHERE `id`='" + mId + "'";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id, name, email, password;
                id = String.valueOf(resultSet.getInt(1));
                name = resultSet.getString(2);
                email = resultSet.getString(3);
                password = resultSet.getString(4);
                model = new MembersModel(id, name, email, password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public Boolean update(MembersModel model) {
        try {
            String sql = "UPDATE `members` SET `name`='" + model.getName() + "',`email`='" + model.getEmail() + "',`password`='" + model.getPassword() + "' WHERE `id`='" + model.getId() + "'";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean deleteAll() {
        try {
            String sql = "DELETE FROM `members`";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
            MembersModel model = new MembersModel("", "Bantos", "bantos@gmail.com", "12345");
            return insert(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean delete(String mId) {
        try {
            if (getAllMembers().size()==1) {
                return deleteAll();
            } else {
                String sql = "DELETE FROM `members` WHERE `id`='" + mId + "'";
                PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
                int result = preparedStatement.executeUpdate();
                return result > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
