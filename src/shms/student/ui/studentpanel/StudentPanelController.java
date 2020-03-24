/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shms.student.ui.studentpanel;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author User
 */
public class StudentPanelController implements Initializable {

    @FXML
    private MenuItem closeMenu;
    @FXML
    private MenuItem addMealMenu;
    @FXML
    private MenuItem newComplaintMenu;
    @FXML
    private MenuItem settingsMenu;
    @FXML
    private MenuItem logoutMenu;
    @FXML
    private MenuItem aboutMenu;
    @FXML
    private JFXButton addMealButton;
    @FXML
    private JFXButton newComplaintButton;
    @FXML
    private JFXButton settingsButton;
    @FXML
    private JFXButton logoutButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void closeMenuAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void addMealButtonAction(ActionEvent event) {
        loadWindow("/shms/student/ui/studentpanel/add_meal.fxml", "Add Meal");
    }

    @FXML
    private void newComplaintButtonAction(ActionEvent event) {
        loadWindow("/shms/student/ui/studentpanel/new_complaint.fxml", "New Complaint");
    }

    @FXML
    private void settingsButtonAction(ActionEvent event) {
        loadWindow("/shms/student/ui/studentpanel/settings.fxml", "Settings");
    }

    @FXML
    private void logoutButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void aboutMenuAction(ActionEvent event) {
        loadWindow("/shms/student/ui/studentpanel/about.fxml", "About Developers");
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
            Logger.getLogger(StudentPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
