package com.tk.wework;

import java.util.HashMap;
import java.util.Map;

public class Restful {
    public String url;
    public String method;
    public Map<String,Object> headers = new HashMap<>();
    public Map<String,Object> query = new HashMap<>();
    public String body;
}
