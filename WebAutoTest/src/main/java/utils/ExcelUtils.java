package utils;

import config.ActionsKeywords;
import config.Constants;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelUtils {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;

	// 设置Excel文件路径，方便读取到文件
	public static void setExcelFile(String Path) throws Exception {
		try {
			FileInputStream ExcelFile = new FileInputStream(Path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
		} catch (Exception e) {
			Log.error("Class Utils | Method setExcelFile | Exception desc : " + e.getMessage());
			ActionsKeywords.sResult = false;
		}
	}

	// 读取Excel文件单元格数据
	// 新增sheet name参数，这样就可以去读取Test Steps和Test Cases两个工作表的单元格数据
	public static String getCellData(int RowNum, int ColNum, String SheetName) throws Exception {
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = "";
			if (Cell == null) {
				return CellData;
			}
			if(Cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
				CellData = Cell.getNumericCellValue()+"";
				int endIndex = CellData.indexOf(".")+1;
				if(Integer.parseInt(CellData.substring(endIndex))==0){
					CellData = CellData.substring(0, endIndex-1);
				}
			}else{
				CellData = Cell.getStringCellValue();
			}
			
			return CellData;
		} catch (Exception e) {
			Log.error("Class Utils | Method getCellData | Exception desc : " + e.getMessage());
			ActionsKeywords.sResult = false;
			return "";
		}
	}

	// 得到一共多少行数据
	public static int getRowCount(String SheetName) {
		int iNumber = 0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			iNumber = ExcelWSheet.getLastRowNum();
		} catch (Exception e) {
			Log.error("Class Utils | Method getRowCount | Exception desc : " + e.getMessage());
			ActionsKeywords.sResult = false;
		}
		return iNumber;
	}

	// 得到测试用例的行号
	public static int getRowContains(String sTestCaseName, int colNum, String SheetName) throws Exception {
		int iRowNum = 1;
		try {
			// ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int rowCount = ExcelUtils.getRowCount(SheetName);
			while (iRowNum <= rowCount) {
				if (ExcelUtils.getCellData(iRowNum, colNum, SheetName).equalsIgnoreCase(sTestCaseName)) {
					break;
				}
				iRowNum++;
			}
		} catch (Exception e) {
			Log.error("Class Utils | Method getRowContains | Exception desc : " + e.getMessage());
			ActionsKeywords.sResult = false;
		}
		return iRowNum;
	}

	// 计算一个测试用例有多少个步骤
	public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception {
		try {
			for (int i = iTestCaseStart; i <= ExcelUtils.getRowCount(SheetName); i++) {
				String testCaseID = ExcelUtils.getCellData(i, Constants.Col_TestCaseID, SheetName);
				if (!sTestCaseID.equals(testCaseID)) {
					int number = i - 1;
					return number;
				}
			}
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int number = ExcelWSheet.getLastRowNum();
			return number;
		} catch (Exception e) {
			Log.error("Class Utils | Method getRowContains | Exception desc : " + e.getMessage());
			ActionsKeywords.sResult = false;
			return 0;
		}
	}

	// 构造一个往单元格写数据的方法，主要是用来写结果pass还是fail
	public static void setCellData(String Result, int RowNum, int ColNum, String SheetName) throws Exception {
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			XSSFCellStyle style = ExcelWBook.createCellStyle();
			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
			}
			if (Result.equals("PASS")) {
				style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				style.setFillForegroundColor(HSSFColorPredefined.GREEN.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			} else if(Result.equals("FAIL")) {
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
				style.setFillForegroundColor(HSSFColorPredefined.RED.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			} else{
			}
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			Cell.setCellValue(Result);
			Cell.setCellStyle(style);
			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
			ExcelWBook.write(fileOut);
			// fileOut.flush();
			fileOut.close();
			ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
		} catch (Exception e) {
			Log.error("Class Utils | Method setRowContains | Exception desc : " + e.getMessage());
			ActionsKeywords.sResult = false;
		}
	}

}