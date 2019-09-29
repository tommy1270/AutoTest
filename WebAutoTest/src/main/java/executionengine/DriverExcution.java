package executionengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import utils.BaseTools;

@SpringBootApplication
public class DriverExcution {
    public static String customerName;
    public static String code;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DriverExcution.class, args);
        customerName = "Webtest" + BaseTools.getDateChar();
        code = BaseTools.getRandomNum(18);
        DriverScript driverScript = new DriverScript();
        driverScript.execute_TestCase();
    }
}