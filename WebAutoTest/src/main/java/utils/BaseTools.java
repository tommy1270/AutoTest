package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class BaseTools {
	public static String screenPath;
	public static String getDateChar() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = sdf.format(date);
		return str;
	}
	
	public static void screenShoot(WebDriver driver){
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File directory = new File("");//参数为空 
			String courseFile = directory.getCanonicalPath(); 
			screenPath = courseFile+"\\src\\main\\resources\\screenShoot\\"+ BaseTools.getDateChar()+".png";
			FileUtils.copyFile(src, new File(screenPath));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static String getRandomChar(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
				'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z' };
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(62)]);
		}
		return buffer.toString();
	}

	public static String getRandomNum(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(10)]);
		}
		return buffer.toString();
	}

	public static String getRandomLetter(int length) {
		char[] chr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
				'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(52)]);
		}
		return buffer.toString();
	}

	
	public static String getRandomName(int num) {
		String postName = UUID.randomUUID().toString().replace("-", "").substring(0, num);
		return postName;
	}
	
	public static String getRandomUserName(int letter,int num){
		String str1 = BaseTools.getRandomLetter(letter);
		String str2 = BaseTools.getRandomNum(num);
		return str1 + str2;
	}
}
