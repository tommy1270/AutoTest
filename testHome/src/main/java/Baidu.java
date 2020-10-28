import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class Baidu {
    @Test
    public void testGetHtml(){
        given().
                log().all()
                .get("http://www.baidu.com")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testMp3(){
        given().
                queryParam("wd","mp3")
                .when()
                .get("http://www.baidu.com/s?wd=mp3")
        .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void getNews(){
        useRelaxedHTTPSValidation();
        given()
                .queryParam("code","sogou")
                .header("Cookie","acw_tc=2760824a16038698914093374e3955807ee200f017c6bf0fdd3757b95f6d6a; xq_a_token=3242a6863ac15761c18a8469b89065b03bd5e164; xqat=3242a6863ac15761c18a8469b89065b03bd5e164; xq_r_token=729679220e12a2fbd19b15c94e6b7624c5ea8702; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOi0xLCJpc3MiOiJ1YyIsImV4cCI6MTYwNDgwMzAzMSwiY3RtIjoxNjAzODY5ODY2NjQwLCJjaWQiOiJkOWQwbjRBWnVwIn0.SGoJsMRiIvpACHnJH7H0npCL50MUb3teIh0kXYvUIdw6bJkS6RN7n-uDcE_9Z0KYp6NIRkkXrAlTHQt2Tve_Yha-MdsvFT_GhxKFd7IKN6J_50n7k9JnIPRKsUixJhkRRbcpmAuYRRUscGniaAMo8nG2ySLopvl8_KTDc9Y82abHnPwoz1p88y0IEKgPd17YCILrxbMRIgw7RspEDLq9VzitNrHV5RwIhD82dBLbIwPozqNEPS2kZ94rBLstqXedoSYafE0I5Y6HZlOFIBEfzOmom61PrQ91tPjfGcau3EUrfO_j4KfZNWPgDG6eDR6kxJ8YulvpcMkcw93Rz7RbmQ; u=881603869891445; Hm_lvt_1db88642e346389874251b5a1eded6e3=1603869892; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1603869892; device_id=24700f9f1986800ab4fcc880530dd0ed")
         .when()
                .get("https://www.xueqiu.com/stock/search.json")
        .then()
                .log().all()
                .statusCode(200)
                .body("stocks[0].name",equalTo("搜狗"))
                .body("stocks[0].code",equalTo("SOGO"));
    }


}
