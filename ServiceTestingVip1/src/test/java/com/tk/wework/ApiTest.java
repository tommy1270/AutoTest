package com.tk.wework;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApiTest {

    @Test
    void templateFromYaml() {
        Api api = new Api();
        Map<String,Object> map = new HashMap<String,Object>();
        api.templateFromYaml("/api/list.yaml",map).then().statusCode(200);
    }
}