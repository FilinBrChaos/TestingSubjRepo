package example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredDemoTest {

    private static final String URL_1 = "https://fakerestapi.azurewebsites.net/api/v1";
    private static final String URL_2 = "https://restful-booker.herokuapp.com";

    @Test
    public void createTestObj() {
        String jsonString = "{\"id\": 0, \"title\": \"string\", \"dueDate\": \"2024-03-04T19:34:05.119Z\", \"completed\": true}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonString)
                .baseUri(URL_1)
                .when()
                .post("/Activities");

        response.then()
                .statusCode(200);
    }

    @Test
    public void createTestToken() {
        given()
                .baseUri(URL_2)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth")
                .then()
                .statusCode(200);
    }

    @Test
    public void getFilteredTestData() {
        Response createdObj = given()
                .baseUri(URL_2)
                .contentType(ContentType.JSON)
                .body("{\"firstname\": \"CustomText\", \"lastname\": \"Brown\", \"totalprice\": 111, \"depositpaid\": true, \"bookingdates\": {\"checkin\": \"2018-01-01\", \"checkout\": \"2019-01-01\"}, \"additionalneeds\": \"Breakfast\"}")
                .when()
                .post("/booking");
        int index = createdObj.then().statusCode(200).extract().path("bookingid");

        Response foundIndex = given()
                .baseUri(URL_2)
                .contentType(ContentType.JSON)
                .params("firstname", "CustomText")
                .when()
                .get("/booking");

        foundIndex.then().statusCode(200)
                .body("[0].bookingid", equalTo(index));
    }

    @Test
    public void checkAuthorizationFail() {
        given()
                .baseUri("https://airportgap.com/api")
                .contentType(ContentType.JSON)
                .when()
                .get("/favorites")
                .then()
                .statusCode(401);
    }

    @Test
    public void checkBasicMethods() {
        given()
                .baseUri(URL_1)
                .contentType(ContentType.JSON)
                .when()
                .get("/Activities")
                .then()
                .statusCode(200);

        given()
                .baseUri(URL_1)
                .contentType(ContentType.JSON)
                .body("{\"id\": 0, \"title\": \"string\", \"dueDate\": \"2024-03-04T19:34:05.119Z\", \"completed\": true}")
                .when()
                .post("/Activities")
                .then()
                .statusCode(200);

        given()
                .baseUri(URL_1)
                .contentType(ContentType.JSON)
                .body("{\"id\": 1, \"title\": \"string\", \"dueDate\": \"2024-03-04T19:34:05.119Z\", \"completed\": true}")
                .when()
                .put("/Activities/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));

        given()
                .baseUri(URL_1)
                .contentType(ContentType.JSON)
                .when()
                .delete("/Activities/1")
                .then()
                .statusCode(200);
    }
}
