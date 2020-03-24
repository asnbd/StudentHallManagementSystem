package shms.admin.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import shms.admin.alert.AlertMessage;
import shms.admin.database.DatabaseHandler;
import shms.main.ui.welcome.WelcomeController;

public class HallInfoController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private JFXTextField hallNameField;
    @FXML
    private JFXComboBox<String> hallTypeCombo;
    @FXML
    private JFXTextField institutionField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXTextField contactNumberField;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    private boolean isDataExist;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hallTypeCombo.getItems().addAll("Male", "Female");
        getHallInfoData();
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        String inputHallName = hallNameField.getText();
        String inputHallType = hallTypeCombo.getSelectionModel().getSelectedItem() == null ? "" : hallTypeCombo.getSelectionModel().getSelectedItem().toString();
        String inputInstitution = institutionField.getText();
        String inputAddress = addressField.getText();
        String inputEmail = emailField.getText();
        String inputContactNumber = contactNumberField.getText();

        Boolean flag = inputHallName.isEmpty() || inputHallType.isEmpty() || inputInstitution.isEmpty() || inputAddress.isEmpty() || inputEmail.isEmpty() || inputContactNumber.isEmpty();
        if (flag) {
            AlertMessage.showError("Error", null, "Please Enter all fields");
            return;
        }

        String st = "DELETE FROM `shms_options`; INSERT INTO `shms_options` (`option_id`, `option_name`, `option_value`) VALUES "
                + "('1', 'hall_name', '" + inputHallName + "'), "
                + "('2', 'hall_type', '" + inputHallType + "'), "
                + "('3', 'hall_institution', '" + inputInstitution + "'), "
                + "('4', 'hall_address', '" + inputAddress + "'), "
                + "('5', 'hall_email', '" + inputEmail + "'), "
                + "('6', 'hall_contact_number', '" + inputContactNumber + "')";

//        if (isDataExist) {
//            System.out.println("Hall Information Exists");
//            st = "UPDATE `shms_options` SET `option_value` = 'ABBAS' WHERE `shms_options`.`option_id` = 1";
//            st = "INSERT INTO `shms_options` (`option_id`, `option_name`, `option_value`) VALUES "
//                   + "('1', 'hall_name', '" + inputHallName + "'), "
//                   + "('2', 'hall_type', '" + inputHallType + "'), "
//                   + "('3', 'hall_institution', '" + inputInstitution + "'), "
//                    + "('4', 'hall_address', '" + inputAddress + "'), "
//                    + "('5', 'hall_email', '" + inputEmail + "'), "
//                    + "('6', 'hall_contact_number', '" + inputContactNumber + "')";
//        } else {
//            st = "DELETE FROM `shms_options`; INSERT INTO `shms_options` (`option_id`, `option_name`, `option_value`) VALUES "
//                    + "('1', 'hall_name', '" + inputHallName + "'), "
//                    + "('2', 'hall_type', '" + inputHallType + "'), "
//                    + "('3', 'hall_institution', '" + inputInstitution + "'), "
//                    + "('4', 'hall_address', '" + inputAddress + "'), "
//                    + "('5', 'hall_email', '" + inputEmail + "'), "
//                    + "('6', 'hall_contact_number', '" + inputContactNumber + "')";
//        }
        System.out.println(st);
        DatabaseHandler handler = DatabaseHandler.getInstance();
        if (handler.execAction(st)) {
            AlertMessage.showInformation("Message", null, "Saved");
            closeStage();
        } else {
            AlertMessage.showError("Error", null, "Database Error Occured");
        }
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        closeStage();
    }

    private void getHallInfoData() {
        isDataExist = false;
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM shms_options";
        System.out.println(qu);
        ResultSet rs = handler.execQuery(qu);
        try {
            if (rs.next()) {
                hallNameField.setText(rs.getString("option_value"));

                if (rs.next()) {
                    hallTypeCombo.setValue(rs.getString("option_value"));
                } else {
                    return;
                }

                if (rs.next()) {
                    institutionField.setText(rs.getString("option_value"));
                } else {
                    return;
                }

                if (rs.next()) {
                    addressField.setText(rs.getString("option_value"));
                } else {
                    return;
                }

                if (rs.next()) {
                    emailField.setText(rs.getString("option_value"));
                } else {
                    return;
                }

                if (rs.next()) {
                    contactNumberField.setText(rs.getString("option_value"));
                } else {
                    return;
                }
                isDataExist = true;
            } else {
                System.out.println("No Hall Information Found!");
            }
        } catch (SQLException ex) {
            System.out.println("Error in LoginController");
            AlertMessage.showError("Database Error", null, "Database Error Occured");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void loadWindow(String location, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error Loading Window: " + title);
            Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeStage() {
        ((Stage) pane.getScene().getWindow()).close();
    }
}
