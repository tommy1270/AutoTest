package cases;

import config.TestConfig;
import model.InterfaceName;
import model.LoginCase;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigFile;
import utils.DatabaseUtil;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LoginTest {
    @BeforeTest(description = "测试准备，加载配置数据")
    public void before() {
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.addDepartmentUrl = ConfigFile.getUrl(InterfaceName.ADDDEPARTMENT);

        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }

    @Test(groups = "loginTrue", description = "登录成功用例")
    public void loginTrue() throws Exception {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 1);
        System.out.println(loginCase.toString());
        int successNo = getResult(loginCase);

        Assert.assertEquals(loginCase.getExpected(), successNo);
    }

    @Test(description = "登录失败用例")
    public void loginFalse() throws Exception {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 2);
        System.out.println(loginCase.toString());
        int successNo = getResult(loginCase);

        Assert.assertEquals(loginCase.getExpected(), successNo);
    }

    private int getResult(LoginCase loginCase) throws Exception {
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject param = new JSONObject();
        param.put("username", loginCase.getUsername());
        param.put("password", loginCase.getPassword());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        post.setHeader("content-type", "application/json");

        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");

        JSONObject resultJson = new JSONObject(result);
        int code = (int) resultJson.get("success");

        System.out.println(code);
        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
        return code;
    }
}
