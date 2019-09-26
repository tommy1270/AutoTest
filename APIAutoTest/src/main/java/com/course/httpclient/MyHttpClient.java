package com.course.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ResourceBundle;

public class MyHttpClient {
    private String url;

    @BeforeTest
    public void before(){
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        url = bundle.getString("url");
    }

    @Test
    public void test1() throws IOException {
        String result = "";
        HttpGet get = new HttpGet(this.url);
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(get);
        String content = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(content);

    }
}
