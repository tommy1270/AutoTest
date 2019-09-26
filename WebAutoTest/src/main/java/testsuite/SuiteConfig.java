package testsuite;

import config.ActionsKeywords;
import config.Constants;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.BaseTools;
import utils.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class SuiteConfig {
    public static DriverScript script;

    @BeforeSuite
    public void beforeClass() throws Exception {
        DriverScript.customerName = "Webtest" + BaseTools.getDateChar();
        script = new DriverScript();
        DriverScript.actionsKeywords = new ActionsKeywords();
        DriverScript.method = DriverScript.actionsKeywords.getClass().getMethods();
        ExcelUtils.setExcelFile(Constants.Path_TestData);
        // 创建一个文件输入流对象，参数来源外部OR.properties文件
        FileInputStream fs = new FileInputStream(Constants.Path_OR);
        InputStreamReader isr = new InputStreamReader(fs, "UTF-8");
        // 创建一个Properties对象
        ActionsKeywords.OR = new Properties();
        // 加载全部对象仓库文件
        ActionsKeywords.OR.load(isr);
        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath();
        FileInputStream ffs = new FileInputStream(courseFile + Constants.Path_SYS);
        InputStreamReader iisr = new InputStreamReader(ffs, "UTF-8");
        DriverScript.sysPro = new Properties();
        DriverScript.sysPro.load(iisr);
        Constants.Path_TestData = DriverScript.sysPro.getProperty("dataEngine");
        ExcelUtils.setExcelFile(Constants.Path_TestData);
        DOMConfigurator.configure(DriverScript.sysPro.getProperty("log4j"));
        ActionsKeywords.browserType = DriverScript.sysPro.getProperty("browserType");
    }

    @AfterSuite
    public void afterClass() {
        ActionsKeywords.closeBrowser(0, "", "", "", "");
    }
}
