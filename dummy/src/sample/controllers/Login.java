package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample._bin.Bin;
import sample.data.DataHandler;

import java.io.IOException;
import java.util.Objects;

public class Login {
    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private JFXButton btnLogin;

    @FXML
    void authenticate(ActionEvent event) {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        if (!(email.isEmpty() || password.isEmpty())) {
            Boolean isAuthenticated = new DataHandler().authenticate(password, email);
            if (isAuthenticated) {
                try {
                    new Bin(btnLogin.getScene().getWindow()).showSuccessMessage("Successful");
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../fxmls/dashboard.fxml")));
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                new Bin(btnLogin.getScene().getWindow()).showErrorMessage("Invalid Details");
            }
        } else {
            new Bin(btnLogin.getScene().getWindow()).showErrorMessage("All fields required");
        }
    }
}
