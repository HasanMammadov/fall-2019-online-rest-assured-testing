package com.automation.tests.day5;
import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class POJOPractice {

    @BeforeAll
    public static void beforeAll() {
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
    }

    @Test
    public void getUser() {
        Response response = given().
                auth().
                basic("admin", "admin").
                when().
                get("/spartans/{id}", 631).prettyPeek();
        // Get the body and map it to a Java object.
        // For JSON responses this requires that you have either Jackson or Gson
        //this is a deserialization

        //Spartan spartan =   new Spartan(631,Hasan Jan, MAale)
                              //this part coming from Json response and parsing with Gson to java
                            //deserialization
        Spartan spartan = response.as(Spartan.class);//json responce Java objecte ceviririk
        System.out.println(spartan);
//           coming from responce       from pojo clas
        assertEquals(631, spartan.getId());
        assertEquals("Hasan Jan", spartan.getName());
        assertEquals("Male", spartan.getGender());


        //deserialization: POJO <- JSON
        //serialization:   POJO -> JSON
        //both operations are done with a help of Gson.
        //RestAssured automatically calls GSon for these operations
        //any JSON object can be stored in Map object.
        Map<String, ?> spartanAsMap = response.as(Map.class);
        System.out.println(spartanAsMap);
    }


    @Test
    public void addUser() {
        Spartan spartan = new Spartan("Hasan Jan", "Male", 31231241121L);
//
//        Gson gson = new Gson();
//        String pojoAsJSON = gson.toJson(spartan);
//        System.out.println(pojoAsJSON);

        Response response = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                body(spartan).// spartan java object yaratdiq. Hasan Jan.
                // RestAssured Gson ile avtomatik onu Json a convert eliyerek pot requst eledi
                        //post request de body hissesinde ne gonderdiyimizi deyirik
                when().
                post("/spartans").prettyPeek();

        response.then().statusCode(201);//to ensure that user was created

        int usersId = response.jsonPath().getInt("data.id");

        System.out.println("Users id :: " + usersId);

        System.out.println("####DELETE USER####");

        given().
                auth().basic("admin", "admin").
                when().
                delete("/spartans/{id}", usersId).prettyPeek().
                then().
                assertThat().statusCode(204);//to ensure that user was deleted
    }


}

