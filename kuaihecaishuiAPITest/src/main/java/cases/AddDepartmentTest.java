package cases;

import config.TestConfig;
import model.AddDepartmentCase;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.DatabaseUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AddDepartmentTest {
    @Test(groups = "loginTrue", description = "新增部门成功用例")
    public void addDeptTrue() throws Exception {
        SqlSession session = DatabaseUtil.getSqlSession();
        AddDepartmentCase addDepartmentCase = session.selectOne("addDepartmentCase", 1);
        JSONObject resultJson = getResult(addDepartmentCase);
        int successCode = resultJson.getInt("success");
        Assert.assertEquals(addDepartmentCase.getExpected(), successCode);
        int resultParentId = resultJson.getJSONObject("dataMap").getJSONObject("department").getInt("parentId");
        Assert.assertEquals(addDepartmentCase.getParentId(), resultParentId);
        int resultLevel = resultJson.getJSONObject("dataMap").getJSONObject("department").getJSONObject("info").getInt("level");
        Assert.assertEquals(addDepartmentCase.getLevel(), resultLevel);
        String resultName = resultJson.getJSONObject("dataMap").getJSONObject("department").getJSONObject("info").getString("name");
        Assert.assertEquals(addDepartmentCase.getName(), resultName);
    }

    @Test(description = "新增部门失败用例")
    public void addDeptFalse() throws Exception {
        SqlSession session = DatabaseUtil.getSqlSession();
        AddDepartmentCase addDepartmentCase = session.selectOne("addDepartmentCase", 2);
        JSONObject resultJson = getResult(addDepartmentCase);
        int successCode = resultJson.getInt("success");
        Assert.assertEquals(addDepartmentCase.getExpected(), successCode);
        JSONObject errorJson = resultJson.getJSONObject("errorMap");
        JSONObject expectJson = new JSONObject();
        expectJson.put("error","系统异常");
        Assert.assertEquals(errorJson.toString(),expectJson.toString());

    }

    private JSONObject getResult(AddDepartmentCase addDepartmentCase) throws Exception {
        HttpPost post = new HttpPost(TestConfig.addDepartmentUrl);
        JSONObject param = new JSONObject();
        param.put("parentId",addDepartmentCase.getParentId());
        JSONObject info = new JSONObject();
        info.put("name",addDepartmentCase.getName());
        info.put("level",addDepartmentCase.getLevel());
        param.put("info",info);
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setHeader("content-type","application/json");
        post.setEntity(entity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        JSONObject resultJson = new JSONObject(result);
        return resultJson;
    }
}
