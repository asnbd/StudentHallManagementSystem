package shms.main.ui.welcome;

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import shms.admin.ui.Preferences;

public class WelcomeController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private JFXButton adminButton;
    @FXML
    private JFXButton studentButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void adminButtonAction(ActionEvent event) {
        Preferences preferences = Preferences.getPreferences();
        if (preferences.isFirstRun()) {
            System.out.println("Application First Run");
            closeStage();
            loadWindow("/shms/admin/ui/db_config.fxml", "Database Configuration");
        } else {
            closeStage();
            loadWindow("/shms/admin/ui/login.fxml", "Admin Login");
        }
    }

    @FXML
    private void studentButtonAction(ActionEvent event) {
        closeStage();
        loadWindow("/shms/student/ui/login.fxml", "Student Login");
    }

    void loadWindow(String location, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeStage() {
        ((Stage) pane.getScene().getWindow()).close();
    }

}
