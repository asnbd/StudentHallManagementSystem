package shms.student.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
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
import shms.main.ui.welcome.WelcomeController;

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
    private JFXButton signupButton;
    @FXML
    private Label forgotPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void loginButtonAction(ActionEvent event) {
        closeStage();
        loadWindow("/shms/student/ui/studentpanel/student_panel.fxml", "Student Hall Management System Student Panel");
    }

    @FXML
    private void signupButtonAction(ActionEvent event) {
        closeStage();
        loadWindow("/shms/student/ui/signup.fxml", "Student Signup");
    }

    @FXML
    private void forgotPasswordAction(MouseEvent event) {
        closeStage();
        loadWindow("/shms/student/ui/reset_password.fxml", "Reset Password");
    }
    
    void loadWindow(String location, String title)
    {
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch(IOException ex) {
            Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeStage() {
        ((Stage)pane.getScene().getWindow()).close();
    }
}
