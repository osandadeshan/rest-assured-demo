package com.restassured.example.test;

import com.github.javafaker.Faker;
import com.maxsoft.testngtestresultsanalyzer.TestAnalyzeReportListener;
import com.restassured.example.Constants;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
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

@Listeners(TestAnalyzeReportListener.class)
public class BaseTest {

    public final RequestSpecification requestSpec = RestAssured.given();

    @BeforeMethod
    public void setRequestSpecification() {
        requestSpec
                .contentType(JSON)
                .accept(JSON)
                .baseUri(API_BASE_URL)
                .header(new Header(AUTHENTICATION_HEADER_NAME, AUTHENTICATION_HEADER_VALUE_PREFIX + getAuthToken()));
    }

    public int createNewUserAndReturnUserId(String firstName, String lastName) {
        JSONObject userRequestJson = new JSONObject();
        userRequestJson.put("email", firstName + lastName + Constants.FAKE_EMAIL_DOMAIN);
        userRequestJson.put("name", firstName + " " + lastName);
        userRequestJson.put("gender", "male");
        userRequestJson.put("status", "active");

        return given()
                .request()
                .spec(requestSpec)
                .body(userRequestJson.toString()).log().all()
                .post(USERS_ENDPOINT)
                .then().log().all()
                .statusCode(SC_OK)
                .extract()
                .body()
                .jsonPath()
                .get("data.id");
    }

    public int createNewUserAndReturnUserId() {
        Faker faker = new Faker();
        return createNewUserAndReturnUserId(faker.name().firstName(), faker.name().lastName());
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
