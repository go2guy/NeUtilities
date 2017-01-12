package com.neselenium.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.google.common.base.Stopwatch;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;


public final class Utils {

    static Stopwatch timer;

    public static String mobileType;
    private static ITestContext _context;
    public static AppiumDriverLocalService AppiumService;
    DesiredCapabilities cap = new DesiredCapabilities();
    public AppiumDriverLocalService service = null;
    public AppiumServiceBuilder builder = null;

    // onstructor Called
    public Utils() {}

    public static String getBrowserType() {
        return mobileType;
    }

    public static void setBrowserType(String appType) {
        mobileType = appType;
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

    }


    public enum BrowserType {
        FIREFOX, IE, CHROME, ANDROID
    }



    public static String getParam(String value) {
        System.out.println("Param [" + value + "] requested from context");
        return getContext().getCurrentXmlTest().getParameter(value);
    }


    @SuppressWarnings("unchecked")
    public static void launchApp(String app,
            String platformVersion, String baseurl) {

        if (app.contains("APP"))
            ((AppiumDriver<MobileElement>) LocalDriverManager.getDriver()).launchApp();
        else
            LocalDriverManager.getDriver().get(baseurl);

    }

    //    public static void switchToWebView(WebDriver driver, String browse,
    //            String platformVersion,
    //            String baseurl) {
    //        if (browse.equals("ANDROID_APP") && Integer.parseInt(platformVersion) >= 19) {
    //            ((AppiumDriver<?>) LocalDriverManager.getDriver()).context("XXXXXXXXXX");
    //        } else if (browse.equals("ANDROID_APP") && Integer.parseInt(platformVersion) < 19) {
    //            ((AppiumDriver<?>) LocalDriverManager.getDriver()).switchTo().window("WEBVIEW");
    //        }
    //
    //    }

    public static void switchToNativeView(String browse,
            String platformVersion,
            String baseurl) {
        if (browse.equals("ANDROID_APP") && Integer.parseInt(platformVersion) >= 19) {
            ((AppiumDriver<?>) LocalDriverManager.getDriver()).context("NATIVE_APP");
        } else if (browse.equals("ANDROID_APP") && Integer.parseInt(platformVersion) < 19) {
            ((AppiumDriver<?>) LocalDriverManager.getDriver()).switchTo().window("NATIVE_APP");
        }

    }



    @SuppressWarnings("unchecked")
    public static void scrollDownByNunberOfSwipes(int swipes) {
        Dimension dimensions;
        Double screenHeightStart;
        Double screenHeightEnd;
        int scrollStart;
        int scrollEnd;
        Utils.sleep(2000);

        if (Utils.getBrowserType().contentEquals("ANDROID_APP")) {
            dimensions = LocalDriverManager.getDriver().manage().window().getSize();
            screenHeightStart = dimensions.getHeight() * 0.5;
            scrollStart = screenHeightStart.intValue();
            screenHeightEnd = dimensions.getHeight() * 0.2;
            scrollEnd = screenHeightEnd.intValue();
            for (int i = 0; i < swipes; i++) {
                ((AppiumDriver<MobileElement>) LocalDriverManager.getDriver()).swipe(0, scrollStart, 0, scrollEnd, 2000);
            }
        } else {
            dimensions = LocalDriverManager.getDriver().manage().window().getSize();
            screenHeightStart = dimensions.getHeight() * 0.3;
            scrollStart = screenHeightStart.intValue();
            screenHeightEnd = dimensions.getHeight() * 0.2;
            scrollEnd = screenHeightEnd.intValue();
            for (int i = 0; i < swipes; i++) {
                ((AppiumDriver<MobileElement>) LocalDriverManager.getDriver()).swipe(0, scrollStart, 0, scrollEnd, 100);
            }
        }

        Utils.sleep(2000);
        return;
    }

