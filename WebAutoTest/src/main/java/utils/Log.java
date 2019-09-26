package utils;

import config.Constants;
import org.apache.log4j.Logger;
import testsuite.DriverScript;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Log {

    // 初始化log4j log
    private static Logger Log = Logger.getLogger(utils.Log.class.getName());
    public static String OutputFileName = getDateTimeByFormat(new Date(), "yyyyMMdd_HHmmss");
	private static OutputStreamWriter outputStreamWriter;
	private static String logFileName;

    // 运行测试用例之前的日志输出
    public static void startTestCase(String sTestCaseName){
    	Output("INFO",sTestCaseName+"-S-T-A-R-T-");
        Log.info("*******************************************************");
        Log.info("$$$$$$$$$          "+sTestCaseName+ "         $$$$$$$$$$");
        Log.info("*******************************************************");
    }

    // 用例执行结束后日志输出
    public static void endTestCase(String sTestCaseName){
    	Output("INFO",sTestCaseName+"-E---N---D-");
    }

    // 以下是不同日志级别的方法，方便需要的时候调用，一般info和error用得最多
    public static void info(String message) {
       Output("INFO",message);
    }

    public static void warn(String message) {
    	 Output("WARN",message);
    }

    public static void error(String message) {
    	 Output("ERROR",message);
    }

    public static void fatal(String message) {
        Log.fatal(message);
    }

    public static void debug(String message) {
        Log.debug(message);
    }
    
    public static void Output(String logTypeName, String logMessage) {
		Date date = new Date();
		String logTime = getDateTimeByFormat(date, "yyyy-MM-dd HH:mm:ss.SSS");
		String logEntry = logTime + " " + logTypeName + ": " + logMessage + "\r\n";
		System.out.print(logEntry);
		WriteLog(logEntry);
	}
    
    private static String getDateTimeByFormat(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);

	}
    
    private static void WriteLog(String logEntry) {

		try {
			File directory = new File("");//参数为空
			String courseFile = directory.getCanonicalPath();
			System.out.println(11);
			FileInputStream ffs = new FileInputStream(courseFile + Constants.Path_SYS);
			InputStreamReader isr = new InputStreamReader(ffs, "UTF-8");
			DriverScript.sysPro = new Properties();
			DriverScript.sysPro.load(isr);
			// 定义日志文件保存路径和日志文件名称
			logFileName = courseFile + DriverScript.sysPro.getProperty("logPath") + OutputFileName + ".log";
			if (outputStreamWriter == null) {
				File logFile = new File(logFileName);
				if (!logFile.exists())
					logFile.createNewFile();
				// 利用OutputStreamWriter往日志文件写内容，字符编码是unicode
				outputStreamWriter = new OutputStreamWriter(new FileOutputStream(logFileName), "UTF-8");
			}
			outputStreamWriter.write(logEntry, 0, logEntry.length());
			outputStreamWriter.flush();
		} catch (Exception e) {
			System.out.println(LogType.LogTypeName.ERROR.toString() + ": Failed to write the file " + logFileName);
			e.printStackTrace();
		}
	}

	public static void caseTime(String sTestCaseID, String string) {
		Output("INFO",sTestCaseID+"耗时："+string+"s");
	}

	public static void testTime(String string, String string1) {
		Output("INFO","共耗时：" + string + "m" + string1 + "s");
	}

}
