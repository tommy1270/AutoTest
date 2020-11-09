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

    public RequestSpecification getDefaultSpecification() {
        return given().log().all();
    }


    public static String template(String path, Map<String, Object> map) {
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

    public Response templateFromHar(String path,String patten, Map<String, Object> map) {
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return null;
    }

    public Response templateFromYaml(String path, Map<String, Object> map) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Restful restful = mapper.readValue(WeworkConfig.class.getResourceAsStream(path), Restful.class);
            if (restful.method.equals("get")) {
                map.entrySet().forEach(entry -> {
                    restful.query.replace(entry.getKey(), entry.getValue());
                });
            } else if (restful.method.equals("post")) {
                if (map.containsKey("body")) {
                    restful.body = map.get("body").toString();
                }
                if (map.containsKey("_file")) {
                    String filePath = map.get("_file").toString();
                    map.remove("_file");
                    restful.body = template(filePath, map);
                }
            }
            RequestSpecification requestSpecification = getDefaultSpecification();
            if (restful.query != null) {
                restful.query.entrySet().forEach(entry -> {
                    requestSpecification.queryParam(entry.getKey(), entry.getValue());
                });
            }
            if (restful.body != null) {
                requestSpecification.body(restful.body);
            }
            return requestSpecification.log().all()
                    .request(restful.method, restful.url)
                    .then().log().all().extract().response();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
