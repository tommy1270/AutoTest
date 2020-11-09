package com.tk.wework.contact;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;


import java.util.Map;

import static io.restassured.RestAssured.given;

public class Member extends Contact {
    String random = System.currentTimeMillis() + "";

    public Response create(Map<String, Object> map) {
        String body = template("/data/member.json", map);
        return getDefaultSpecification().body(body).post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().extract().response();
    }

    public Response update(Map<String, Object> map) {
        String body = template("data/updateMember.json",map);
        return getDefaultSpecification().body(body).post("https://qyapi.weixin.qq.com/cgi-bin/user/update")
                .then().extract().response();
    }

}
