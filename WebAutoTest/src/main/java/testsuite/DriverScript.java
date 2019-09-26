package testsuite;

import config.ActionsKeywords;
import config.Constants;
import utils.BaseTools;
import utils.ExcelUtils;
import utils.Log;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DriverScript {
	public String sActionKeyword;
	public String sPageObject;
	public String sRelativePath;
	public String sData;
	public String tData;
	public String ifContinue;
	public static Method[] method;
	public static ActionsKeywords actionsKeywords;
	public int iTestStep;
	public static String customerName;
	public static String code;
	public static String updatedContractName;
	public static String resignedContractName;
	public static String expiredContractName;
	public static Properties sysPro;
	
	public void execute_TestCase(String sTestCaseID) throws Exception {
		long starttime = System.currentTimeMillis();
		Log.startTestCase(sTestCaseID);
		iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
		int iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
		
		// 下面这个for循环的次数就等于测试用例的步骤数
		for (; iTestStep <= iTestLastStep; iTestStep++) {
			ActionsKeywords.sResult = true;
			String stepRunMode = ExcelUtils.getCellData(iTestStep, Constants.Col_TestStepRunMode, Constants.Sheet_TestSteps);
			if (stepRunMode.equals("Yes")) {
				sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword, Constants.Sheet_TestSteps);
				sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
				sRelativePath = ExcelUtils.getCellData(iTestStep, Constants.Col_RelativePath, Constants.Sheet_TestSteps);
				sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
				if("${customerName}".equals(sData)){
					sData = customerName;
				}else if("${code}".equals(sData)){
					sData = code;
				}else if("${updatedContractName}".equals(sData)){
					sData = updatedContractName;
				}else if("${resignedContractName}".equals(sData)){
					sData = resignedContractName;
				}else if("${expiredContractName}".equals(sData)){
					sData = expiredContractName;
				}
				tData = ExcelUtils.getCellData(iTestStep, Constants.Col_Data, Constants.Sheet_TestSteps);
				if("${customerName}".equals(tData)){
					tData = customerName;
				}
				ifContinue = ExcelUtils.getCellData(iTestStep, Constants.Col_IFContinue, Constants.Sheet_TestSteps);
				execute_Actions();
				if (!ActionsKeywords.sResult) {
					if ("Yes".equals(ifContinue)) {
						continue;
					} else if ("No".equals(ifContinue)) {
						break;
					} else {
						Log.error("参数设定错误…… ");
						break;
					}
				}
			}
		}
		long endtime = System.currentTimeMillis();
		Log.caseTime(sTestCaseID, ((endtime - starttime) / 1000) + "");
		if (ActionsKeywords.cResult) {
			//ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestcase, Constants.Col_Result, Constants.Sheet_TestCases);
			Log.endTestCase(sTestCaseID);
		} else {
			//ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestcase, Constants.Col_Result, Constants.Sheet_TestCases);
			Log.endTestCase(sTestCaseID);
			if ("No".equals(ifContinue) && !ActionsKeywords.sResult) {
				throw new Exception("ERROR");
			}
		}
	}

	public void execute_Actions() throws Exception {
		for (Method value : method) {
			if (value.getName().equals(sActionKeyword)) {
				value.invoke(actionsKeywords, iTestStep + 1, sPageObject, sRelativePath, sData, tData);
				ActionsKeywords.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//				Thread.sleep(1000);
				if (ActionsKeywords.sResult) {
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					break;
				} else {
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					ExcelUtils.setCellData(BaseTools.screenPath, iTestStep, Constants.Col_ScreenShootPath, Constants.Sheet_TestSteps);
					break;
				}
			}
		}
	}
}
