package shms.admin.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import shms.admin.alert.AlertMessage;
import shms.admin.database.DatabaseHandler;

public class LoginController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXButton settingsButton;
    @FXML
    private Label forgotPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Preferences preferences = Preferences.getPreferences();
    }

    @FXML
    private void loginButtonAction(ActionEvent event) {
        String inputUsername = usernameField.getText();
        String inputPassword = passwordField.getText();

        Boolean flag = inputUsername.isEmpty() || inputPassword.isEmpty();
        if (flag) {
            AlertMessage.showInformation("Message", null, "Please Enter Username & Password");
            return;
        }

        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM shms_admin WHERE user_name = '" + inputUsername + "'";
        ResultSet rs = handler.execQuery(qu);
        try {
            if (rs.next()) {
                String dbPassword = rs.getString("password");
                if (dbPassword.equals(inputPassword)) {
                    System.out.println("Password Match");
                    closeStage();
                    loadWindow("/shms/admin/ui/adminpanel/admin_panel.fxml", "Student Hall Management System Admin Panel");
                } else {
                    AlertMessage.showWarning("Invalid Email or Password", null, "Please Enter Valid Email & Password");
                    System.out.println("Invalid Email or Password");
                }
            } else {
                AlertMessage.showWarning("Invalid Email or Password", null, "Please Enter Valid Email & Password");
                System.out.println("Invalid Email or Password");
            }
        } catch (SQLException ex) {
            System.out.println("Error in LoginController");
            AlertMessage.showError("Database Error", null, "Database Error Occured");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void settingsButtonAction(ActionEvent event) {
        closeStage();
        loadWindow("/shms/admin/ui/db_config.fxml", "Database Configuration");
    }

    @FXML
    private void forgotPasswordLabelAction(MouseEvent event) {
        closeStage();
        loadWindow("/shms/admin/ui/reset_password.fxml", "Reset Password");
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
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeStage() {
        ((Stage) pane.getScene().getWindow()).close();
    }

}
