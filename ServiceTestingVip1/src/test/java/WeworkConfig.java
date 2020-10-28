public class WeworkConfig {
    public String agentId = "1000002";
    public String corpid = "ww64f10c11bcb5db89";
    public String secret = "U01in9axxzuuVGhwBxXuy6_MTIyBaJKZ_55_vcIYy2U";

    private static WeworkConfig weworkConfig;

    public static WeworkConfig getInstance(){
        if (weworkConfig==null){
            weworkConfig = new WeworkConfig();
        }
        return weworkConfig;
    }
}