    @SuppressWarnings("unchecked")
    public static void scrollUpToElement(WebElement element) {

        Dimension dimensions;
        Double screenHeightStart;
        Double screenHeightEnd;
        int scrollStart;
        int scrollEnd;

        if (Utils.getBrowserType().contentEquals("ANDROID_APP")) {

            dimensions = LocalDriverManager.getDriver().manage().window().getSize();
            screenHeightStart = dimensions.getHeight() * 0.5;
            scrollStart = screenHeightStart.intValue();
            screenHeightEnd = dimensions.getHeight() * 0.2;
            scrollEnd = screenHeightEnd.intValue();
            for (int i = 0; i < dimensions.getHeight(); i++) {
                ((AppiumDriver<MobileElement>) LocalDriverManager.getDriver()).swipe(0, scrollEnd, 0, scrollStart, 2000);
                try {
                    if (element.isDisplayed())
                        break;
                } catch (NoSuchElementException e) {

                }
            }

        } else {

            dimensions = LocalDriverManager.getDriver().manage().window().getSize();
            screenHeightStart = dimensions.getHeight() * 0.3;
            scrollStart = screenHeightStart.intValue();
            screenHeightEnd = dimensions.getHeight() * 0.2;
            scrollEnd = screenHeightEnd.intValue();
            for (int i = 0; i < dimensions.getHeight(); i++) {
                ((AppiumDriver<MobileElement>) LocalDriverManager.getDriver()).swipe(0, scrollEnd, 0, scrollStart, 100);
                try {
                    if (element.isDisplayed())
                        break;
                } catch (NoSuchElementException e) {

                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void scrollDownToText(String what) {

        Dimension dimensions;
        Double screenHeightStart;
        Double screenHeightEnd;
        int scrollStart;
        int scrollEnd;

        if (Utils.getBrowserType().contentEquals("ANDROID_APP")) {

            dimensions = LocalDriverManager.getDriver().manage().window().getSize();
            screenHeightStart = dimensions.getHeight() * 0.5;
            scrollStart = screenHeightStart.intValue();
            screenHeightEnd = dimensions.getHeight() * 0.2;
            scrollEnd = screenHeightEnd.intValue();
            for (int i = 0; i < dimensions.getHeight(); i++) {
                ((AppiumDriver<MobileElement>) LocalDriverManager.getDriver()).swipe(0, scrollStart, 0, scrollEnd, 2000);
                try {
                    if (((AndroidDriver<MobileElement>) LocalDriverManager.getDriver())
                        .findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + what + "\")") != null)
                        break;
                } catch (NoSuchElementException e) {

                }
            }
        }

    }


    public static void startTimer() {
        timer = Stopwatch.createStarted();
    }

    public static Stopwatch stopTimer() {
        return timer.stop();
        //System.out.println("Method took: [" + Utils.stopTimer() + "]");
    }


    public static void takeScreenShot(ITestContext context, ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            Date date = new Date(System.currentTimeMillis());
            String dateString = date.toString();
            String screenShotName = getParam("ScreenShotDirectory") + dateString + result.getName() + ".png";
            File scrFile = ((TakesScreenshot) LocalDriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File(screenShotName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void AppiumService(ITestContext context) {

        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, getParam("platformName"));
        serverCapabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        serverCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);

        AppiumService = AppiumDriverLocalService
            .buildService(new AppiumServiceBuilder().withCapabilities(serverCapabilities)
                .withIPAddress("127.0.0.1")
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, getParam("appiumLogLevel")));

        AppiumService.start();

    }

    public static int getRndNumber() {
        Random random = new Random();
        int randomNumber = 0;
        boolean loop = true;
        while (loop) {
            randomNumber = random.nextInt();
            if (Integer.toString(randomNumber).length() == 10 && !Integer.toString(randomNumber).startsWith("-")) {
                loop = false;
            }
        }
        return randomNumber;
    }

    public static String getMethodName() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        return e.getMethodName();
    }

    public static void removeApp(ITestContext context) {
        String[] command =
            {
                Utils.getParam("adbLocation"),
                "shell",
                "pm",
                "uninstall",
                Utils.getParam("appPackage")};

        try {

            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Utils.sleep(1000);

    }

    public static void clearAppData(ITestContext context) {

        String[] command =
            {
                Utils.getParam("adbLocation"),
                "shell",
                "pm",
                "clear",
                Utils.getParam("appPackage")};
        Process execute = null;
        String returnValue = null;

        try {
            execute = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader output = new BufferedReader(new InputStreamReader(execute.getInputStream()));

        try {
            returnValue = output.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(returnValue, "Success");

        Utils.sleep(1000);

    }

    public static void setContext(ITestContext context) {
        _context = context;
    }

    public static ITestContext getContext() {
        return _context;
    }
}


