import io.restassured.RestAssured;

/**
 * Project Name    : rest-assured-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 5/19/2022
 * Time            : 12:12 PM
 * Description     :
 **/

public class Token {

    public static String getToken() {
        return RestAssured
                .get("https://intelliapi-mockserver.herokuapp.com/auth")
                .jsonPath()
                .get("token");
    }
}
