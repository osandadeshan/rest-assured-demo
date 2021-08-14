package com.restassured.example.test;

import org.testng.annotations.BeforeMethod;
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

    private int userId;

    @BeforeMethod
    public void before() {
        userId = createNewUserAndReturnUserId();
    }

    @Test(description = "Verify that a user can be deleted")
    public void testUserDeletion() {
        given()
                .request()
                .spec(requestSpec)
                .pathParam(USER_ID_PATH_PARAM_NAME, userId).log().all()
                .delete(GET_USER_ENDPOINT)
                .then().log().all()
                .statusCode(SC_OK)
                .body("code", equalTo(204));
    }
}
