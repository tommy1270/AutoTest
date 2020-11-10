package com.tk.wework;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ApiTest {

    @Test
    void templateFromYaml() {
        Api api = new Api();
        Map<String,Object> map = new HashMap<String,Object>();
        api.getResponseFromYaml("/api/list.yaml",map).then().statusCode(200);
    }

    @Test
    void getApiFromHar() {
        Api api = new Api();
        System.out.println(api.getApiFromHar("/api/demo.har.json",".*party.*").url);
        System.out.println(api.getApiFromHar("/api/demo.har.json",".*getDepartMember.*").url);
        System.out.println(api.getApiFromHar("/api/demo.har.json",".*getDepartMember1.*").url);
    }

    @Test
    void getResponseFromHar() {
        Api api = new Api();
        Map<String,Object> map = new HashMap<>();
        map.put("body","test002");
        api.getResponseFromHar("/api/demo.har.json",".*party.*",map);
    }
}