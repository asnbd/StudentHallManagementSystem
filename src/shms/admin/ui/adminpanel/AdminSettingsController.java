package shms.admin.ui.adminpanel;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shms.admin.alert.AlertMessage;
import shms.admin.database.DatabaseHandler;
import shms.admin.ui.LoginController;
import shms.admin.ui.Preferences;

public class AdminSettingsController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField dbHostField;
    @FXML
    private JFXTextField dbUserField;
    @FXML
    private JFXPasswordField dbPasswordField;
    @FXML
    private JFXTextField dbNameField;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXTextField fullNameField;
    @FXML
    private JFXPasswordField oldPasswordField;
    @FXML
    private JFXPasswordField newPasswordField;
    @FXML
    private JFXPasswordField newPasswordRetypeField;

    private String dbPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDbConfigurationValues();
        initAdminAccount();
    }

    @FXML
    private void saveButtonDatabaseAction(ActionEvent event) {
        String inputDbHost = dbHostField.getText();
        String inputDbUser = dbUserField.getText();
        String inputDbPassword = dbPasswordField.getText();
        String inputDbName = dbNameField.getText();

        Boolean flag = inputDbHost.isEmpty() || inputDbUser.isEmpty() || inputDbPassword.isEmpty() || inputDbName.isEmpty();
        if (flag) {
            AlertMessage.showWarning("Message", null, "Please enter all required fields");
            return;
        }

        Preferences preferences = Preferences.getPreferences();
        preferences.setFirstRun(false);
        preferences.setDbHost(inputDbHost);
        preferences.setDbUser(inputDbUser);
        preferences.setDbPassword(inputDbPassword);
        preferences.setDbName(inputDbName);

        Preferences.writePreferencesToFile(preferences);

        System.out.println("Save Success!");
        AlertMessage.showInformation("Message", null, "Configuration Saved! Please restart the application to take changes");
        //System.exit(0);
    }

    @FXML
    private void saveButtonAccountAction(ActionEvent event) {
        String inputUsername = usernameField.getText();
        String inputEmail = emailField.getText();
        String inputFullName = fullNameField.getText();
        String inputOldPassword = oldPasswordField.getText();
        String inputNewPassword = newPasswordField.getText();
        String inputNewPasswordRetype = newPasswordRetypeField.getText();

        Boolean flag = inputUsername.isEmpty() || inputEmail.isEmpty() || inputFullName.isEmpty() || inputOldPassword.isEmpty();
        if (flag) {
            AlertMessage.showWarning("Message", null, "Please enter all required fields");
            return;
        }

        String st = "UPDATE `shms_admin` SET `email` = '" + inputEmail + "', "
                + "`full_name` = '" + inputFullName + "'";

        if (!inputNewPassword.isEmpty() || !inputNewPasswordRetype.isEmpty()) {
            if (inputNewPasswordRetype.isEmpty() || inputNewPassword.isEmpty()) {
                AlertMessage.showInformation("Message", null, "Please Retype New Password");
                return;
            } else if (!inputNewPassword.equals(inputNewPasswordRetype)) {
                AlertMessage.showInformation("Message", null, "New password does not match!");
                return;
            } else {
                st += ", `password` = '" + inputNewPassword + "'";
            }
        }
        st += " WHERE `shms_admin`.`user_id` = 1";

        if (inputOldPassword.equals(dbPassword)) {
            System.out.println(st);
            DatabaseHandler handler = DatabaseHandler.getInstance();
            if (handler.execAction(st)) {
                if (!inputNewPassword.isEmpty()) {
                    dbPassword = inputNewPassword;
                }
                AlertMessage.showInformation("Message", null, "Saved");
            } else {
                AlertMessage.showError("Error", null, "Database Error Occured");
            }
        } else {
            AlertMessage.showWarning("Message", null, "Wrong old password. Please enter valid old password");
        }
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        ((Stage) pane.getScene().getWindow()).close();
    }

    private void initDbConfigurationValues() {
        Preferences preferences = Preferences.getPreferences();
        dbHostField.setText(String.valueOf(preferences.getDbHost()));
        dbUserField.setText(String.valueOf(preferences.getDbUser()));
        dbPasswordField.setText(String.valueOf(preferences.getDbPassword()));
        dbNameField.setText(String.valueOf(preferences.getDbName()));
    }

    private void initAdminAccount() {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM shms_admin";
        ResultSet rs = handler.execQuery(qu);
        try {
            if (rs.next()) {
                usernameField.setText(rs.getString("user_name"));
                emailField.setText(rs.getString("email"));
                fullNameField.setText(rs.getString("full_name"));
                dbPassword = rs.getString("password");
            } else {
                System.out.println("No Admin Account Found!");
            }
        } catch (SQLException ex) {
            AlertMessage.showError("Database Error", null, "Database Error Occured");
            System.out.println("Error in LoginController");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
