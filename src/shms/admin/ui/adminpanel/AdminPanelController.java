package shms.admin.ui.adminpanel;

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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminPanelController implements Initializable {

    @FXML
    private BorderPane rootPane;
    @FXML
    private MenuItem closeMenu;
    @FXML
    private MenuItem registerStudentMenu;
    @FXML
    private MenuItem addMealMenu;
    @FXML
    private MenuItem newNoticeMenu;
    @FXML
    private MenuItem recieveBillMenu;
    @FXML
    private MenuItem billSetupMenu;
    @FXML
    private MenuItem settingsMenu;
    @FXML
    private MenuItem logoutMenu;
    @FXML
    private MenuItem aboutMenu;
    @FXML
    private JFXButton registerStudentButton;
    @FXML
    private JFXButton addMealButton;
    @FXML
    private JFXButton newNoticeButton;
    @FXML
    private JFXButton recieveBillButton;
    @FXML
    private JFXButton dailyMealBillsButton;
    @FXML
    private JFXButton billSetupButton;
    @FXML
    private JFXButton settingsButton;
    @FXML
    private JFXButton logoutButton;
    @FXML
    private MenuItem dailyMealBillsMenu;
    @FXML
    private JFXButton importFromExcelButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void closeMenuAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void registerStudentButtonAction(ActionEvent event) {
        loadWindow("/shms/admin/ui/adminpanel/register_student.fxml", "Register Student");
    }

    @FXML
    private void addMealButtonAction(ActionEvent event) {
        loadWindow("/shms/admin/ui/adminpanel/meal_update.fxml", "Add Meal");
    }

    @FXML
    private void newNoticeButtonAction(ActionEvent event) {
        loadWindow("/shms/admin/ui/adminpanel/new_notice.fxml", "New Notice");
    }

    @FXML
    private void recieveBillButtonAction(ActionEvent event) {
        loadWindow("/shms/admin/ui/adminpanel/recieve_bill.fxml", "Recieve Bill");
    }

    @FXML
    private void dailyMealBillsButtonAction(ActionEvent event) {
        loadWindow("/shms/admin/ui/adminpanel/daily_bill_setup.fxml", "Daily Bill Setup");
    }

    @FXML
    private void billSetupButtonAction(ActionEvent event) {
        loadWindow("/shms/admin/ui/adminpanel/bill_setup.fxml", "Bill Setup");
    }

    @FXML
    private void settingsButtonAction(ActionEvent event) {
        loadWindow("/shms/admin/ui/adminpanel/admin_settings.fxml", "Settings");
    }

    @FXML
    private void logoutButtonAction(ActionEvent event) {
        ((Stage) rootPane.getScene().getWindow()).close();
        loadWindow("/shms/admin/ui/login.fxml", "Admin Login");
        //System.exit(0);
    }

    @FXML
    private void aboutMenuAction(ActionEvent event) {
        loadWindow("/shms/admin/ui/adminpanel/about.fxml", "About");
    }

    @FXML
    private void importFromExcelButtonAction(ActionEvent event) {
        loadWindow("/shms/admin/ui/adminpanel/import_excel.fxml", "Import From Excel");
    }

    void loadWindow(String location, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            System.out.println("Load Window: " + title);
        } catch (IOException ex) {
            System.out.println("Error Loading Window: " + title);
            Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void hallDetailsRefreshButtonAction(ActionEvent event) {

    }

    @FXML
    private void hallDetailsEditButtonAction(ActionEvent event) {
        loadWindow("/shms/admin/ui/hall_info.fxml", "Hall Information");
    }

    @FXML
    private void fullScreenMenuAction(ActionEvent event) {
        Stage stage = ((Stage) rootPane.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    private void studentListMenuAction(ActionEvent event) {
        loadWindow("/shms/admin/ui/adminpanel/student_list.fxml", "Student List");
    }

}
