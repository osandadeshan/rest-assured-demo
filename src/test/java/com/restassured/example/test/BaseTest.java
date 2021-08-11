package com.restassured.example.test;

import com.maxsoft.testngtestresultsanalyzer.ReportListener;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import static com.restassured.example.Constants.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;

/**
 * Project Name    : rest-assured-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 8/11/2021
 * Time            : 12:47 PM
 * Description     :
 **/

@Listeners(ReportListener.class)
public class BaseTest {

    public final RequestSpecification requestSpec = RestAssured.given();

    @BeforeMethod
    public void setRequestSpec() {
        requestSpec
                .contentType(JSON)
                .accept(JSON)
                .baseUri(API_BASE_URL)
                .header(new Header(AUTHENTICATION_HEADER_NAME, AUTHENTICATION_HEADER_VALUE_PREFIX + getAuthToken()));
    }

    private String getAuthToken() {
        return given()
                .contentType(JSON)
                .accept(JSON)
                .baseUri(AUTHENTICATION_BASE_URL).log().all()
                .get(AUTHENTICATION_ENDPOINT)
                .then().log().all()
                .statusCode(SC_OK)
                .extract()
                .body()
                .jsonPath()
                .get("token");
    }
}
