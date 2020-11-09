package com.tk.wework.contact;

import com.tk.wework.Api;
import com.tk.wework.Wework;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class Contact extends Api {
    public Contact(){
        reset();
    }

    public void reset(){
        requestSpecification = given();
        requestSpecification
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("access_token", Wework.getToken())
                .expect().log().all().statusCode(200);

        requestSpecification.filter((req,res,ctx)->{


           return ctx.next(req,res);
        });
    }




}
