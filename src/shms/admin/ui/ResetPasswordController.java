package shms.admin.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
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

public class ResetPasswordController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXButton resetButton;
    @FXML
    private JFXButton cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void resetButtonAction(ActionEvent event) {
        String inputEmail = emailField.getText();
        
        Boolean flag = inputEmail.isEmpty();
        if (flag) {
            AlertMessage.showError("Error", null, "Please Enter Email");
            return;
        }

        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM shms_admin WHERE email = '" + inputEmail + "'";
        ResultSet rs = handler.execQuery(qu);
        try {
            if (rs.next()) {
                resetPassword(rs.getInt("user_id"));
            } else {
                System.out.println("No Account Found");
                AlertMessage.showError("Error", null, "No account associated with this email");
            }
        } catch (SQLException ex) {
            System.out.println("Error in LoginController");
            AlertMessage.showError("Database Error", null, "Database Error Occured");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        closeStage();
        loadWindow("/shms/admin/ui/login.fxml", "Admin Login");
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

    private void resetPassword(int userID) {
        String newPassword = generatePassword();
        
        String st = "UPDATE `shms_admin` SET `password` = '" + newPassword + "' WHERE `shms_admin`.`user_id` = " + userID + ";";
        
        System.out.println(st);
        DatabaseHandler handler = DatabaseHandler.getInstance();
        if (handler.execAction(st)) {
            AlertMessage.showInformation("Message", null, "Password reset success! Check your email for new password.");
            closeStage();
            loadWindow("/shms/admin/ui/login.fxml", "Admin Login");
        } else {
            AlertMessage.showError("Error", null, "Database Error Occured");
        }
        
        sendEmail(newPassword);
    }

    private String generatePassword() {
        int len = 12;
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#_=?";      //"!@#$%^&*_=+-/.?<>)";

        String values = Capital_chars + Small_chars + numbers + symbols;

        Random rndm_method = new Random();

        char[] password = new char[len];

        for (int i = 0; i < len; i++) {
            password[i] = values.charAt(rndm_method.nextInt(values.length()));
        }

        return String.valueOf(password);
    }

    private void sendEmail(String newPassword) {
        System.out.println("Send Password '" + newPassword + "' to Email.");
    }
}
