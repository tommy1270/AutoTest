package com.course.cases;


import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class UpdateUserInfoTest {
    @Test(dependsOnGroups = "loginTrue", description = "更新用户信息接口测试")
    public void updateUserInfo() throws Exception {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 1);
        System.out.println("更新用户信息测试用例" + updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);
        //获取接口返回信息
        int result = getResult(updateUserInfoCase);
        Thread.sleep(2000);
        //验证
        User user = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        System.out.println("修改后的用户信息：" + user.toString());
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);

    }


    @Test(dependsOnGroups = "loginTrue", description = "删除用户信息接口测试")
    public void deleteUser() throws Exception {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 2);
        System.out.println("删除用户测试用例" + updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);
        //获取接口返回信息
        int result = getResult(updateUserInfoCase);
        Thread.sleep(2000);
        //验证
        User user = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        System.out.println("修改后的用户信息：" + user.toString());
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws Exception {
        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("userId", updateUserInfoCase.getUserId());
        param.put("userName", updateUserInfoCase.getUserName());
        param.put("sex", updateUserInfoCase.getSex());
        param.put("age", updateUserInfoCase.getAge());
        param.put("permission", updateUserInfoCase.getPermission());
        param.put("isDelete", updateUserInfoCase.getIsDelete());

        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setHeader("content-type", "application/json");
        post.setEntity(entity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
        int result = Integer.parseInt(resultStr);
        return result;
    }
}
