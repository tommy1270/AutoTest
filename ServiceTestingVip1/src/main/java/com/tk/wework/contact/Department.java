package com.tk.wework.contact;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Department extends Contact {
    public Response list(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        //从yaml文件获取请求参数并请求获取响应
        return getResponseFromYaml("/api/list.yaml", map);
    }

    public Response create(String name, String parentid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("parentid",parentid);
        map.put("_file","/data/create.json");
        return getResponseFromYaml("/api/create.yaml",map);
    }

    public Response create(Map<String, Object> map) {
        map.put("_file","/data/create.json");
        return getResponseFromYaml("/api/create.yaml",map);
    }

    public Response update(String id, String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("name",name);
        map.put("_file","/data/update.json");
        return getResponseFromYaml("/api/update.yaml",map);
    }

    public Response update(Map<String,Object> map){
        //todo

        return getResponseFromHar("/data/har.json","",map);
    }

    public Response delete(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        return getResponseFromYaml("/api/delete.yaml",map);
    }

    public Response deleteAll() {
        List<Integer> list = list("").then().extract().path("department.id");
        System.out.println(list);
        for (Integer id : list) {
            delete(String.valueOf(id));
        }
        return null;
    }


}
