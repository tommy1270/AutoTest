package com.tk.wework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.tk.wework.contact.Contact;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class Api {
    Map<String, Object> querry = new HashMap<String, Object>();

    public Api(){
        useRelaxedHTTPSValidation();
    }

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

    public Response getResponseFromHar(String path, String patten, Map<String, Object> map) {
        Restful restful = getApiFromHar(path, patten);
        restful = updateApiFromMap(restful, map);
        return getResponseFromRestful(restful);
    }

    public Response getResponseFromYaml(String path, Map<String, Object> map) {
        Restful restful = getApiFromYaml(path);
        restful = updateApiFromMap(restful, map);
        return getResponseFromRestful(restful);
    }

    public Restful getApiFromHar(String path, String patten) {
        HarReader harReader = new HarReader();
        try {
            Har har = harReader.readFromFile(new File(URLDecoder.decode(getClass().getResource(path).getPath(), "utf-8")));
            HarRequest harRequest = null;
            for (HarEntry entry : har.getLog().getEntries()) {
                if (entry.getRequest().getUrl().matches(patten)) {
                    harRequest = entry.getRequest();
                    break;
                }
            }
            if (harRequest == null) {
                throw new Exception("未匹配到");
            }
            Restful restful = new Restful();
            restful.method = harRequest.getMethod().name().toLowerCase();
            restful.url = harRequest.getUrl();
            harRequest.getQueryString().forEach(entry -> {
                restful.query.put(entry.getName(), entry.getValue());
            });
            restful.body = harRequest.getPostData().getText();
            return restful;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Restful getApiFromYaml(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(WeworkConfig.class.getResourceAsStream(path), Restful.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Restful updateApiFromMap(Restful restful, Map<String, Object> map) {
        if (map == null) {
            return restful;
        }
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
        return restful;
    }

    public Response getResponseFromRestful(Restful restful) {
        RequestSpecification requestSpecification = getDefaultSpecification();
        if (restful.query != null) {
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }
        if (restful.body != null) {
            requestSpecification.body(restful.body);
        }

        //todo:多环境支持
        String[] url = updateUrl(restful.url);

        return requestSpecification.log().all()
                .header("Host",url[0])
                .request(restful.method, url[1])
                .then().log().all().extract().response();
    }

    public String[] updateUrl(String url) {
        Map<String, String> hosts = WeworkConfig.getInstance().env.get(WeworkConfig.getInstance().current);
        String host = "";
        String urlNew = "";
        for (Map.Entry<String,String> entry : hosts.entrySet()) {
            if (url.contains(entry.getKey())) {
                host = entry.getKey();
                urlNew = url.replace(entry.getKey(), entry.getValue());

            }
        }
        return new String[]{host,urlNew};
    }

}
