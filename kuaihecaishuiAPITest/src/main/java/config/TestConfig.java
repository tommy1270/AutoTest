package config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestConfig {
    public static String loginUrl;
    public static String addDepartmentUrl;


    public static DefaultHttpClient defaultHttpClient;
    public static CookieStore store;
}
