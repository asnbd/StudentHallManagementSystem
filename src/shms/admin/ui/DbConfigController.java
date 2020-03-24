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

public class DbConfigController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private JFXTextField dbHostField;
    @FXML
    private JFXTextField dbUserField;
    @FXML
    private JFXPasswordField dbPasswordField;
    @FXML
    private JFXTextField dbNameField;
    @FXML
    private JFXButton nextButton;
    @FXML
    private JFXButton cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initConfigurationValues();
    }    

    @FXML
    private void saveButtonAction(ActionEvent event) {
        saveConfiguration();
        checkConfiguration();
        checkAdminTable();
    }
    

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        closeStage();
        loadWindow("/shms/admin/ui/login.fxml", "Admin Login");
    }
    
    private void initConfigurationValues() {
        Preferences preferences = Preferences.getPreferences();
        dbHostField.setText(String.valueOf(preferences.getDbHost()));
        dbUserField.setText(String.valueOf(preferences.getDbUser()));
        dbPasswordField.setText(String.valueOf(preferences.getDbPassword()));
        dbNameField.setText(String.valueOf(preferences.getDbName()));
    }
    
    private void saveConfiguration(){
        String inputDbHost = dbHostField.getText();
        String inputDbUser = dbUserField.getText();
        String inputDbPassword = dbPasswordField.getText();
        String inputDbName = dbNameField.getText();
        
        Preferences preferences = Preferences.getPreferences();
        preferences.setFirstRun(false);
        preferences.setDbHost(inputDbHost);
        preferences.setDbUser(inputDbUser);
        preferences.setDbPassword(inputDbPassword);
        preferences.setDbName(inputDbName);
        
        Preferences.writePreferencesToFile(preferences);
    }
    
    private void checkConfiguration(){
        DatabaseHandler.closeConnection();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        
        if(handler != null){
            System.out.println("Connection Success");
            AlertMessage.showInformation("Message", null, "Database Connection Success!");
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
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeStage() {
        ((Stage) pane.getScene().getWindow()).close();
    }

    private void checkAdminTable() {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM shms_admin";
        ResultSet rs = handler.execQuery(qu);
        try {
            if (rs.next()) {
                System.out.println("Admin Account: Found");
            } else {
                System.out.println("Admin Account: Not Found!");
                loadWindow("/shms/admin/ui/admin_account.fxml", "Admin Account");
            }
        } catch (SQLException ex) {
            System.out.println("Error in Database");
            AlertMessage.showError("Database Error", null, "Database Error Occured");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
