package com.automation.tests.rahul;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ComplexJsonParse {

    @Test
    public void test() {

        JsonPath jsonPath = new JsonPath(Payload.CoursePrice());
        //1. Print No of courses returned by API
        int courseCount = jsonPath.getInt("courses.size()");
        System.out.println("courseCount = " + courseCount);
        //2.Print Purchase Amount
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println("purchaseAmount = " + purchaseAmount);


        int seleniumPrice = jsonPath.getInt("courses[0].price");
        int seleniumCopy = jsonPath.getInt("courses[0].copies");
        int totalSelenium = seleniumCopy * seleniumPrice;


        int cypressPrice = jsonPath.getInt("courses[1].price");
        int cypressCopy = jsonPath.getInt("courses[1].copies");
        int totalCypress = cypressCopy * cypressPrice;


        int rpaPrice = jsonPath.getInt("courses[2].price");
        int rpaCount = jsonPath.getInt("courses[2].copies");
        int totalRpa = rpaCount * rpaPrice;

        int actualPurchaseAmount = (totalCypress + totalRpa + totalSelenium);
        Assertions.assertEquals(910, actualPurchaseAmount);
        System.out.println("actualPurchaseAmount = " + actualPurchaseAmount);
        //3. Print Title of the first course

        String firstCourseTitle = jsonPath.getString("courses[0].title");
        System.out.println("firstCourseTitle = " + firstCourseTitle);

        // 4. Print All course titles and their respective Prices

        String secondCourseTitle = jsonPath.getString("courses[1].title");
        String thirdCourseTitle = jsonPath.getString("courses[2].title");

        Map<String, Integer> coursesInfo = new LinkedHashMap<>();
        coursesInfo.put(firstCourseTitle, totalSelenium);
        coursesInfo.put(secondCourseTitle, totalCypress);
        coursesInfo.put(thirdCourseTitle, totalRpa);

        System.out.println("coursesInfo = " + coursesInfo);
//5. Print no of copies sold by RPA Course

        int numOfCopSoldRPA = jsonPath.getInt("courses[2].copies");
        System.out.println("numOfCopSoldRPA = " + numOfCopSoldRPA);
//6. Verify if Sum of all Course prices matches with Purchase Amount

    }

    @Test
    public void nameAndPrice() {
        JsonPath jsonPath1 = new JsonPath(Payload.CoursePrice());
        int courseCount = jsonPath1.getInt("courses.size()");

        for (int i = 0; i < courseCount; i++) {
            int prices = jsonPath1.getInt("courses[" + i + "].price");
            String courseTitle = jsonPath1.getString("courses[" + i + "].title");
            System.out.println(courseTitle + "::" + prices);
        }
    }

    @Test
    public void titleRpa() {
        JsonPath jsonPath1 = new JsonPath(Payload.CoursePrice());
        int courseCount = jsonPath1.getInt("courses.size()");

        for (int i = 0; i < courseCount; i++) {
            String title = jsonPath1.getString("courses[" + i + "].title");

            if (title.equalsIgnoreCase("RPA")) {

                int RPAcopies = jsonPath1.getInt("courses[" + i + "].copies");
                System.out.println("RPAcopies = " + RPAcopies);
                break;
            }
        }


    }

    @Test
    public void Sum() {
        JsonPath jsonPath1 = new JsonPath(Payload.CoursePrice());
        int courseCount = jsonPath1.getInt("courses.size()");
        int sum =0;

        for (int i = 0; i < courseCount; i++) {

            String title = jsonPath1.getString("courses[" + i + "].title");
                int copies = jsonPath1.getInt("courses[" + i + "].copies");
            int prices = jsonPath1.getInt("courses[" + i + "].price");
            int amount=copies*prices;
            sum = sum+amount;
            }

        System.out.println("sum = " + sum);
        int purchaseAmount = jsonPath1.getInt("dashboard.purchaseAmount");

        Assertions.assertEquals(sum,purchaseAmount);


        }



}