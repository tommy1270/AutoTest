package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
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
import java.util.List;

public class GetUserListTest {
    @Test(dependsOnGroups = "loginTrue", description = "获取用户列表接口测试")
    public void getUserList() throws Exception {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = sqlSession.selectOne("getUserListCase", 1);
        System.out.println("获取用户列表测试用例：" + getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
        //发送请求获取接口返回数据
        JSONArray resultJson = getJsonResult(getUserListCase);
        //验证
        List<User> userList = sqlSession.selectList(getUserListCase.getExpected(), getUserListCase);
        JSONArray userListJson = new JSONArray(userList);
        Assert.assertEquals(userListJson.length(), resultJson.length());
        for (int i = 0; i < userListJson.length(); i++) {
            JSONObject actualJson = (JSONObject) userListJson.get(i);
            System.out.println("期望的user:" + actualJson.toString());
            JSONObject expectJson = (JSONObject) resultJson.get(i);
            System.out.println("获取的user:" + expectJson.toString());
            Assert.assertEquals(actualJson.toString(), expectJson.toString());
        }

    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws Exception {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("userName", getUserListCase.getUserName());
        param.put("sex", getUserListCase.getSex());
        param.put("age", getUserListCase.getAge());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setHeader("content-type", "application/json");
        post.setEntity(entity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONArray jsonArray = new JSONArray(result);
        return jsonArray;
    }
}
