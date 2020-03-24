/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shms.admin.ui.adminpanel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ImportExcelController implements Initializable {

    @FXML
    private JFXTextField selectFileField;
    @FXML
    private JFXButton browseButton;
    @FXML
    private JFXButton importButton;
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
    private void selectFileFieldAction(ActionEvent event) {
    }

    @FXML
    private void browseButtonAction(ActionEvent event) {
    }

    @FXML
    private void importButtonAction(ActionEvent event) {
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
    }
    
}
