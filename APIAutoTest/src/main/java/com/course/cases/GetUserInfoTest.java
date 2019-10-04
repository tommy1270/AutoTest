package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GetUserInfoTest {
    @Test(dependsOnGroups = "loginTrue", description = "获取用户信息接口测试")
    public void getUserInfo() throws Exception {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase", 1);
        System.out.println("获取用户信息测试用例" + getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);
        //发送请求获取数据
        JSONArray resultJson = getJsonResult(getUserInfoCase);
        //验证
        User user = session.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        JSONArray userJson = new JSONArray(userList);
        System.out.println(userJson.toString());
        System.out.println(resultJson.toString());
        System.out.println(userJson.get(0));
        System.out.println(resultJson.get(0));
        Assert.assertEquals(userJson.get(0).toString(),resultJson.get(0).toString());
    }

    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws Exception {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("userId",getUserInfoCase.getUserId());
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        post.setHeader("content-type","application/json");
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        return jsonArray;
    }
}
