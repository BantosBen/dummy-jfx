package sample.controllers;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample._bin.Bin;
import sample.data.DataHandler;
import sample.models.MembersModel;

import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    @FXML
    private JFXTextField txtEmailInsert;

    @FXML
    private JFXTextField txtNameInsert;

    @FXML
    private JFXPasswordField txtPasswordInsert;

    @FXML
    private JFXButton btnSaveInsert;

    @FXML
    private JFXButton btnDeleteAll;

    @FXML
    private JFXTextField txtEmailUpdate;

    @FXML
    private JFXTextField txtNameUpdate;

    @FXML
    private JFXPasswordField txtPasswordUpdate;

    @FXML
    private TextField txtSearchUpdate;

    @FXML
    private JFXTextField txtEmailDelete;

    @FXML
    private JFXTextField txtNameDelete;

    @FXML
    private JFXPasswordField txtPasswordDelete;

    @FXML
    private TextField txtSearchDelete;

    @FXML
    private TableView<MembersModel> tblMembers;

    @FXML
    private TableColumn<String, MembersModel> colId;

    @FXML
    private TableColumn<String, MembersModel> colName;

    @FXML
    private TableColumn<String, MembersModel> colEmail;

    private String updateID, deleteID;

    @FXML
    void delete() {
        String email;
        Bin bin = new Bin(btnSaveInsert.getScene().getWindow());
        email = txtEmailDelete.getText();
        if (!(email.isEmpty())) {
            Boolean isDeleted = new DataHandler().delete(deleteID);
            if (isDeleted) {
                bin.showSuccessMessage("Member deleted");
                txtEmailDelete.setText("");
                txtNameDelete.setText("");
                txtPasswordDelete.setText("");
                txtSearchDelete.setText("");
                fillTable();
            } else {
                bin.showErrorMessage("Failed");
            }
        } else {
            new RubberBand(txtEmailDelete).play();
            new RubberBand(txtNameDelete).play();
            new RubberBand(txtPasswordDelete).play();
            new RubberBand(txtSearchDelete).play();
            bin.showErrorMessage("No data found");
        }
    }

    @FXML
    void insert() {
        String email, name, password;
        Bin bin = new Bin(btnSaveInsert.getScene().getWindow());
        email = txtEmailInsert.getText();
        name = txtNameInsert.getText();
        password = txtPasswordInsert.getText();
        if (!(email.isEmpty() || name.isEmpty() || password.isEmpty())) {
            MembersModel model = new MembersModel("", name, email, password);
            Boolean isInserted = new DataHandler().insert(model);
            if (isInserted) {
                new Swing(btnSaveInsert).play();
                bin.showSuccessMessage("New member added");
                txtEmailInsert.setText("");
                txtNameInsert.setText("");
                txtPasswordInsert.setText("");
                fillTable();
            } else {
                bin.showErrorMessage("Failed");
            }
        } else {
            new Wobble(txtEmailInsert).play();
            new Wobble(txtNameInsert).play();
            new Wobble(txtPasswordInsert).play();
            bin.showErrorMessage("All fields required");
        }
    }

    @FXML
    void update() {
        String email, name, password;
        Bin bin = new Bin(btnSaveInsert.getScene().getWindow());
        email = txtEmailUpdate.getText();
        name = txtNameUpdate.getText();
        password = txtPasswordUpdate.getText();
        if (!(email.isEmpty() || name.isEmpty() || password.isEmpty())) {
            MembersModel model = new MembersModel(updateID, name, email, password);
            Boolean isUpdated = new DataHandler().update(model);
            if (isUpdated) {
                bin.showSuccessMessage("Member updated");
                txtEmailUpdate.setText("");
                txtNameUpdate.setText("");
                txtPasswordUpdate.setText("");
                txtSearchUpdate.setText("");
                fillTable();
            } else {
                bin.showErrorMessage("Failed");
            }
        } else {
            new Shake(txtEmailUpdate).play();
            new Shake(txtNameUpdate).play();
            new Shake(txtPasswordUpdate).play();
            new Shake(txtSearchUpdate).play();
            bin.showErrorMessage("All fields required");
        }
    }

    @FXML
    void searchDelete() {
        Bin bin = new Bin(btnSaveInsert.getScene().getWindow());
        deleteID = txtSearchDelete.getText();
        if (!deleteID.isEmpty()) {
            MembersModel model = new DataHandler().searchMemberById(deleteID);
            if (model != null) {
                txtEmailDelete.setText(model.getEmail());
                txtNameDelete.setText(model.getName());
                txtPasswordDelete.setText(model.getPassword());
            } else {
                bin.showErrorMessage("No data found");
            }

        } else {
            bin.showErrorMessage("Search field required");
        }
    }

    @FXML
    void searchUpdate() {
        Bin bin = new Bin(btnSaveInsert.getScene().getWindow());
        updateID = txtSearchUpdate.getText();
        if (!updateID.isEmpty()) {
            MembersModel model = new DataHandler().searchMemberById(updateID);
            if (model != null) {
                txtEmailUpdate.setText(model.getEmail());
                txtNameUpdate.setText(model.getName());
                txtPasswordUpdate.setText(model.getPassword());
            } else {
                bin.showErrorMessage("No data found");
            }
        } else {
            bin.showErrorMessage("Search field required");
        }
    }

    @FXML
    void deleteAll() {
        Bin bin = new Bin(btnSaveInsert.getScene().getWindow());
        Boolean isDeleted = new DataHandler().deleteAll();
        if (isDeleted) {
            new Hinge(btnDeleteAll).play();
            bin.showSuccessMessage("All data deleted");
            fillTable();
        } else {
            bin.showErrorMessage("Failed");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTable();
    }


    private void fillTable() {
        ObservableList<MembersModel> modelObservableList = new DataHandler().getAllMembers();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tblMembers.setItems(modelObservableList);
        new Tada(tblMembers).play();
    }
}
