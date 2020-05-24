package com.automation.tests.rahul;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basic {

    File jsonFile = new File(System.getProperty("user.dir")+"/rahul.json");

        String BASE_URI ="https://rahulshettyacademy.com";


        @Test
        @DisplayName("post new address to map")
        public void post(){
           String response= given().log().all().queryParam("key","qaclick123")
                    .header("Content-Type","application/json").baseUri(BASE_URI).
                    body("{\n" +
                            "  \"location\": {\n" +
                            "    \"lat\": -38.383494,\n" +
                            "    \"lng\": 33.427362\n" +
                            "  },\n" +
                            "  \"accuracy\": 50,\n" +
                            "  \"name\": \"Hasan House\",\n" +
                            "  \"phone_number\": \"(+1) 347 830 0367\",\n" +
                            "  \"address\": \"29, side layout, cohen 09\",\n" +
                            "  \"types\": [\n" +
                            "    \"shoe park\",\n" +
                            "    \"shop\"\n" +
                            "  ],\n" +
                            "  \"website\": \"http://google.com\",\n" +
                            "  \"language\": \"Azerbaycan\"\n" +
                            "}").
                    when().post("maps/api/place/add/json").
                    then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
                    .header("Server","Apache/2.4.18 (Ubuntu)").extract().response()
                    .asString();
           // extract as a string edib stringde save edirik, sonra Json Path class ile,
            // sonra body den istediyimiz hisseni goture bilirik

            System.out.println("response = " + response);

            JsonPath jsonPath = new JsonPath(response);// stringi goturur, json a convert edir.
            String placeID =(jsonPath.getString("place_id"));
            System.out.println("place ID: "+placeID);



            given().log().all().queryParam("key","qaclick123").
                    header("Content-type","application/json").baseUri(BASE_URI)
                    .body("{\n" +
                            "\"place_id\":\""+placeID+"\",\n" +
                            "\"address\":\"2701 Ocean ave, Brooklyn, USA\",\n" +
                            "\"key\":\"qaclick123\"\n" +
                            "}\n").
                    when().put("/maps/api/place/update/json")
                    .then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));


               String getPlaceResponce= given().log().all().queryParam("key","qaclick123").baseUri(BASE_URI).
                       queryParam("place_id",placeID)
                        .when().get("/maps/api/place/get/json").
                        then().log().all().assertThat().statusCode(200).extract().response().asString();


                JsonPath jsonPath1 = new JsonPath(getPlaceResponce);
                String updatedAdress = jsonPath1.getString("address");
                String expectedAdress = "2701 Ocean ave, Brooklyn, USA";
                System.out.println(updatedAdress);

            Assertions.assertEquals(updatedAdress,expectedAdress);




            /*
            * {
    "status": "OK",  burda path statusdi, OK alacam
    "place_id": "e2ddecf58ca30f6827fb8f41570a1de0",// burda place id---bu uzun kod
    "scope": "APP",
    "reference": "cb5be113135786cf970fafded6863edacb5be113135786cf970fafded6863eda",
    "id": "cb5be113135786cf970fafded6863eda"
}
            *
            *
            *
            *
            * */

            // UPDATE Place
        }


}
