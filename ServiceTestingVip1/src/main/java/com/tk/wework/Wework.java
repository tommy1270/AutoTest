package com.tk.wework;

import static io.restassured.RestAssured.given;


public class Wework {
    private static String token;
    public static String getWeworkToken(String secret) {
        return given()
                .queryParam("corpid", WeworkConfig.getInstance().corpid)
                .queryParam("corpsecret", secret)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all()
                .statusCode(200).extract().path("access_token");
    }


    public static String getToken(){
        //todo:支持两种类型token
        if(token == null){
            token = getWeworkToken(WeworkConfig.getInstance().contactSecret);
        }
        return token;
    }
}
