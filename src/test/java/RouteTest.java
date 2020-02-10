import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * Project Name    : rest-assured-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 2/9/2020
 * Time            : 1:04 PM
 * Description     :
 **/


public class RouteTest {

    private static final String BASE_URI = "http://localhost:3000";
    private static String TASK_ID = null;

    @Test
    public void doPost() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "Task 1");
        jsonObj.put("category", "R&D");
        jsonObj.put("status", "Completed");

        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObj.toJSONString())
                .when()
                .post(BASE_URI + "/tasks")
                .then();

        System.out.println("POST Response:\n" + response.extract().body().asString());

        TASK_ID = response.extract().body().jsonPath().get("_id");
        // Tests
        Assert.assertEquals(response.extract().statusCode(), 201);
        Assert.assertEquals(response.extract().body().jsonPath().get("name"), "Task 1");
        Assert.assertEquals(response.extract().body().jsonPath().get("category"), "R&D");
        Assert.assertEquals(response.extract().body().jsonPath().get("status[0]"), "Completed");
    }

    @Test
    public void doGet() {
        doPost();

        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URI + "/tasks/" + TASK_ID)
                .then();

        System.out.println("GET Response:\n" + response.extract().body().asString());

        // Tests
        Assert.assertEquals(response.extract().statusCode(), 200);
        Assert.assertEquals(response.extract().body().jsonPath().get("name"), "Task 1");
        Assert.assertEquals(response.extract().body().jsonPath().get("category"), "R&D");
        Assert.assertEquals(response.extract().body().jsonPath().get("status[0]"), "Completed");
    }

    @Test
    public void doPut() {
        doPost();

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "Task 2");
        jsonObj.put("category", "QE");
        jsonObj.put("status", "In Progress");

        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObj.toJSONString())
                .when()
                .put(BASE_URI + "/tasks/" + TASK_ID)
                .then();

        System.out.println("PUT Response:\n" + response.extract().body().asString());

        // Tests
        Assert.assertEquals(response.extract().statusCode(), 200);
        Assert.assertEquals(response.extract().body().jsonPath().get("name"), "Task 2");
        Assert.assertEquals(response.extract().body().jsonPath().get("category"), "QE");
        Assert.assertEquals(response.extract().body().jsonPath().get("status[0]"), "In Progress");
    }

    @AfterMethod
    public void doDelete() {
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete(BASE_URI + "/tasks/" + TASK_ID)
                .then();

        System.out.println("Delete Response:\n" + response.extract().body().asString());

        // Tests
        Assert.assertEquals(response.extract().statusCode(), 204);
    }


}
