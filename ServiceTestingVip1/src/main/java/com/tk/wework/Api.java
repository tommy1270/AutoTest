package com.tk.wework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.tk.wework.contact.Contact;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Api {
    Map<String, Object> querry = new HashMap<String, Object>();
    public RequestSpecification requestSpecification = given();

    public Response send() {
        requestSpecification = given().log().all();
        querry.entrySet().forEach(entry -> {
            requestSpecification.queryParam(entry.getKey(), entry.getValue());
        });
        return requestSpecification.when().request("get", "baidu.com");
    }

    public static String template(String path, Map<String, Object> map) {
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

    public Response templateFromHar(String path, Map<String, Object> map) {
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return null;
    }

    public Response templateFromYaml(String path,Map<String,Object> map){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Restful restful =  mapper.readValue(WeworkConfig.class.getResourceAsStream(path),Restful.class);
            if(restful.method.equals("get")){
                map.entrySet().forEach(entry->{
                    restful.query.replace(entry.getKey(),entry.getValue());
                });
            }
            restful.query.entrySet().forEach(entry->{
                this.requestSpecification = this.requestSpecification.queryParam(entry.getKey(),entry.getValue());
            });
            return requestSpecification.log().all().request(restful.method,restful.url).then().log().all().extract().response();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
