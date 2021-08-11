package com.restassured.example.test;

import org.testng.annotations.Test;

import static com.restassured.example.Constants.GET_USER_ENDPOINT;
import static com.restassured.example.Constants.USER_ID_PATH_PARAM_NAME;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

/**
 * Project Name    : rest-assured-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 8/11/2021
 * Time            : 1:20 PM
 * Description     :
 **/

public class DeleteUserTest extends BaseTest {

    @Test(description = "Verify that a user can be deleted")
    public void testUserDeletion() {
        given()
                .request()
                .spec(requestSpec)
                .pathParam(USER_ID_PATH_PARAM_NAME, "33").log().all()
                .delete(GET_USER_ENDPOINT)
                .then().log().all()
                .statusCode(SC_OK)
                .body("data.message", equalTo("Resource not found"));
    }
}
