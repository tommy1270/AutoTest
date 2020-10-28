package testsuite;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SmokeTest {

    @Test(priority = 1)
    public void Login_001() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /*@Test(priority = 2, dependsOnMethods = {"Login_001"})
    public void CreateCustomer_002() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 3, dependsOnMethods = {"CreateCustomer_002"})
    public void UpdateCustomerBasicInfo_003() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 4, dependsOnMethods = {"UpdateCustomerBasicInfo_003"})
    public void UpdateCustomerBusinessInfo_004() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 5, dependsOnMethods = {"UpdateCustomerBusinessInfo_004"})
    public void CreateContact_005() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 6, dependsOnMethods = {"CreateContact_005"})
    public void UpdateContact_006() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 7, dependsOnMethods = {"UpdateContact_006"})
    public void DeleteContact_007() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 8, dependsOnMethods = {"DeleteContact_007"})
    public void CreateOrder_008() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 9, dependsOnMethods = {"CreateOrder_008"})
    public void CreateBook_009() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 10, dependsOnMethods = {"CreateBook_009"})
    public void EnterIntoBook_010() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 11, dependsOnMethods = {"EnterIntoBook_010"})
    public void BookInitial_011() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 12, dependsOnMethods = {"BookInitial_011"})
    public void CreateAccount_012() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 13, dependsOnMethods = {"CreateAccount_012"})
    public void UpdateAccount_013() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 14, dependsOnMethods = {"UpdateAccount_013"})
    public void DeleteAccount_014() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 15, dependsOnMethods = {"DeleteAccount_014"})
    public void AccountOpenclose_015() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 16, dependsOnMethods = {"AccountOpenclose_015"})
    public void QuantityBeginning_016() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 17, dependsOnMethods = {"QuantityBeginning_016"})
    public void AccountCodeSetting_017() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 18, dependsOnMethods = {"AccountCodeSetting_017"})
    public void ImportAccountBeginning_018() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 19, dependsOnMethods = {"ImportAccountBeginning_018"})
    public void AccountChangeToAssist_019() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 20, dependsOnMethods = {"AccountChangeToAssist_019"})
    public void ImportAssistBeginning_020() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 21, dependsOnMethods = {"ImportAssistBeginning_020"})
    public void UpdateAssistBeginning_021() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 22, dependsOnMethods = {"UpdateAssistBeginning_021"})
    public void CustomerAssist_022() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 23, dependsOnMethods = {"CustomerAssist_022"})
    public void SupplierAssist_023() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 24, dependsOnMethods = {"SupplierAssist_023"})
    public void StockAssist_024() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 25, dependsOnMethods = {"StockAssist_024"})
    public void DepartmentAssist_025() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 26, dependsOnMethods = {"DepartmentAssist_025"})
    public void ItemAssist_026() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 27, dependsOnMethods = {"ItemAssist_026"})
    public void StaffAssist_027() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 28, dependsOnMethods = {"StaffAssist_027"})
    public void BatchDeleteAssist_028() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 29, dependsOnMethods = {"BatchDeleteAssist_028"})
    public void ImportAssist_029() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 30, dependsOnMethods = {"ImportAssist_029"})
    public void DocumentTypesIncome_030() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 31, dependsOnMethods = {"DocumentTypesIncome_030"})
    public void DocumentTypesPurchase_031() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 32, dependsOnMethods = {"DocumentTypesPurchase_031"})
    public void CreateFixedAssets_032() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 33, dependsOnMethods = {"CreateFixedAssets_032"})
    public void UpdateFixedAssets_033() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 34, dependsOnMethods = {"UpdateFixedAssets_033"})
    public void FixedAssetsDisposition_034() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 35, dependsOnMethods = {"FixedAssetsDisposition_034"})
    public void DeleteFixedAssets_035() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 36, dependsOnMethods = {"DeleteFixedAssets_035"})
    public void CreateIntangibleAssets_036() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 37, dependsOnMethods = {"CreateIntangibleAssets_036"})
    public void UpdateIntangibleAssets_037() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 38, dependsOnMethods = {"UpdateIntangibleAssets_037"})
    public void DeleteIntangibleAssets_038() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 39, dependsOnMethods = {"DeleteIntangibleAssets_038"})
    public void CreateLongtermPrepaid_039() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 40, dependsOnMethods = {"CreateLongtermPrepaid_039"})
    public void UpdateLongtermPrepaid_040() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 41, dependsOnMethods = {"UpdateLongtermPrepaid_040"})
    public void DeleteLongtermPrepaid_041() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 42, dependsOnMethods = {"DeleteLongtermPrepaid_041"})
    public void TrialBalance_042() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 43, dependsOnMethods = {"TrialBalance_042"})
    public void CreateVoucher_043() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 44, dependsOnMethods = {"CreateVoucher_043"})
    public void UpdateVoucher_044() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 45, dependsOnMethods = {"UpdateVoucher_044"})
    public void AuditVoucher_045() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 46, dependsOnMethods = {"AuditVoucher_045"})
    public void CopyVoucher_046() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 47, dependsOnMethods = {"CopyVoucher_046"})
    public void CombineVoucher_047() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 48, dependsOnMethods = {"CombineVoucher_047"})
    public void ReverseVoucher_048() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 49, dependsOnMethods = {"ReverseVoucher_048"})
    public void InsertVoucher_049() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 50, dependsOnMethods = {"InsertVoucher_049"})
    public void IssuedVoucher_050() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 51, dependsOnMethods = {"IssuedVoucher_050"})
    public void DeleteVoucher_051() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 52, dependsOnMethods = {"DeleteVoucher_051"})
    public void RefreshCodeVoucher_052() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 53, dependsOnMethods = {"RefreshCodeVoucher_052"})
    public void CreateGoods_053() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 54, dependsOnMethods = {"CreateGoods_053"})
    public void UpdateGoods_054() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 55, dependsOnMethods = {"UpdateGoods_054"})
    public void DeleteGoods_055() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 56, dependsOnMethods = {"DeleteGoods_055"})
    public void BatchUpdateGoods_056() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 57, dependsOnMethods = {"BatchUpdateGoods_056"})
    public void BatchDeleteGoods_057() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test(priority = 58, dependsOnMethods = {"BatchDeleteGoods_057"})
    public void CreatePayroll_058() throws Exception {
        SuiteConfig.script.execute_TestCase(Thread.currentThread().getStackTrace()[1].getMethodName());
    }*/

    @Test(dataProvider = "getDataFromExcel")
    public void test01(String username,String password){
        System.out.println(username);
        System.out.println(password);
    }

    @DataProvider
    public Iterator<Object[]> getDataFromCSV(){
        //Object[][] objects = {{"tom","123"},{"jerry","456"},{"jack","789"}};
        String path ="./src/main/resources/data.csv";
        System.out.println(path);
        return readDataFromCSV(path);
    }

    @DataProvider
    public Object[][] getDataFromExcel(){
        //Object[][] objects = {{"tom","123"},{"jerry","456"},{"jack","789"}};
        String path ="./src/main/resources/data.xlsx";
        System.out.println(path);
        return readDataFromExcel(path);
    }

    public Iterator<Object[]> readDataFromCSV(String path){
        List<Object[]> list = new ArrayList<Object[]>();
        try{
            FileReader is = new FileReader(new File(path));
            BufferedReader br = new BufferedReader(is);
            while (br.ready()){
                list.add(br.readLine().split(","));
            }
            return list.iterator();
        }catch(Exception e){
            throw new SkipException(e.getMessage());
        }
    }

    public Object[][] readDataFromExcel(String path){
        Workbook workbook;
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int rowInExcel = sheet.getPhysicalNumberOfRows();
            int columnInExcel = sheet.getRow(0).getPhysicalNumberOfCells();
            String[][] obj = new String[rowInExcel-1][columnInExcel];
            for (int row = 0; row < rowInExcel; row++) {
                for (int col = 0; col < columnInExcel; col++) {
                    sheet.getRow(row).getCell(col).setCellType(Cell.CELL_TYPE_STRING);
                    obj[row-1][col] = sheet.getRow(row).getCell(col).getStringCellValue();
                    System.out.println(obj[row-1][col]);
                }
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
