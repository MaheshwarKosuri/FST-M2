package Activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {

    final static String baseURI = "https://petstore.swagger.io/v2/user";

    @Test(priority = 1)
    public void postRequest() throws IOException {
        FileInputStream file = new FileInputStream("src/test/java/Activities/Activity2.json");
        String reqBody = new String(file.readAllBytes());

        Response response = given().contentType(ContentType.JSON).body(reqBody).when().post(baseURI);

        file.close();

        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("3696"));
    }

    @Test(priority = 2)
    public void getRequest(){
        File outputJSON = new File("src/test/java/Activities/userGETResponse.json");

        Response response = given().contentType(ContentType.JSON).pathParam("username", "mahmad")
                .when().get(baseURI+"/{username}");

        String resBody = response.getBody().asPrettyString();

        try{
            outputJSON.createNewFile();

            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        response.then().body("id", equalTo(3696));
        response.then().body("username", equalTo("mahmad"));
        response.then().body("firstName", equalTo("mah"));
        response.then().body("lastName", equalTo("mad"));
        response.then().body("email", equalTo("justincase@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812763450"));
    }

    @Test(priority = 3)
    public void deleteRequest(){
        Response response =
                given().contentType(ContentType.JSON)
                        .pathParam("username", "mahmad").when().delete(baseURI + "/{username}");

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("mahmad"));
    }
}
