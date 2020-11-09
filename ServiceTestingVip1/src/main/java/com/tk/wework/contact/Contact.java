package com.tk.wework.contact;

import com.tk.wework.Api;
import com.tk.wework.Wework;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Contact extends Api {
    public Contact() {

    }


    @Override
    public RequestSpecification getDefaultSpecification() {

        RequestSpecification requestSpecification = super.getDefaultSpecification();
        requestSpecification.queryParam("access_token", Wework.getToken())
                .contentType(ContentType.JSON);
        requestSpecification.filter((req, res, ctx) -> {
            return ctx.next(req, res);
        });
        return requestSpecification;
    }
}
