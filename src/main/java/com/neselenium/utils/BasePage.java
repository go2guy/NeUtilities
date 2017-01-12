package com.neselenium.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class BasePage extends LocalDriverManager {

    private WebDriver driver = getDriver();
    public static String mobileType;

    public BasePage() {}

    public boolean waitForTextToBePresent(WebElement element, String value) {

        boolean isExists = true;
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, value));
        } catch (NoSuchElementException e) {
            isExists = false;
        } catch (Exception e) {
            isExists = false;
        }

        return isExists;
    }

    public boolean waitFortitleToBePresent(String value) {

        boolean isExists = true;
        try {
            wait.until(ExpectedConditions.titleContains(value));
        } catch (NoSuchElementException e) {
            isExists = false;
        } catch (Exception e) {
            isExists = false;
        }

        return isExists;
    }

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

    public boolean waitForSpinner() {

        boolean isExists = true;
        try {
            if (getBrowserType().contains("ANDROID"))
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("android.widget.ProgressBar")));
            else if (getBrowserType().contains("IOS")) {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("In progress")));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("Network connection in progress")));
            } else
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("blocking-spinner")));

        } catch (NullPointerException e) {
            isExists = false;
        } catch (Exception e) {
            isExists = false;
        }

        return isExists;

    }

    public String formatBalance(String what) {

        if (what.contains("*"))
            return what.substring(what.indexOf('$') + 1, what.length() - 1);
        else
            return what.substring(what.indexOf('$') + 1, what.length());

    }

    public void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void click(WebElement element) {

        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        waitForSpinner();
    }

    public void type(WebElement element, String keys) {

        wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(keys);
    }

    public void actionType(WebElement element, String keys) {

        wait.until(ExpectedConditions.elementToBeClickable(element)).click();

        Actions action = new Actions(driver);

        action.moveToElement(element).click().build().perform();
        action.moveToElement(element).sendKeys(keys).build().perform();
        action.keyDown(element, Keys.CONTROL).keyUp(element, Keys.CONTROL).perform();

    }

    public void sendKeysWithEvent(WebElement element, String keys, String event) throws Exception {

        type(element, keys);
        switch (event) {
            case "keyup":
                new Actions(driver).keyDown(element, Keys.CONTROL).keyUp(element, Keys.CONTROL).perform();
                break;
            case "onblurJS":
                doJavascriptOnElement(element, element.getAttribute("onblur"));
                break;
            case "onfocus":
                element.click();
                break;
            case "keyupJS":
                doJavascriptOnElement(element, element.getAttribute("onkeyup"));
                break;
            case "keyupTAB":
                element.sendKeys(Keys.TAB);
                break;
        }

    }

    public void doJavascriptOnElement(WebElement element, String javascript) throws Exception {
        ((JavascriptExecutor) driver).executeScript(javascript, element);
    }

    public boolean waitForElement(WebElement element) {
        boolean isExists = true;
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (NoSuchElementException e) {
            isExists = false;
        } catch (Exception e) {
            isExists = false;
        }

        return isExists;
    }

    public boolean waitForVisibilityOfElement(WebElement element) {

        boolean isExists = true;
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException e) {
            isExists = false;
        } catch (Exception e) {
            isExists = false;
        }

        return isExists;
    }

    public boolean waitForTextToBePresentInElement(WebElement element, String value) {
        boolean isExists = true;
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, value));
        } catch (NoSuchElementException e) {
            isExists = false;
        } catch (Exception e) {
            isExists = false;
        }

        return isExists;

    }

    public boolean isElementDisplayed(WebElement element) {
        boolean isExists = true;
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            isExists = false;
        } catch (Exception e) {
            isExists = false;
        }

        return isExists;

    }

    public boolean isElementDisplayed(By element) {
        boolean isExists = true;
        try {
            return ((WebElement) element).isDisplayed();
        } catch (NoSuchElementException e) {
            isExists = false;
        } catch (Exception e) {
            isExists = false;
        }

        return isExists;

    }

    public boolean waitForTitle(String title) {
        return wait.until(ExpectedConditions.titleContains(title));
    }


    public void waitForText(String what) {

        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[contains(.,'" + what + "')]"), what));
            assert true;
        } catch (NoSuchElementException e) {
            assert false;
        } catch (Exception e) {
            assert false;
        }

    } // End is waitForText


    public Boolean isDisplayed(WebElement element) {
        boolean isExists = true;
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            isExists = false;
        } catch (Exception e) {
            isExists = false;
        }

        return isExists;
    }


    public void waitForPageLoad() {
        long startTime = System.currentTimeMillis();
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        // WebDriverWait wait = new WebDriverWait(driver, 30);
        System.out.println("Waiting for Page Load to complete");
        wait.until(pageLoadCondition);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Page Load Completed in Milliseconds [" +
            totalTime + "]");
    }


    public static void copyToClipbord(String copyTo) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection str = new StringSelection(copyTo);
        clipboard.setContents(str, null);
    }

    public void setText(WebElement element,
            String value) {
        copyToClipbord(value);
        click(element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
        //element.clear();
        //element.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
        wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
    }

    public String getText(WebElement element) {
        return element.getText();
    }

}
