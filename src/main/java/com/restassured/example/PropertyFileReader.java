package com.restassured.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Project Name    : rest-assured-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 8/11/2021
 * Time            : 12:51 PM
 * Description     :
 **/

public class PropertyFileReader {

    public static String getProperty(String propertyName) {
        String propertyValue = null;

        try (InputStream input = new FileInputStream(Constants.CONFIG_PROPERTIES_FILE_PATH)) {
            Properties prop = new Properties();
            prop.load(input);
            propertyValue = prop.getProperty(propertyName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return propertyValue;
    }
}