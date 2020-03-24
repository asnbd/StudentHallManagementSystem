package shms.admin.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import shms.admin.ui.LoginController;
import shms.admin.ui.Preferences;

public class DatabaseHandler {

    private static DatabaseHandler handler = null;
    private static String DBHOST;
    private static String USERNAME;
    private static String PASSWORD;
    private static String DBNAME;
    private static String CON; // = "jdbc:mysql://localhost/shmsdb?allowMultiQueries=true";
    private static Statement stmt = null;
    private static Connection conn = null;

    private DatabaseHandler() {
        databaseConfig();
        createConnection();
        setupAdminTable();
        setupOptionsTable();
        setupStudentDataTable();
    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
                handler = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
            System.out.println();
        }
        return handler;
    }

    void createConnection() {
        try {
            conn = DriverManager.getConnection(CON, USERNAME, PASSWORD);
            System.out.println("Database Connected.");
        } catch (SQLException ex) {
            System.out.println("Error Connecting Database.");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Error Connecting Database. Please check database settings or connection settings");
            alert.showAndWait();

            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setupAdminTable() {
        String TABLE_NAME = "SHMS_ADMIN";
        try {
            stmt = conn.createStatement();

            //String dbCreate = "CREATE DATABASE IF NOT EXISTS " + DBNAME;
            //stmt.executeUpdate(dbCreate);
            //System.out.println(dbCreate);
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table: " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                String sqlQ = "CREATE TABLE `" + DBNAME + "`.`" + TABLE_NAME + "` ( "
                        + "`user_id` INT(11) NOT NULL AUTO_INCREMENT , "
                        + "`user_name` VARCHAR(100) NOT NULL , "
                        + "`email` VARCHAR(100) NOT NULL , "
                        + "`full_name` VARCHAR(100) NOT NULL , "
                        + "`password` VARCHAR(30) NOT NULL , "
                        + "`last_login` VARCHAR(20) NOT NULL , "
                        + "`last_ip` VARCHAR(20) NOT NULL , "
                        + "PRIMARY KEY (`user_id`) , "
                        + "UNIQUE (`email`))";

                System.out.println(sqlQ);
                stmt.execute(sqlQ);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setupOptionsTable() {
        String TABLE_NAME = "SHMS_OPTIONS";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table: " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                String sqlQ = "CREATE TABLE `" + DBNAME + "`.`" + TABLE_NAME + "` ( "
                        + "`option_id` INT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL, "
                        + "`option_name` VARCHAR(191) UNIQUE NOT NULL, "
                        + "`option_value` LONGTEXT NOT NULL )";
                System.out.println(sqlQ);
                stmt.execute(sqlQ);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setupStudentDataTable() {
        String TABLE_NAME = "SHMS_STUDENT_DATA";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table: " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                String sqlQ = "CREATE TABLE `" + DBNAME + "`.`" + TABLE_NAME + "` ( "
                        + "`student_id` INT(11) NOT NULL PRIMARY KEY , "
                        + "`name` VARCHAR(100) NOT NULL , "
                        + "`dept` VARCHAR(100) NOT NULL , "
                        + "`batch` VARCHAR(10) NOT NULL , "
                        + "`room` VARCHAR(20) NOT NULL , "
                        + "`mobile` VARCHAR(20) NOT NULL , "
                        + "`email` VARCHAR(100) NOT NULL , "
                        + "`fathers_name` VARCHAR(100) NOT NULL , "
                        + "`fathers_mobile` VARCHAR(20) NOT NULL , "
                        + "`mothers_name` VARCHAR(100) NOT NULL , "
                        + "`mothers_mobile` VARCHAR(20) NOT NULL , "
                        + "`local_guardians_name` VARCHAR(100) NOT NULL , "
                        + "`local_guardians_mobile` VARCHAR(20) NOT NULL , "
                        + "`permanent_address` VARCHAR(200) NOT NULL , "
                        + "`present_address` VARCHAR(200) NOT NULL , "
                        + "`blood_group` VARCHAR(5) NOT NULL , "
                        + "`gender` VARCHAR(10) NOT NULL , "
                        + "`remarks` VARCHAR(200) NOT NULL)";
                System.out.println(sqlQ);
                stmt.execute(sqlQ);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setupMemberTable() {
        String TABLE_NAME = "MEMBER";
        try {
            stmt = conn.createStatement();
            //stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS demodb");
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println(TABLE_NAME + " already exists. Ready for go!");
            } else {
                stmt.execute("CREATE TABLE `" + DBNAME + "`.`" + TABLE_NAME + "` ( "
                        + "`id` VARCHAR(200) NOT NULL , "
                        + "`name` VARCHAR(200) NOT NULL , "
                        + "`mobile` VARCHAR(200) NOT NULL , "
                        + "`email` VARCHAR(100) NOT NULL , "
                        + "PRIMARY KEY (`id`)) ENGINE = InnoDB");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
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

    void databaseConfig() {
        Preferences preferences = Preferences.getPreferences();
        DBHOST = String.valueOf(preferences.getDbHost());
        USERNAME = String.valueOf(preferences.getDbUser());
        PASSWORD = String.valueOf(preferences.getDbPassword());
        DBNAME = String.valueOf(preferences.getDbName());

        CON = "jdbc:mysql://" + DBHOST + "/" + DBNAME + "?allowMultiQueries=true";
    }
}
