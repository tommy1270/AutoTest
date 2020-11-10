package com.tk.wework.contact;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {
    private Department department;
    String random = System.currentTimeMillis()+"";

    @BeforeEach
    void setUp() {
        if(department == null){
            department = new Department();
            //department.deleteAll();
        }
    }

    @Test
    void list() {
        department.list("").then().body("department[0].name",equalTo("天空之城有限公司"));
        //department.list("3").then().body("department[0].name",equalTo("生产部"));
        //department.list("2").then().body("department[0].id",equalTo(2));
    }

    @Test
    void create() {
        String name = "的盛世嫡妃"+random;
        department.create(name,"2").then().body("errcode",equalTo(0));
        String id = department.list("").path("department.find{it.name=='"+name+"'}.id")+"";
        department.delete(id);
        //department.create("testDept2","4").then().body("errcode",equalTo(60008));
    }

    @Test
    void createWithMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","新增的dept");
        map.put("name_en","hahaha");
        map.put("parentid",2);
        map.put("id",66);
        department.create(map).then().body("errcode",equalTo(0));
        department.delete("66");
    }

    @Test
    void update() {
        //String id = department.create("tertt3tertt","2").path("id")+"";
        String nameOle = "hahahaha"+random;
        department.create(nameOle,"3");
        String id = department.list("").path("department.find{it.name=='"+nameOle+"'}.id")+"";
        department.update(id,"testDeptNew2"+random).then().body("errcode",equalTo(0));
        department.delete(id);
    }

    @Test
    void delete() {
        String id = department.create("new1Dept","3").path("id")+"";
        department.delete(id).then().body("errcode",equalTo(0));
    }

    @Test
    void deleteAll() {
        department.deleteAll();
        assertThat(1.0,lessThan(2.0));
    }
}