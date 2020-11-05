package com.tk.wework.contact;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    public static Member member;
    @BeforeAll
    void setUp() {
        member = new Member();
    }

    @Test
    void create() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userid",member.random);
        map.put("name","TestName"+member.random);
        map.put("department",[5]);
        map.put("email",member.random+"@qq.com");
        member.create(map).then().body("errcode",equalTo(0));
    }
}