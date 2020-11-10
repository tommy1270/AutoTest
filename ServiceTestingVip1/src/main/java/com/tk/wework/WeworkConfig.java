package com.tk.wework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class WeworkConfig {
    public String agentId = "1000002";
    public String secret = "U01in9axxzuuVGhwBxXuy6_MTIyBaJKZ_55_vcIYy2U";
    public String corpid = "ww64f10c11bcb5db89";
    public String contactSecret = "JkJaxqlWsEZlDp4yx793BZIjyu6TUuVtBhasPENxAog";
    public String current = "test";
    public Map<String, Map<String,String>> env;

    private static WeworkConfig weworkConfig;

    public static WeworkConfig getInstance() {
        if (weworkConfig == null) {
            weworkConfig = load("/conf/WeworkConfig.yaml");
            System.out.println(weworkConfig.agentId);
        }
        return weworkConfig;
    }

    public static WeworkConfig load(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(WeworkConfig.class.getResourceAsStream(path),WeworkConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
