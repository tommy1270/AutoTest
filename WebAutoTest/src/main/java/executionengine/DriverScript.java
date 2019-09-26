package executionengine;

import config.ActionsKeywords;
import config.Constants;
import org.apache.log4j.xml.DOMConfigurator;
import utils.BaseTools;
import utils.ExcelUtils;
import utils.Log;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DriverScript {

	// 声明一个public static的类对象，所以我们可以在main方法范围之外去使用。
	public static ActionsKeywords actionsKeywords;
	public static String sActionKeyword;
	// 下面是返回类型是方法，这里用到反射类
	public static Method method[];
	public static String sPageObject;

	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sData;
	public static String sRelativePath;
	public static String stepRunMode;
	public static String tData;
	public static String ifContinue;
	public static Properties sysPro;

	// 这里我们初始化'ActionsKeywords'类的一个对象
	public DriverScript() throws Exception {
		actionsKeywords = new ActionsKeywords();
		method = actionsKeywords.getClass().getMethods();
		// 创建一个文件输入流对象，参数来源外部OR.properties文件
		FileInputStream fs = new FileInputStream(Constants.Path_OR);
		InputStreamReader isr = new InputStreamReader(fs,"UTF-8");
		// 创建一个Properties对象
		ActionsKeywords.OR = new Properties();
		// 加载全部对象仓库文件
		ActionsKeywords.OR.load(isr);
		FileInputStream ffs = new FileInputStream(Constants.Path_SYS);
		InputStreamReader iisr = new InputStreamReader(ffs,"UTF-8");
		sysPro = new Properties();
		sysPro.load(iisr);
		Constants.Path_TestData = sysPro.getProperty("dataEngine");
		ExcelUtils.setExcelFile(Constants.Path_TestData);
		DOMConfigurator.configure(sysPro.getProperty("log4j"));
		ActionsKeywords.browserType = sysPro.getProperty("browserType");
	}

	void execute_TestCase() throws Exception {
		long start = System.currentTimeMillis();
		// 获取测试用例数量
		int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
		// 外层for循环，有多少个测试用例就执行多少次循环
		for (int iTestcase = 1; iTestcase <= iTotalTestCases; iTestcase++) {
			// 从Test Case表获取测试ID
			ActionsKeywords.cResult = true;
			sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);
			// 获取当前测试用例的Run Mode的值
			sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode, Constants.Sheet_TestCases);
			// Run Mode的值控制用例是否被执行
			if (sRunMode.equals("Yes")) {
				long starttime = System.currentTimeMillis();
				Log.startTestCase(sTestCaseID);
				// 只有当Run Mode的单元格数据是Yes，下面代码才会被执行
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
				// 下面这个for循环的次数就等于测试用例的步骤数
				for (; iTestStep <= iTestLastStep; iTestStep++) {
					ActionsKeywords.sResult = true;
					stepRunMode = ExcelUtils.getCellData(iTestStep, Constants.Col_TestStepRunMode, Constants.Sheet_TestSteps);
					if(stepRunMode.equals("Yes")){
						sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword, Constants.Sheet_TestSteps);
						sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
						sRelativePath = ExcelUtils.getCellData(iTestStep, Constants.Col_RelativePath, Constants.Sheet_TestSteps);
						sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
						if("${customerName}".equals(sData)){
							sData = DriverExcution.customerName;
						}else if("${code}".equals(sData)){
							sData = DriverExcution.code;
						}
						tData = ExcelUtils.getCellData(iTestStep, Constants.Col_Data, Constants.Sheet_TestSteps);
						if("${customerName}".equals(tData)){
							tData = DriverExcution.customerName;
						}
						ifContinue = ExcelUtils.getCellData(iTestStep, Constants.Col_IFContinue, Constants.Sheet_TestSteps);
						execute_Actions();
						if (!ActionsKeywords.sResult) {
							if("Yes".equals(ifContinue)){
								continue;
							}else if("No".equals(ifContinue)){
								break;
							}else{
								Log.error("参数设定错误…… ");
								break;
							}
						}
					}
				}
				if (ActionsKeywords.cResult) {
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestcase, Constants.Col_Result, Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);
				}else{
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestcase, Constants.Col_Result, Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);
					if("No".equals(ifContinue)&& !ActionsKeywords.sResult){
						break;
					}
				}
				long endtime = System.currentTimeMillis();
				Log.caseTime(sTestCaseID,((endtime-starttime)/1000)+"");
			}
		}
		ActionsKeywords.closeBrowser(0, "", "", "", "");
		long end = System.currentTimeMillis();
		int seconds = (int) ((end - start)/1000);
		int minutes = seconds / 60; 
        int remainingSeconds = seconds % 60;
		Log.testTime(minutes + "", remainingSeconds + "");
	}

	private static void execute_Actions() throws Exception {
		for (Method value : method) {
			if (value.getName().equals(sActionKeyword)) {
				value.invoke(actionsKeywords, iTestStep + 1, sPageObject, sRelativePath, sData, tData);
				ActionsKeywords.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//				Thread.sleep(2000);
				if (ActionsKeywords.sResult) {
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult,
							Constants.Sheet_TestSteps);
					break;
				} else {
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult,
							Constants.Sheet_TestSteps);
					ExcelUtils.setCellData(BaseTools.screenPath, iTestStep, Constants.Col_ScreenShootPath,
							Constants.Sheet_TestSteps);
					break;
				}
			}
		}
	}

}