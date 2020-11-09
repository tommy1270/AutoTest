package com.tk.wework.contact;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    public static Member member;

    @BeforeAll
    static void setUp() {
        member = new Member();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test", "Haha"})
    void create(String name) {
        String nameNew = name + member.random;
        String random = String.valueOf(System.currentTimeMillis()).substring(5,13);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userid", nameNew);
        map.put("name", nameNew);
        map.put("email", random + "@qq.com");
        map.put("mobile","186"+ random);
        map.put("department", Arrays.asList(1, 2));
        member.create(map).then().body("errcode", equalTo(0));
    }
}