package com.tk.contact;

import static io.restassured.RestAssured.given;

import com.jayway.jsonpath.JsonPath;
import com.tk.Wework;
import io.restassured.response.Response;

public class Department {
    public Response list(String id){
        return given().log().all()
                .param("access_token", Wework.getToken())
                .param("id",id)
        .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
         .then().log().all()
                .extract().response();
    }


    public Response create(String name,String parentid){
        String body = JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/create.json"))
                .set("$.name",name).set("$.parenid",parentid).jsonString();
        return given().log().all()
                .queryParam("access_token",Wework.getToken())
                .body(body)
        .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
        .then().log().all().extract().response();
    }

    public Response update(){
        String body = JsonPath.parse(this.getClass().getResourceAsStream("/data/update.json")).jsonString();
        return given().log().all().queryParam("access_token",Wework.getToken()).body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().log().all().extract().response();
    }

    public Response delete(String id){
        return given().log().all().param("access_token",Wework.getToken()).param("id",id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().log().all().extract().response();
    }

}
