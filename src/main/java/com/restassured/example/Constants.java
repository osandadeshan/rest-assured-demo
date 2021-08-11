package com.restassured.example;

import java.io.File;

import static com.restassured.example.PropertyFileReader.getProperty;

/**
 * Project Name    : rest-assured-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 8/11/2021
 * Time            : 12:52 PM
 * Description     :
 **/

public class Constants {

    // Directories
    public static final String FILE_SEPARATOR = File.separator;
    public static final String PROJECT_DIRECTORY = System.getProperty("user.dir");
    public static final String CONFIG_PROPERTIES_FILE_PATH = PROJECT_DIRECTORY + FILE_SEPARATOR
            + "src" + FILE_SEPARATOR + "test" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "config.properties";

    // Path params
    public static final String USER_ID_PATH_PARAM_NAME = "userId";
    public static final String USER_ID_PATH_PARAM = getProperty("user_id_path_param");

    // Urls
    public static final String AUTHENTICATION_BASE_URL = getProperty("auth_base_url");
    public static final String AUTHENTICATION_ENDPOINT = getProperty("auth_endpoint");
    public static final String API_BASE_URL = getProperty("api_base_url");
    public static final String USERS_ENDPOINT = getProperty("users_endpoint");
    public static final String GET_USER_ENDPOINT = USERS_ENDPOINT + USER_ID_PATH_PARAM;

    // Headers
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_HEADER_VALUE_PREFIX = "Bearer ";

    // Fake email domain
    public static final String FAKE_EMAIL_DOMAIN = "@yop.com";
}
