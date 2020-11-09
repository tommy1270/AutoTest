package com.tk.wework.contact;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Department extends Contact {
    public Response list(String id) {
        reset();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        return templateFromYaml("/api/list.yaml",map);
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

    public Response create(Map<String, Object> map) {
        reset();
        String body = template("/data/update.json",map);
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

    public Response deleteAll(){
        reset();
        List<Integer> list =  list("").then().extract().path("department.id");
        System.out.println(list);
        for(Integer id:list){
            delete(String.valueOf(id));
        }
        return null;
    }


}
