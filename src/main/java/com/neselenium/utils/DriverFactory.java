package com.neselenium.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class DriverFactory {

    private DriverFactory() {
        //Do-nothing..Do not allow to initialize this class from outside
    }


    @SuppressWarnings("unchecked")
    public static void createInstance(ITestContext context) {
        WebDriver driver = null;
        DesiredCapabilities cap = new DesiredCapabilities();
        File app;
        File file;

        Utils.setContext(context);

        switch (Utils.getParam("browse")) {
            case "ANDROID_APP":

                app = new File(Utils.getParam("apkfile"));
                cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                cap.setCapability(MobileCapabilityType.DEVICE_NAME, Utils.getParam("device"));
                cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, Utils.getParam("appPackage"));
                cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, Utils.getParam("appActivity"));
                cap.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, Utils.getParam("appWaitActivity"));
                cap.setCapability(MobileCapabilityType.UDID, Utils.getParam("udid"));
                cap.setCapability(MobileCapabilityType.PLATFORM_NAME, Utils.getParam("platformName"));
                cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, Utils.getParam("platformVersion"));
                cap.setCapability("autoLaunch", false);
                cap.setCapability(MobileCapabilityType.NO_RESET, true);

                driver = new AndroidDriver<MobileElement>(Utils.AppiumService.getUrl(), cap);

                ((AppiumDriver<MobileElement>) driver).launchApp();
                break;
            case "IOS_APP":

                cap.setCapability(MobileCapabilityType.DEVICE_NAME, Utils.getParam("device"));
                cap.setCapability(MobileCapabilityType.UDID, Utils.getParam("udid"));
                cap.setCapability(MobileCapabilityType.PLATFORM_NAME, Utils.getParam("platformName"));
                cap.setCapability(MobileCapabilityType.NO_RESET, true);
                cap.setCapability(IOSMobileCapabilityType.BUNDLE_ID, Utils.getParam("bundleID"));
                //                cap.setCapability(IOSMobileCapabilityType.SEND_KEY_STRATEGY, "setValue");

                driver = new IOSDriver<MobileElement>(Utils.AppiumService.getUrl(), cap);
                break;

            case "IOS_APP_SIM":

                app = new File(Utils.getParam("ipafile"));
                cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                cap.setCapability(MobileCapabilityType.DEVICE_NAME, Utils.getParam("device"));
                cap.setCapability(MobileCapabilityType.PLATFORM_NAME, Utils.getParam("platformName"));
                cap.setCapability(MobileCapabilityType.NO_RESET, true);
                cap.setCapability(IOSMobileCapabilityType.BUNDLE_ID, Utils.getParam("bundleID"));

                driver = new IOSDriver<MobileElement>(Utils.AppiumService.getUrl(), cap);
                break;

            case "CHROME":
                file = new File(Utils.getParam("driverpath"));
                setSystemProperty("webdriver.chrome.driver", file.getAbsolutePath());
                driver = new ChromeDriver();
                break;
            case "DOCKER_FF":
                try {
                    driver = new RemoteWebDriver(new URL(Utils.getParam("docker_address")), DesiredCapabilities.firefox());
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "DOCKER_CHROME":
                try {
                    driver = new RemoteWebDriver(new URL(Utils.getParam("docker_address")), DesiredCapabilities.chrome());
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "IE":
                file = new File(Utils.getParam("iedriver"));
                setSystemProperty("webdriver.ie.driver", file.getAbsolutePath());
                DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability("nativeEvents", true);
                capabilities.setCapability("ignoreProtectedModeSettings", true);
                capabilities.setCapability("requireWindowFocus", true);
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                driver = new InternetExplorerDriver(capabilities);
                break;
            case "HUBCHROME":
                try {
                    driver = new RemoteWebDriver(new URL(Utils.getParam("docker_address")), DesiredCapabilities.chrome());
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "HUBFIREFOX":
                try {
                    driver = new RemoteWebDriver(
                        new URL(Utils.getParam("docker_address")),
                        DesiredCapabilities.firefox());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                file = new File(Utils.getParam("driverpath"));
                setSystemProperty("webdriver.gecko.driver", file.getAbsolutePath());
                driver = new FirefoxDriver();
                break;
        }

        Utils.setBrowserType(Utils.getParam("browse"));
        LocalDriverManager.setWebDriver(driver);
        return;
    }

    public static WebDriver loadApp(ITestContext context) {

        WebDriver driver = null;
        DesiredCapabilities cap = new DesiredCapabilities();
        File app;

        switch (Utils.getParam("browse")) {
            case "ANDROID_APP":
                app = new File(Utils.getParam("apkfile"));
                cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                cap.setCapability(MobileCapabilityType.DEVICE_NAME, Utils.getParam("device"));
                cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, Utils.getParam("appPackage"));
                cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, Utils.getParam("appActivity"));
                cap.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, Utils.getParam("appWaitActivity"));
                cap.setCapability(MobileCapabilityType.UDID, Utils.getParam("udid"));
                cap.setCapability(MobileCapabilityType.PLATFORM_NAME, Utils.getParam("platformName"));
                cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, Utils.getParam("platformVersion"));

                driver = new AndroidDriver<MobileElement>(Utils.AppiumService.getUrl(), cap);

                break;

            default:
                app = new File(Utils.getParam("apkfile"));
                cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                cap.setCapability(MobileCapabilityType.DEVICE_NAME, Utils.getParam("device"));
                cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, Utils.getParam("appPackage"));
                cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, Utils.getParam("appActivity"));
                cap.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, Utils.getParam("appWaitActivity"));
                cap.setCapability(MobileCapabilityType.UDID, Utils.getParam("udid"));
                cap.setCapability(MobileCapabilityType.PLATFORM_NAME, Utils.getParam("platformName"));
                cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, Utils.getParam("platformVersion"));

                driver = new AndroidDriver<MobileElement>(Utils.AppiumService.getUrl(), cap);

                break;
        }


        Utils.setBrowserType(Utils.getParam("browse"));
        LocalDriverManager.setWebDriver(driver);
        return driver;
    }

    private static void setSystemProperty(String property, String path) {
        System.setProperty(property, path);
    }


}
