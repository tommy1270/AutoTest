package config;

public class Constants {
    public static String Path_TestData;
    // OR(对象仓库)文件路径
    public static final String Path_OR = ".//src/main/resources/OR.properties";
    public static final String Path_SYS = ".//src/main/resources/system.properties";
    // dataEngine.xlsx中一些单元格的索引值
    public static final int Col_TestCaseID = 0;
    public static final int Col_TestScenarioID = 1;
    public static final int Col_PageObject = 3 ;
    public static final int Col_ActionKeyword = 5 ;
    public static final int Col_RunMode = 2 ;
    // DataEngmine.excel中sheet的名称
    public static final String Sheet_TestSteps = "Test Steps";
    // 第二个工作簿的名称
    public static final String Sheet_TestCases = "Test Cases";

    // 第一个是测试用例结果标记列的索引，第二个是测试步骤里面的结果列索引
    public static final int Col_Result = 3;
    public static final int Col_DataSet = 6;
    public static final int Col_Data=7;
    public static final int Col_TestStepRunMode=8;
    public static final int Col_TestStepResult = 9;
    public static final int Col_RelativePath = 4;
    public static final int Col_ScreenShootPath = 10;
    public static final int Col_IFContinue = 11;

    // 结果状态标记
    public static final String KEYWORD_FAIL = "FAIL";
    public static final String KEYWORD_PASS = "PASS";

}
