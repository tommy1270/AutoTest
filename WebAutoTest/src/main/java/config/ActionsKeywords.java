package config;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BaseTools;
import utils.Log;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ActionsKeywords {

    public static WebDriver driver;
    private static WebDriverWait wait;
    private static boolean result;
    private static int count;
    public static Properties OR;
    public static boolean sResult;// step是否通过
    public static boolean cResult;// case是否通过
    public static String browserType;

    public static void openBrowser(int iTestStep, String object, String relativePath, String data, String tData) {
        Log.info("第" + iTestStep + "行：" + "打开浏览器");
        try {
            if (data.equals("Firefox")) {
                System.setProperty("webdriver.gecko.driver", ".//src//main//resources//lib//geckodriver.exe");
                Log.info("第" + iTestStep + "行：" + "火狐浏览器已经启动");
            } else if (data.equals("IE")) {
                driver = new InternetExplorerDriver();
                Log.info("第" + iTestStep + "行：" + "IE 浏览器已经启动");
            } else if (data.equals("Chrome")) {
                System.setProperty("webdriver.chrome.driver", ".//src//main//resources//lib//chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
                if ("yes".equals(browserType)) {//浏览器有头
                    driver = new ChromeDriver();
                } else if ("no".equals(browserType)) {//浏览器无头
                    driver = new ChromeDriver(options);
                }
                Log.info("第" + iTestStep + "行：" + "谷歌浏览器已经启动");
            } else if (data.equals("Edge")) {
                driver = new EdgeDriver();
                Log.info("第" + iTestStep + "行：" + "Edge 浏览器已经启动");
            }
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            if ("yes".equals(browserType)) {//浏览器有头
                driver.manage().window().maximize();
            } else if ("no".equals(browserType)) {//浏览器无头
                driver.manage().window().setSize(new Dimension(1980, 1280));
            }
            wait = new WebDriverWait(driver, 3);
        } catch (Exception e) {
            Log.info("第" + iTestStep + "行：" + "Not able to open the Browser --- " + e.getMessage());
            BaseTools.screenShoot(driver);
            sResult = false;
            cResult = false;
        }
    }

    public static void openUrl(int iTestStep, String object, String relativePath, String data, String tData) {
        try {
            Log.info("第" + iTestStep + "行：" + "打开服务器地址：" + data);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(data);
        } catch (Exception e) {
            Log.info("第" + iTestStep + "行：" + "无法打开测试服务器地址 --- " + e.getMessage());
            BaseTools.screenShoot(driver);
            sResult = false;
            cResult = false;
        }

    }

    public static void click(int iTestStep, String object, String relativePath, String data, String tData) throws InterruptedException {
        try {
//			Thread.sleep(300);
            result = false;
            By by = By.xpath(OR.getProperty(object));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            List<WebElement> list = driver.findElements(by);
            WebElement we = null;
            if (list.size() == 1) {
                if (!"".equals(relativePath)) {
                    List<WebElement> webList = list.get(0).findElements(By.xpath(relativePath));
                    if (webList.size() == 1) {
//						String str = webList.get(0).getText();
//						if(!"".equals(data) && !data.equals(str)) {
//							Log.error("第" + iTestStep + "行：" + "没有找到" + data);
//							BaseTools.screenShoot(driver);
//							sResult = false;
//							cResult = false;
//							return;
//						}
                        we = webList.get(0);
                    } else if (webList.size() > 1) {
                        for (WebElement webElement : webList) {
                            String str = webElement.getText();
                            if (!"".equals(tData)) {
                                if (str.equals(tData)) {
                                    we = webElement;
                                    break;
                                }
                            } else {
                                if (str.equals(data)) {
                                    we = webElement;
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    we = list.get(0);
                }
                wait.until(ExpectedConditions.elementToBeClickable(we));
                if (!"".equals(data)) {
                    Log.info("第" + iTestStep + "行：" + "点击： " + data);
                } else {
                    if (we.getAttribute("class").contains("icon-close")) {
                        Log.info("第" + iTestStep + "行：" + "关闭弹窗！ ");
                    } else {
                        Log.info("第" + iTestStep + "行：" + "点击： " + we.getText());
                    }
                }
                we.click();
                result = true;
            } else if (list.size() > 1) {
                boolean flag = false;
                for (WebElement element : list) {
                    String realData = element.getText().trim();
                    if (realData.contains("\n")) {
                        String[] strs = realData.split("\n");
                        if (strs.length == 2) {
                            if (strs[0].equals("*")) {
                                realData = strs[1];
                            } else {
                                realData = strs[0];
                            }
                        }
                    }
                    if (realData.equals(data)) {
                        if ("".equals(relativePath)) {
                            we = element;
                        } else {
                            List<WebElement> welist = element.findElements(By.xpath(relativePath));
                            if (welist.size() == 1) {
                                we = welist.get(0);
                            } else {
                                for (WebElement web : welist) {
                                    if (web.getText().equals(tData)) {
                                        we = web;
                                        break;
                                    }
                                }
                            }
                        }
                        flag = true;
//						if(ExpectedConditions.elementToBeClickable(we) == null) {
//							continue;
//						}
                        wait.until(ExpectedConditions.elementToBeClickable(we));
                        wait.until(ExpectedConditions.visibilityOf(we));
                        if (!"".equals(tData)) {
                            we.click();
                            Log.info("第" + iTestStep + "行：" + "点击:" + tData);
                        } else if (!"".equals(we.getText())) {
                            Log.info("第" + iTestStep + "行：" + "点击: " + we.getText());
                            we.click();
                        } else {
                            we.click();
                            Log.info("第" + iTestStep + "行：" + "点击: " + data);
                        }
                        result = true;
                        count = 0;
                        return;
                    }
                }
                if (!flag) {
                    Thread.sleep(2000);
                    throw new Exception("element not found");
                }
            } else {
                Log.error("第" + iTestStep + "行：" + "列表没有加载到元素");
                BaseTools.screenShoot(driver);
                sResult = false;
                cResult = false;
            }
        } catch (Exception e) {
            Log.error("第" + iTestStep + "行：" + "无法点击元素--- " + e.getMessage());
            Thread.sleep(1000);
            if (!result && count < 3) {
                count++;
                Log.info("第" + iTestStep + "行：第" + count + "次重试~~~");
                click(iTestStep, object, relativePath, data, tData);
                return;
            }
            BaseTools.screenShoot(driver);
            sResult = false;
            cResult = false;
        }
    }

    public static void input(int iTestStep, String object, String relativePath, String data, String tData) {
        try {
            By by = By.xpath(OR.getProperty(object));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            List<WebElement> list = driver.findElements(by);
            WebElement we = null;
            String inputData = "";
            if (list.size() == 1) {
                if ("".equals(tData)) {
                    inputData = data;
                } else {
                    inputData = tData;
                }
                if ("".equals(relativePath)) {
                    we = list.get(0);
                } else {
                    we = list.get(0).findElement(By.xpath(relativePath));
                }
            } else if (list.size() > 1) {
                String findData = data;
                inputData = tData;
                for (WebElement element : list) {
                    String str = element.getText();
                    if (str.contains("\n")) {
                        String[] strs = str.split("\n");
                        if (strs.length == 2) {
                            if (strs[0].equals("*")) {
                                str = strs[1];
                            } else {
                                str = strs[0];
                            }
                        } else if (strs.length == 3) {
                            str = strs[0] + strs[1];
                        }
                    }
                    if (str.equals(findData)) {
                        if ("".equals(relativePath)) {
                            we = element;
                        } else {
                            we = element.findElement(By.xpath(relativePath));
                        }
                        break;
                    }
                }
            } else {
                Log.error("第" + iTestStep + "行：" + "未找到:" + object);
                BaseTools.screenShoot(driver);
                sResult = false;
                cResult = false;
                return;
            }
            if ("（空）".equals(inputData)) {
                inputData = "";
            } else if (inputData.contains("path:")) {
                File directory = new File("");
                String courseFile = directory.getCanonicalPath();
                inputData = directory.getCanonicalPath() + inputData.split(":")[1];
            }
            try {
                we.clear();
            } catch (Exception e) {
            } finally {
                Thread.sleep(500);
                we.sendKeys(inputData);
                Log.info("第" + iTestStep + "行：" + "输入: " + inputData);
            }
        } catch (Exception e) {
            Log.error("第" + iTestStep + "行：" + "无法输入文本 --- " + e.getMessage());
            BaseTools.screenShoot(driver);
            sResult = false;
            cResult = false;
        }
    }

    public static void check(int iTestStep, String object, String relativePath, String data, String tData) throws Exception {
        try {
            result = false;
            By by = By.xpath(OR.getProperty(object));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            List<WebElement> list = driver.findElements(by);
            String realData = "";
            if (list.size() == 1) {
                WebElement element = list.get(0);
                if ("".equals(relativePath)) {
                    realData = element.getText();
                    if (element.getTagName().equals("input") || element.getTagName().equals("textarea")) {
                        realData = element.getAttribute("value");
                    } else if (element.getTagName().equals("select")) {
                        Select select = new Select(element);
                        realData = select.getFirstSelectedOption().getText();
                    }
                } else {
                    realData = element.findElement(By.xpath(relativePath)).getText().trim();
                    String tagName = list.get(0).findElement(By.xpath(relativePath)).getTagName();
                    if (tagName.equals("input") || tagName.equals("textarea")) {
                        realData = element.findElement(By.xpath(relativePath)).getAttribute("value").trim();
                    } else if (tagName.equals("select")) {
                        Select select = new Select(list.get(0).findElement(By.xpath(relativePath)));
                        realData = select.getFirstSelectedOption().getText();
                    }
                    if ("（空）".equals(data)) {
                        data = "";
                    }
                    if (!"".equals(data)) {
                        if (!"".equals(tData) && !"（空）".equals(tData)) {
                            data = tData;
                        } else if ("（空）".equals(tData)) {
                            data = "";
                        }
                    }
                }
            } else if (list.size() > 1) {
                if ("".equals(relativePath)) {
                    for (WebElement element : list) {
                        String str = element.getText();
                        if (str.contains("\n")) {
                            String[] strs = str.split("\n");
                            if (strs.length == 2) {
                                if (strs[0].equals("*")) {
                                    str = strs[1];
                                } else {
                                    str = strs[0];
                                }
                            } else if (strs.length == 3) {
                                str = strs[0] + strs[1];
                            }
                        }
                        if (str.equals(data)) {
                            realData = data;
                            if (element.getTagName().equals("input") || element.getTagName().equals("textarea")) {
                                realData = element.getAttribute("value");
                            } else if (element.getTagName().equals("select")) {
                                Select select = new Select(element);
                                realData = select.getFirstSelectedOption().getText();
                            }
                            break;
                        }
                    }
                } else {
                    String sData = data;
                    for (WebElement element : list) {
                        String str = element.getText().trim();
                        if (str.contains("\n")) {
                            String[] strs = str.split("\n");
                            if (strs.length == 2) {
                                if (strs[0].equals("*")) {
                                    str = strs[1];
                                } else {
                                    str = strs[0];
                                }
                            } else if (strs.length == 3) {
                                str = strs[0] + strs[1];
                            }
                        }
                        if (str.equals(sData)) {
                            WebElement target = element.findElement(By.xpath(relativePath));
                            realData = target.getText().trim();
                            if (target.getTagName().equals("input") || target.getTagName().equals("textarea")) {
                                realData = target.getAttribute("value").trim();
                            } else if (target.getTagName().equals("select")) {
                                Select select = new Select(target);
                                realData = select.getFirstSelectedOption().getText().trim();
                            }
                            break;
                        }
                    }
                    if ("（空）".equals(tData)) {
                        data = "";
                    } else if (!"".equals(tData) && !"（空）".equals(tData)) {
                        data = tData;
                    }
                }
            } else {
                Log.error("第" + iTestStep + "行：" + "未找到" + object + "的数据");
                BaseTools.screenShoot(driver);
                sResult = false;
                cResult = false;
                return;
            }
            if (realData.equals(data)) {
                Log.info("第" + iTestStep + "行：" + object + "相关文本内容为：" + data);
                count = 0;
            } else {
                Log.error(realData);
                if (!result && count < 3) {
                    count++;
                    Log.info("第" + iTestStep + "行：第" + count + "次重试~~~");
                    Thread.sleep(1000);
                    check(iTestStep, object, relativePath, data, tData);
                    return;
                }
                Log.error("第" + iTestStep + "行：" + object + "相关文本内容不为：" + data);
                BaseTools.screenShoot(driver);
                sResult = false;
                cResult = false;
            }
            result = true;
        } catch (Exception e) {
            Log.error("第" + iTestStep + "行：" + e.getMessage());
            if (!result && count < 3) {
                count++;
                Log.info("第" + iTestStep + "行：第" + count + "次重试~~~");
                Thread.sleep(1000);
                check(iTestStep, object, relativePath, data, tData);
                return;
            }
            BaseTools.screenShoot(driver);
            sResult = false;
            cResult = false;
        }
    }

    public static void checkDisplayed(int iTestStep, String object, String relativePath, String data, String tData) throws Exception {
        try {
            result = false;
            By by = By.xpath(OR.getProperty(object));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            List<WebElement> list = driver.findElements(by);
            WebElement we = null;
            if (list.size() == 1) {
                if (!"".equals(relativePath)) {
                    we = list.get(0).findElement(By.xpath(relativePath));
                    if (!we.isDisplayed()) {
                        Log.error("第" + iTestStep + "行：" + we.getText() + "未出现");
                        BaseTools.screenShoot(driver);
                        sResult = false;
                        cResult = false;
                        count = 0;
                        return;
                    }
                } else {
                    we = list.get(0);
                }
            } else if (list.size() > 1) {
                if (!"".equals(relativePath)) {
                    for (WebElement element : list) {
                        String str = element.getText().trim();
                        if (str.equals(data)) {
                            List<WebElement> welist = element.findElements(By.xpath(relativePath));
                            if (welist.size() == 1) {
                                we = welist.get(0);
                            } else {
                                for (WebElement web : welist) {
                                    if (web.getText().equals(tData)) {
                                        we = web;
                                        break;
                                    }
                                }
                            }
                            if (we.isEnabled() == false) {
                                Log.error("第" + iTestStep + "行：" + object + "相关标签未出现");
                                BaseTools.screenShoot(driver);
                                sResult = false;
                                cResult = false;
                                return;
                            }
                        }
                    }
                } else {
                    for (WebElement element : list) {
                        String str = element.getText().trim();
                        boolean result = str.equals(data);
                        if (result) {
                            we = element;
                            break;
                        }
                    }
                }
            } else {
                throw new Exception(object + "未出现");
            }
            Log.info("第" + iTestStep + "行：" + we.getText() + "存在！");
            count = 0;
            result = true;
        } catch (Exception e) {
            Log.error("第" + iTestStep + "行：" + e.getMessage());
            if (!result && count < 3) {
                count++;
                Log.info("第" + iTestStep + "行：第" + count + "次重试~~~");
                Thread.sleep(1000);
                checkDisplayed(iTestStep, object, relativePath, data, tData);
                return;
            }
            BaseTools.screenShoot(driver);
            sResult = false;
            cResult = false;
        }
    }

    public static void checkNotDisplayed(int iTestStep, String object, String relativePath, String data, String tData) {
        try {
            Thread.sleep(1000);
            List<WebElement> list = driver.findElements(By.xpath(OR.getProperty(object)));
            WebElement we = null;
            if (list.size() == 1) {
                if (!"".equals(relativePath)) {
                    we = list.get(0).findElement(By.xpath(relativePath));
                    if (we.isDisplayed()) {
                        Log.error("第" + iTestStep + "行：" + we.getText() + "出现了");
                        BaseTools.screenShoot(driver);
                        sResult = false;
                        cResult = false;
                        return;
                    }
                } else {
                    if ("".equals(data)) {
                        Log.error("第" + iTestStep + "行：" + object + "出现了");
                        BaseTools.screenShoot(driver);
                        sResult = false;
                        cResult = false;
                        return;
                    } else {
                        if (data.equals(list.get(0).getText())) {
                            Log.error("第" + iTestStep + "行：" + data + "出现了");
                            BaseTools.screenShoot(driver);
                            sResult = false;
                            cResult = false;
                            return;
                        }
                    }
                }
            } else if (list.size() > 1) {
                if (!"".equals(relativePath)) {
                    for (WebElement element : list) {
                        if (element.getText().equals(data)) {
                            if (element.findElements(By.xpath(relativePath)).isEmpty() == false) {
                                Log.error("第" + iTestStep + "行：" + object + "相关标签出现");
                                BaseTools.screenShoot(driver);
                                sResult = false;
                                cResult = false;
                                return;
                            }
                            break;
                        }
                    }
                } else {
                    for (WebElement element : list) {
                        if (element.getText().equals(data)) {
                            Log.error("第" + iTestStep + "行：" + object + "相关标签出现");
                            BaseTools.screenShoot(driver);
                            sResult = false;
                            cResult = false;
                            return;
                        }
                    }
                }
            } else if (list.isEmpty()) {
                if (!"".equals(data)) {
                    Log.info("第" + iTestStep + "行：" + data + "未出现");
                } else {
                    Log.info("第" + iTestStep + "行：" + object + "未出现");
                }
                return;
            }
            Log.info("第" + iTestStep + "行：" + data + "未出现");
        } catch (Exception e) {
            Log.error("第" + iTestStep + "行：" + e.getMessage());
            BaseTools.screenShoot(driver);
            sResult = false;
            cResult = false;
        }
    }

    public static void chooseDate(int iTestStep, String object, String relativePath, String data, String tData) {
        try {
            String xpath = OR.getProperty(object);
            By by = By.xpath(xpath);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            WebElement element = driver.findElement(by);

            if (data.split("-").length == 3) {
                element.findElement(By.xpath("descendant::thead/tr/th[2]/button")).click();//点击月份回到选择月份页面
            }
            int year = Integer.parseInt(data.split("-")[0]);
            String month = data.split("-")[1];
            WebElement currentYear = element.findElement(By.xpath("descendant::thead/tr/th[2]/button/strong"));
            int SysDateYear = Integer.parseInt(currentYear.getText());
            int n = 0;
            if (year < SysDateYear) {
                n = SysDateYear - year;
                for (int i = 0; i < n; i++) {
                    element.findElement(By.xpath("descendant::span[text()='previous']/..")).click();
                    Thread.sleep(500);
                }
            } else if (year > SysDateYear) {
                n = year - SysDateYear;
                for (int i = 0; i < n; i++) {
                    element.findElement(By.xpath("descendant::span[text()='next']/..")).click();
                    Thread.sleep(500);
                }
            }
            if ("01".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[1]/td[1]/button")).click();
            } else if ("02".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[1]/td[2]/button")).click();
            } else if ("03".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[1]/td[3]/button")).click();
            } else if ("04".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[2]/td[1]/button")).click();
            } else if ("05".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[2]/td[2]/button")).click();
            } else if ("06".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[2]/td[3]/button")).click();
            } else if ("07".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[3]/td[1]/button")).click();
            } else if ("08".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[3]/td[2]/button")).click();
            } else if ("09".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[3]/td[3]/button")).click();
            } else if ("10".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[4]/td[1]/button")).click();
            } else if ("11".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[4]/td[2]/button")).click();
            } else if ("12".equals(month)) {
                element.findElement(By.xpath("descendant::tbody/tr[4]/td[3]/button")).click();
            } else {
                Log.error("第" + iTestStep + "行：" + "月份输入错误");
                BaseTools.screenShoot(driver);
                sResult = false;
                cResult = false;
            }
            if (data.split("-").length == 3) {
                List<WebElement> dayslist = element.findElements(By.xpath("descendant::span[@class='ng-binding']"));
                String day = data.split("-")[2];
                for (WebElement webElement : dayslist) {
                    String str = webElement.getText();
                    if (day.equals(str)) {
                        webElement.findElement(By.xpath("..")).click();
                        break;
                    }
                }
                Log.info("第" + iTestStep + "行：" + "选择" + year + "年" + month + "月" + day + "日");
                return;
            }
            Log.info("第" + iTestStep + "行：" + "选择" + year + "年" + month + "月");
        } catch (Exception e) {
            Log.error("第" + iTestStep + "行：" + "无法选择--- " + e.getMessage());
            BaseTools.screenShoot(driver);
            sResult = false;
            cResult = false;
        }
    }

    public static void wait(int iTestStep, String object, String relativePath, String data, String tData)
            throws Exception {
        long time = Long.parseLong(data + "000");
        Thread.sleep(time);
        Log.info("第" + iTestStep + "行：" + "等待： " + data + "秒");
    }

    public static void moveTo(int iTestStep, String object, String relativePath, String data, String tData) {
        try {
            By by = By.xpath(OR.getProperty(object));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            List<WebElement> list = driver.findElements(by);
            Actions action = new Actions(driver);
            WebElement web = null;
            if (list.size() == 1) {
                if (!"".equals(relativePath)) {
                    web = list.get(0).findElement(By.xpath(relativePath));
                } else {
                    web = list.get(0);
                }
            } else {
                for (WebElement element : list) {
                    if (element.getText().equals(data)) {
                        if (!"".equals(relativePath)) {
                            web = element.findElement(By.xpath(relativePath));
                        } else {
                            web = element;
                        }
                        break;
                    }
                }
            }
            action.moveToElement(web).perform();
            action.release();
            Log.info("第" + iTestStep + "行：" + "移入元素： " + object);
        } catch (Exception e) {
            Log.error("第" + iTestStep + "行：" + "无法移入元素--- " + e.getMessage());
            BaseTools.screenShoot(driver);
            sResult = false;
            cResult = false;
        }
    }

    /*
     * public static void press(int iTestStep, String object, String relativePath,
     * String data, String tData){ try { data = data.toUpperCase(); Robot robot =
     * new Robot(); switch(data){ case "ENTER": robot.keyPress(KeyEvent.VK_ENTER);
     * robot.keyRelease(KeyEvent.VK_ENTER); break; case "F12":
     * robot.keyPress(KeyEvent.VK_F12); robot.keyRelease(KeyEvent.VK_F12); break;
     * case "=": robot.keyPress(KeyEvent.VK_EQUALS);
     * robot.keyRelease(KeyEvent.VK_EQUALS); break; default:
     * Log.error("第"+iTestStep+"行："+"没有该按键"); BaseTools.screenShoot(driver); sResult
     * = false; cResult = false; } } catch (Exception e) {
     * Log.error("第"+iTestStep+"行："+e.getMessage()); BaseTools.screenShoot(driver);
     * sResult = false; cResult = false; } }
     */

    public static void checkStyle(int iTestStep, String object, String relativePath, String data, String tData) {
        try {
            Thread.sleep(500);
            By by = By.xpath(OR.getProperty(object));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            List<WebElement> list = driver.findElements(by);
            WebElement we = null;
            if (list.size() == 1) {
                if ("".equals(relativePath)) {
                    we = list.get(0);
                } else {
                    we = list.get(0).findElement(By.xpath(relativePath));
                }
                tData = data;
            } else if (list.size() > 1) {
                if ("".equals(relativePath)) {
                    for (WebElement element : list) {
                        if (element.getText().equals(data)) {
                            we = element;
                            break;
                        }
                    }
                } else {
                    for (WebElement element : list) {
                        if (element.getText().equals(data)) {
                            we = element.findElement(By.xpath(relativePath));
                            break;
                        }
                    }
                }
            } else {
                Log.error("第" + iTestStep + "行：" + "未找到" + object + "的数据");
                BaseTools.screenShoot(driver);
                sResult = false;
                cResult = false;
                return;
            }
            String className = we.getAttribute("class");
            String disabled = we.getAttribute("disabled");
            if (className.contains(tData) || "true".equals(disabled)) {
                Log.info("第" + iTestStep + "行：" + object + "样式正确");
            } else {
                Log.error("第" + iTestStep + "行：" + object + "样式错误");
                BaseTools.screenShoot(driver);
                sResult = false;
                cResult = false;
            }
        } catch (Exception e) {
            Log.error("第" + iTestStep + "行：" + e.getMessage());
            BaseTools.screenShoot(driver);
            sResult = false;
            cResult = false;
        }
    }

    public static void switchWindow(int iTestStep, String object, String relativePath, String data, String tData) {
        Set<String> handles = driver.getWindowHandles(); // 获取所有已经打开的窗口句柄；
        String handle = driver.getWindowHandle(); // 获取当前窗口句柄
        for (String WindowHandle : handles) {
            if (!handle.equals(WindowHandle)) {
                driver.switchTo().window(WindowHandle);
                if (!driver.getTitle().equals(data)) {
                    continue;
                }
                Log.info("第" + iTestStep + "行：切换到指定浏览器页签！");
                break;
            }
        }
        if ("yes".equals(browserType)) {//浏览器有头
            driver.manage().window().maximize();
        } else if ("no".equals(browserType)) {//浏览器无头
            driver.manage().window().setSize(new Dimension(1980, 1280));
        }
    }

    public static void switchIntoFrame(int iTestStep, String object, String relativePath, String data, String tData) {
        WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
        driver.switchTo().frame(element);
        Log.info("第" + iTestStep + "行：进入frame标签！");
    }

    public static void switchOutofFrame(int iTestStep, String object, String relativePath, String data, String tData) {
        driver.switchTo().defaultContent();
    }

    public static void switchWindowAndCloseOld(int iTestStep, String object, String relativePath, String data, String tData) {
        Set<String> handles = driver.getWindowHandles(); // 获取所有已经打开的窗口句柄；
        String handle = driver.getWindowHandle(); // 获取当前窗口句柄
        for (String WindowHandle : handles) {
            if (!handle.equals(WindowHandle)) {
                driver.close();
                driver.switchTo().window(WindowHandle);
                break;
            }
        }
        switchWindow(iTestStep, object, relativePath, data, tData);
        Log.info("第" + iTestStep + "行：" + "关闭浏览器当前页签");
    }

    public static void closeBrowser(int iTestStep, String object, String relativePath, String data, String tData) {
        try {
            Log.info("关闭并退出浏览器");
            driver.quit();
        } catch (Exception e) {
            Log.error("第" + iTestStep + "行：" + "无法关闭浏览器--- " + e.getMessage());
            BaseTools.screenShoot(driver);
            sResult = false;
            cResult = false;
        }
    }
}
