package com.tk.wework.contact;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;


import java.util.Map;

import static io.restassured.RestAssured.given;

public class Member extends Contact{
    String random = System.currentTimeMillis()+"";
    public Response create(Map<String,Object> map){
        reset();
        String body = template("/data/member.json",map);
        return requestSpecification.body(body).post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().extract().response();
    }
}
