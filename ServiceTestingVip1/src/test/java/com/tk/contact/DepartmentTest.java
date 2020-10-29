package com.tk.contact;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    Department department;

    @BeforeEach
    void setUp(){
        if(department == null){
            department = new Department();
        }
    }

    @Test
    void list(){

        department.list("").then().statusCode(200).body("department.name[0]",equalTo("天空之城有限公司"));
        department.list("2").then().statusCode(200).body("department.name[0]",equalTo("研发部"));

    }

    @Test
    void create() {
        department.create("销售部","1").then().statusCode(200);
    }

    @Test
    void update() {
        String nameOld = "销售部";
        department.create(nameOld,"1");
        String id = department.list("").path("department.find{it.name=='"+nameOld+"'}.id")+"";
        department.update().then().statusCode(200);
    }

    @Test
    void delete() {
        department.delete("3").then().statusCode(200);
    }
}