package shms.student.ui.studentpanel;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddMealController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton cancelButton;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void addButtonAction(ActionEvent event) {
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        closeStage();
    }
    
    private void closeStage() {
        ((Stage)pane.getScene().getWindow()).close();
    }
    
}
