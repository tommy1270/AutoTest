package utils;



import model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {
    private static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getUrl(InterfaceName name) {
        String address = bundle.getString("test.url");
        String uri = "";

        if(name.equals(InterfaceName.LOGIN)){
            uri=bundle.getString("login.uri");
        }else if(name.equals(InterfaceName.ADDDEPARTMENT)){
            uri = bundle.getString("addDepartment.uri");
        }

        String testUrl = address + uri;
        return testUrl;
    }


}
