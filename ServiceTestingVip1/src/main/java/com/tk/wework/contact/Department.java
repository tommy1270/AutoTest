package com.tk.wework.contact;

import com.jayway.jsonpath.JsonPath;
import com.tk.wework.Wework;
import com.tk.wework.WeworkConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class Department extends Contact {
    public Response list(String id) {
        Response response = requestSpecification
                .queryParam("id", id)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().extract().response();
        reset();
        return response;
    }

    public Response create(String name, String parentid) {
        reset();
        String body = JsonPath.parse(getClass()
                .getResourceAsStream("/data/create.json"))
                .set("$.name", name)
                .set("$.parentid", parentid).jsonString();
        return requestSpecification
                .body(body)
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then()
                .extract().response();
    }

    public Response update(String id, String name) {
        reset();
        String body = JsonPath.parse(getClass().getResourceAsStream("/data/update.json"))
                .set("$.id", id).set("$.name", name).jsonString();
        return requestSpecification
                .body(body)
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().extract().response();
    }

    public Response delete(String id) {
        reset();
        return requestSpecification
                .queryParam("id", id)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().extract().response();
    }


}
