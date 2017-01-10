package com.neselenium.utils;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class LocalDriverManager {

    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    public static WebDriver getDriver() {
        return webDriver.get();
    }

    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    @SuppressWarnings("unchecked")
    public static void destroyLocalDriver() {
        if (Utils.getBrowserType().contains("APP")) {
            ((AppiumDriver<MobileElement>) getDriver()).closeApp();
            ((AppiumDriver<MobileElement>) getDriver()).quit();
        } else if (getDriver() != null) {
            getDriver().close();
            getDriver().quit();

        }
    }

}
