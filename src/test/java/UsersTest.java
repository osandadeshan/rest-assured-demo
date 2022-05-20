import com.maxsoft.testngtestresultsanalyzer.TestAnalyzeReportListener;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

/**
 * Project Name    : rest-assured-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 2/9/2020
 * Time            : 1:04 PM
 * Description     :
 **/

@Listeners(TestAnalyzeReportListener.class)
public class UsersTest {

    private final String NAME = "Osanda Nimalarathna";
    private final String EMAIL = "osanda2@gmail.com";
    private final String GENDER = "male";
    private final String STATUS = "active";
    private String USER_ID;
    private RequestSpecification requestSpecification;

    @BeforeClass
    public void setup() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader(Constants.AUTH_HEADER_NAME, "Bearer " + Token.getToken());
        requestSpecification = requestSpecBuilder.build();
    }

    @Test()
    public void testCreateUser() {
        final JSONObject reqJson = new JSONObject();
        reqJson.put("name", NAME);
        reqJson.put("email", EMAIL);
        reqJson.put("gender", GENDER);
        reqJson.put("status", STATUS);

        ValidatableResponse response = given()
                .spec(requestSpecification)
                .body(reqJson.toString())
                .when().log().all()
                .post(Constants.USERS_ENDPOINT)
                .then().log().all()
                .statusCode(SC_OK)
                .body("data.name", equalTo(NAME))
                .body("data.email", equalTo(EMAIL))
                .body("data.gender", equalTo(GENDER))
                .body("data.status", equalTo(STATUS));

        USER_ID = response.extract().body().jsonPath().get("data.id").toString();
    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testRetrieveUser() {
        given()
                .spec(requestSpecification)
                .when().log().all()
                .get(Constants.USERS_ENDPOINT + USER_ID)
                .then().log().all()
                .statusCode(SC_OK)
                .body("data.name", equalTo(NAME))
                .body("data.email", equalTo(EMAIL))
                .body("data.gender", equalTo(GENDER))
                .body("data.status", equalTo(STATUS));
    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testUpdateUser() {
        final String name = "Eranga Nimalarathna";
        final String email = "eranga@gmail.com";
        final String gender = "male";
        final String status = "active";

        final JSONObject reqJson = new JSONObject();
        reqJson.put("name", name);
        reqJson.put("email", email);
        reqJson.put("gender", gender);
        reqJson.put("status", status);

        given()
                .spec(requestSpecification)
                .body(reqJson.toString())
                .when().log().all()
                .put(Constants.USERS_ENDPOINT + USER_ID)
                .then().log().all()
                .statusCode(SC_OK)
                .body("data.name", equalTo(name))
                .body("data.email", equalTo(email))
                .body("data.gender", equalTo(gender))
                .body("data.status", equalTo(status));
    }

    @AfterClass
    public void removeUser() {
        given()
                .spec(requestSpecification)
                .when()
                .delete(Constants.USERS_ENDPOINT + USER_ID)
                .then()
                .statusCode(SC_OK);
    }
}
