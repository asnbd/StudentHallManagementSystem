package shms.admin.ui.adminpanel;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreferencesOld {
    public static final String CONFIG_FILE = "config.txt";
    String dbHost;
    String dbUser;
    String dbPassword;
    String dbName;
    
    public PreferencesOld(){
        dbHost = "localhost";
        dbUser = "root";
        dbPassword = "";
        dbName = "shmsdb";
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    
    public static void initConfig(){
        Writer writer = null;
        try {
            PreferencesOld preference = new PreferencesOld();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preference,writer);
        } catch (IOException ex) {
            Logger.getLogger(PreferencesOld.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(PreferencesOld.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static PreferencesOld getPreferences(){
        Gson gson = new Gson();
        PreferencesOld preferences = new PreferencesOld();
        try {
            preferences = gson.fromJson(new FileReader(CONFIG_FILE), PreferencesOld.class);
        } catch (FileNotFoundException ex) {
            initConfig();
            Logger.getLogger(PreferencesOld.class.getName()).log(Level.SEVERE, null, ex);
        }
        return preferences;
    }
}
