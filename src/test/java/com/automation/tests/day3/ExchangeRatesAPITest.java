package com.automation.tests.day3;


import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;



import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class ExchangeRatesAPITest {

    @BeforeAll
    public static void setup(){
        baseURI="http://api.openrates.io";//resource path
        // get latest currency

    }
    @Test
    public void getLatestRates(){
        Response response = get("/latest").prettyPeek();
        response.then().assertThat().statusCode(200);
        //given() - all input details
        //when() - submit the API
        //then() - validate the response

//        / is path parameter
//       ? - sorting/filter - it is query parameter
    }

    @Test
    public void getLatestRatesUSD(){
        // after ? we specify query parameters. If there are couple of them we use & to concatenate them
        //http://www.google.com/index.html?q=apple&zip=123123
        //q - query parameter
        //zip - another query parameter
        //with rest assured, we provide query parameters into given() part.
        //give() - request preparation
        //you can specify query parameters in URL explicitly: http://api.openrates.io/latest?base=USD
        //rest assured, will just assemble URL for you
        Response response = given().
                queryParam("base", "USD").
                when().
                get("/latest").prettyPeek();
        //verify that GET request to the endpoint was successful
        Headers headers = response.getHeaders();
        String contentType = headers.getValue("Content-Type");
        System.out.println("contentType = " + contentType);
        response.then().statusCode(200);
        response.then().assertThat().body("base", is("USD"));
        //let's verify that response contains today's date

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        response.then().assertThat().body("date", containsString(date));
        //is - same as equals
    }



@Test
    public void getHistoryRates(){
    Response response = given().
            queryParam("base", "USD").
            when().
            get("/2008-01-02").prettyPeek();
    Headers headers = response.getHeaders();//response header
    response.then().assertThat().
            statusCode(200).
            and().
            body("date", is("2008-01-02")).
            and().
            body("rates.USD", is(1.0f));
    //and() doesn't have a functional role, it's just a syntax sugar
    //we can chain validations
    //how we can retrieve
    //rates - it's an object
    //all currencies are like instance variables
    //to get any instance variable (property), objectName.propertyName
    float actual = response.jsonPath().get("rates.USD");
    assertEquals(1.0, actual);
    }
}
