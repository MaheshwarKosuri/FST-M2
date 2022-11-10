package Activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity1 {

    String baseURI = "";
    @BeforeClass
    public void setup(){
        baseURI = "https://petstore.swagger.io/v2/pet";
    }

    @Test (priority = 0)
    public void postRequest(){

        String requestBody =
                "{"+
                    "\"id\": 77232,"+
                    "\"name\": \"Riley\","+
                    "\"status\": \"alive\"" +
                "}";
        Response response = given().contentType(ContentType.JSON).body(requestBody).when().post(baseURI);

        response.then().body("id",equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }

    @Test (priority = 1)
    public void getRequest(){
        Response response = given().contentType(ContentType.JSON)
                .when().pathParam("petId","77232").get(baseURI + "/{petId}");

        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }

    @Test (priority = 2)
    public void deleteRequest(){
        Response response = given().contentType(ContentType.JSON)
                .when().pathParam("petId","77232").delete(baseURI+"/{petId}");

        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("77232"));
    }

    @AfterClass
    public void tearDown(){

    }
}
