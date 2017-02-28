/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saigonbpo
 */
public class Configuration {

    private static final Configuration instance;
    private static final Properties prop;

    private Configuration() {
    }

    static {
        instance = new Configuration();
        prop = new Properties();

        try {            
            prop.load(new FileInputStream("Configuration" + File.separator + "Configuration.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Configuration getInstance() {
        return instance;
    }
    
    public Properties getProperty(){
        return prop;
    }

    public String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }
}
