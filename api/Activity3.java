package Activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity3 {
    final static String baseURI = "https://petstore.swagger.io/v2/pet";

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseURI)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectBody("status", equalTo("alive"))
                .build();
    }

    @DataProvider
    public Object[][] petInfo(){
        Object[][] testData = new Object[][]{
                {77232, "Riley", "alive"},
                {77233, "Hansel", "alive"}
        };
        return testData;
    }

    @Test(priority = 1)
    public void postRequest(){
        String reqBody = "{\"id\": 77232, \"name\": \"Riley\", \"status\": \"alive\"}";
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post();

        reqBody = "{\"id\": 77233, \"name\": \"Hansel\", \"status\": \"alive\"}";
        response = given().spec(requestSpec)
                .body(reqBody)
                .when().post();

        response.then().spec(responseSpec);
    }

    @Test(dataProvider = "petInfo", priority=2)
    public void getPets(int id, String name, String status) {
        Response response = given().spec(requestSpec)
                .pathParam("petId", id)
                .when().get("/{petId}");


        System.out.println(response.asPrettyString());

        response.then()
                .spec(responseSpec)
                .body("name", equalTo(name));
    }

    @Test(dataProvider = "petInfo", priority=3)
    public void deletePets(int id, String name, String status) {
        Response response = given().spec(requestSpec)
                .pathParam("petId", id)
                .when().delete("/{petId}");

        response.then().body("code", equalTo(200));
    }
}
