package com.tk;

import io.restassured.RestAssured;

public class Wework {

    private static String token;
    public static String getWeworkToken(String secret){
        return RestAssured.given()
                .param("corpid", WeworkConfig.getInstance().corpid)
                .param("corpsecret", secret)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all().statusCode(200)
                .extract().path("access_token");
    }

    public static String getToken(){
        if(token==null){
            token = getWeworkToken(WeworkConfig.getInstance().contactSecret);
        }
        return token;
    }
}
