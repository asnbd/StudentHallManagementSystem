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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import shms.admin.alert.AlertMessage;
import shms.admin.database.DatabaseHandler;
import shms.main.ui.welcome.WelcomeController;

public class AdminAccountController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private JFXTextField adminUsernameField;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXTextField fullNameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXPasswordField passwordConfirmField;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        String inputAdminUsername = adminUsernameField.getText();
        String inputEmail = emailField.getText();
        String inputFullName = fullNameField.getText();
        String inputPassword = passwordField.getText();
        String inputPasswordConfirm = passwordConfirmField.getText();

        Boolean flag = inputAdminUsername.isEmpty() || inputEmail.isEmpty() || inputFullName.isEmpty() || inputPassword.isEmpty() || inputPasswordConfirm.isEmpty();
        if (flag) {
            AlertMessage.showWarning("Message", null, "Please Enter all fields");
            return;
        }

        Boolean passwordMatch = inputPassword.equals(inputPasswordConfirm);
        if (!passwordMatch) {
            AlertMessage.showError("Error", null, "Password doesn't match");
            return;
        }

        DatabaseHandler handler = DatabaseHandler.getInstance();
        String st = "INSERT INTO `shms_admin` (`user_id`, `user_name`, `email`, `full_name`, `password`, `last_login`, `last_ip`) VALUES ("
                + "'1', "
                + "'" + inputAdminUsername + "', "
                + "'" + inputEmail + "', "
                + "'" + inputFullName + "', "
                + "'" + inputPassword + "', "
                + "'CURRENT_TIME', "
                + "'CURRENT_IP')";

        System.out.println(st);
        if (handler.execAction(st)) {
            AlertMessage.showInformation("Message", null, "Saved");
            checkOptionsTable();
        } else {
            AlertMessage.showError("Error", null, "Database Error Occured");
        }
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        closeStage();
        loadWindow("/shms/admin/ui/login.fxml", "Admin Login");
    }

    private void checkOptionsTable() {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM shms_options";
        ResultSet rs = handler.execQuery(qu);
        try {
            if (rs.next()) {
                System.out.println("Hall Information: Found");
                closeStage();
            } else {
                System.out.println("Hall Information: Not Found!");
                closeStage();
                loadWindow("/shms/admin/ui/hall_info.fxml", "Hall Information");
            }
        } catch (SQLException ex) {
            AlertMessage.showError("Database Error", null, "Database Error Occured");
            System.out.println("Error in Database");
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
