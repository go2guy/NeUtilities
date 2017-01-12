package com.neselenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class LocalDriverManager {

    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    public static WebDriverWait wait;

    public static WebDriver getDriver() {
        return webDriver.get();
    }

    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
        wait = new WebDriverWait(driver, 60);
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
