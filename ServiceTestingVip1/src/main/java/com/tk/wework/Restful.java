package com.tk.wework;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Restful {
    Map<String, Object> querry = new HashMap<String, Object>();
    public RequestSpecification requestSpecification = given();

    public Response send() {
        requestSpecification = given().log().all();
        querry.entrySet().forEach(entry -> {
            requestSpecification.queryParam(entry.getKey(), entry.getValue());
        });
        return requestSpecification.when().request("get", "baidu.com");
    }


}
