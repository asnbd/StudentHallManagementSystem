package shms.admin.ui.adminpanel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import shms.admin.database.DatabaseHandler;

public class StudentListController implements Initializable {

    ObservableList<Student> list = FXCollections.observableArrayList();
    @FXML
    private BorderPane rootPane;
    @FXML
    private JFXComboBox<String> deptCombo;
    @FXML
    private JFXComboBox<String> batchCombo;
    @FXML
    private JFXTextField searchField;
    @FXML
    private TableView<Student> studentListTable;
    @FXML
    private TableColumn<Student, String> idCol;     //int
    @FXML
    private TableColumn<Student, String> nameCol;
    @FXML
    private TableColumn<Student, String> deptCol;
    @FXML
    private TableColumn<Student, String> batchCol;
    @FXML
    private TableColumn<Student, String> roomCol;
    @FXML
    private TableColumn<Student, String> mobileCol;
    @FXML
    private TableColumn<Student, String> emailCol;
    @FXML
    private TableColumn<Student, String> fathersNameCol;
    @FXML
    private TableColumn<Student, String> fathersMobileCol;
    @FXML
    private TableColumn<Student, String> mothersNameCol;
    @FXML
    private TableColumn<Student, String> mothersMobileCol;
    @FXML
    private TableColumn<Student, String> localGuardiansNameCol;
    @FXML
    private TableColumn<Student, String> localGuardiansMobileCol;
    @FXML
    private TableColumn<Student, String> permanentAddressCol;
    @FXML
    private TableColumn<Student, String> presentAddressCol;
    @FXML
    private TableColumn<Student, String> bloodGroupCol;
    @FXML
    private TableColumn<Student, String> genderCol;
    @FXML
    private TableColumn<Student, String> remarksCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboBoxes();
        initCol();
        loadData();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        deptCol.setCellValueFactory(new PropertyValueFactory<>("dept"));
        batchCol.setCellValueFactory(new PropertyValueFactory<>("batch"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        fathersNameCol.setCellValueFactory(new PropertyValueFactory<>("fathersName"));
        fathersMobileCol.setCellValueFactory(new PropertyValueFactory<>("fathersMobile"));
        mothersNameCol.setCellValueFactory(new PropertyValueFactory<>("mothersName"));
        mothersMobileCol.setCellValueFactory(new PropertyValueFactory<>("mothersMobile"));
        localGuardiansNameCol.setCellValueFactory(new PropertyValueFactory<>("localGuardiansName"));
        localGuardiansMobileCol.setCellValueFactory(new PropertyValueFactory<>("localGuardiansMobile"));
        permanentAddressCol.setCellValueFactory(new PropertyValueFactory<>("permanentAddress"));
        presentAddressCol.setCellValueFactory(new PropertyValueFactory<>("presentAddress"));
        bloodGroupCol.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        remarksCol.setCellValueFactory(new PropertyValueFactory<>("remarks"));
    }

    private void loadData() {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM SHMS_STUDENT_DATA";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("student_id");     //int
                String name = rs.getString("name");
                String dept = rs.getString("dept");
                String batch = rs.getString("batch");
                String room = rs.getString("room");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                String fathersName = rs.getString("fathers_name");
                String fathersMobile = rs.getString("fathers_mobile");
                String mothersName = rs.getString("mothers_name");
                String mothersMobile = rs.getString("mothers_mobile");
                String localGuardiansName = rs.getString("local_guardians_name");
                String localGuardiansMobile = rs.getString("local_guardians_mobile");
                String permanentAddress = rs.getString("permanent_address");
                String presentAddress = rs.getString("present_address");
                String bloodGroup = rs.getString("blood_group");
                String gender = rs.getString("gender");
                String remarks = rs.getString("remarks");

                list.add(new Student(id, name, dept, batch, room, mobile, email, fathersName, fathersMobile, mothersName, mothersMobile, localGuardiansName, localGuardiansMobile, permanentAddress, presentAddress, bloodGroup, gender, remarks));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        studentListTable.getItems().setAll(list);
    }

    private void initComboBoxes() {
        deptCombo.getItems().add("All Department");
        batchCombo.getItems().add("All Batch");

        deptCombo.setValue("All Department");
        batchCombo.setValue("All Batch");
    }

    @FXML
    private void searchButtonAction(ActionEvent event) {
        String inputDept = deptCombo.getValue();
        String inputBatch = batchCombo.getValue();
        String inputSearchText = searchField.getText();
        
        String qu1 = "SELECT * FROM SHMS_STUDENT_DATA";
        String qu2 = "SELECT * FROM SHMS_STUDENT_DATA";
            
        if (!inputSearchText.isEmpty()) {
            qu1 += " WHERE student_id LIKE '%" + inputSearchText + "%'";
            qu2 += " WHERE name LIKE '%" + inputSearchText + "%'";
        }
        
        String qu = qu1 + " union " + qu2;
        System.out.println(qu);

        DatabaseHandler handler = DatabaseHandler.getInstance();

        ResultSet rs = handler.execQuery(qu);
        
        list.clear();
        
        try {
            while (rs.next()) {
                String id = rs.getString("student_id");     //int
                String name = rs.getString("name");
                String dept = rs.getString("dept");
                String batch = rs.getString("batch");
                String room = rs.getString("room");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                String fathersName = rs.getString("fathers_name");
                String fathersMobile = rs.getString("fathers_mobile");
                String mothersName = rs.getString("mothers_name");
                String mothersMobile = rs.getString("mothers_mobile");
                String localGuardiansName = rs.getString("local_guardians_name");
                String localGuardiansMobile = rs.getString("local_guardians_mobile");
                String permanentAddress = rs.getString("permanent_address");
                String presentAddress = rs.getString("present_address");
                String bloodGroup = rs.getString("blood_group");
                String gender = rs.getString("gender");
                String remarks = rs.getString("remarks");

                list.add(new Student(id, name, dept, batch, room, mobile, email, fathersName, fathersMobile, mothersName, mothersMobile, localGuardiansName, localGuardiansMobile, permanentAddress, presentAddress, bloodGroup, gender, remarks));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        studentListTable.getItems().clear();
        studentListTable.getItems().setAll(list);
    }

    public static class Student {

        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty dept;
        private final SimpleStringProperty batch;
        private final SimpleStringProperty room;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;
        private final SimpleStringProperty fathersName;
        private final SimpleStringProperty fathersMobile;
        private final SimpleStringProperty mothersName;
        private final SimpleStringProperty mothersMobile;
        private final SimpleStringProperty localGuardiansName;
        private final SimpleStringProperty localGuardiansMobile;
        private final SimpleStringProperty permanentAddress;
        private final SimpleStringProperty presentAddress;
        private final SimpleStringProperty bloodGroup;
        private final SimpleStringProperty gender;
        private final SimpleStringProperty remarks;

        public Student(String id, String name, String dept, String batch, String room, String mobile, String email, String fathersName, String fathersMobile, String mothersName, String mothersMobile, String localGuardiansName, String localGuardiansMobile, String permanentAddress, String presentAddress, String bloodGroup, String gender, String remarks) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.dept = new SimpleStringProperty(dept);
            this.batch = new SimpleStringProperty(batch);
            this.room = new SimpleStringProperty(room);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
            this.fathersName = new SimpleStringProperty(fathersName);
            this.fathersMobile = new SimpleStringProperty(fathersMobile);
            this.mothersName = new SimpleStringProperty(mothersName);
            this.mothersMobile = new SimpleStringProperty(mothersMobile);
            this.localGuardiansName = new SimpleStringProperty(localGuardiansName);
            this.localGuardiansMobile = new SimpleStringProperty(localGuardiansMobile);
            this.permanentAddress = new SimpleStringProperty(permanentAddress);
            this.presentAddress = new SimpleStringProperty(presentAddress);
            this.bloodGroup = new SimpleStringProperty(bloodGroup);
            this.gender = new SimpleStringProperty(gender);
            this.remarks = new SimpleStringProperty(remarks);
        }

        public String getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public String getDept() {
            return dept.get();
        }

        public String getBatch() {
            return batch.get();
        }

        public String getRoom() {
            return room.get();
        }

        public String getMobile() {
            return mobile.get();
        }

        public String getEmail() {
            return email.get();
        }

        public String getFathersName() {
            return fathersName.get();
        }

        public String getFathersMobile() {
            return fathersMobile.get();
        }

        public String getMothersName() {
            return mothersName.get();
        }

        public String getMothersMobile() {
            return mothersMobile.get();
        }

        public String getLocalGuardiansName() {
            return localGuardiansName.get();
        }

        public String getLocalGuardiansMobile() {
            return localGuardiansMobile.get();
        }

        public String getPermanentAddress() {
            return permanentAddress.get();
        }

        public String getPresentAddress() {
            return presentAddress.get();
        }

        public String getBloodGroup() {
            return bloodGroup.get();
        }

        public String getGender() {
            return gender.get();
        }

        public String getRemarks() {
            return remarks.get();
        }
    }
}
