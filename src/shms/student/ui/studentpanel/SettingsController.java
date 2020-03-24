/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shms.student.ui.studentpanel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SettingsController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXPasswordField oldPasswordField;
    @FXML
    private JFXPasswordField newPasswordField;
    @FXML
    private JFXPasswordField newPasswordRetypeField;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void saveButtonAction(ActionEvent event) {
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        closeStage();
    }
    
    private void closeStage() {
        ((Stage)pane.getScene().getWindow()).close();
    }
    
}
