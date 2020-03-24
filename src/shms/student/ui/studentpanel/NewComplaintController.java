/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shms.student.ui.studentpanel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class NewComplaintController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private JFXComboBox<?> categoryCombo;
    @FXML
    private TextArea complaintDescriptionTextArea;
    @FXML
    private JFXButton submitButton;
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
    private void submitButtonAction(ActionEvent event) {
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        closeStage();
    }
    
    private void closeStage() {
        ((Stage)pane.getScene().getWindow()).close();
    }
    
}
