package com.neselenium.utils;

import java.util.List;

import org.openqa.selenium.Dimension;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.HideKeyboardStrategy;

public class IosObjects {

    public static String iosFindValueInTable(String what) {
        List<MobileElement> cells = null;
        IOSElement table;
        int x, y = 0;
        //first view in UICatalog is a table
        table = (IOSElement) ((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByClassName("UIATableView");
        //find number of rows
        List<MobileElement> rows = table.findElementsByClassName("UIATableCell");

        for (x = 0; x < rows.size(); x++) {
            cells = rows.get(x).findElementsByClassName("UIAStaticText");
            for (y = 0; y < cells.size(); y++) {
                if (cells.get(y).getText().contains(what))
                    return cells.get(y).getText();
            }
        }
        return null;
    }

    public static String iosFindStaticTexts(int cell, int staticTexts) {

        String searchValue = ".tableViews()[0].cells()[" + cell + "].staticTexts()[" + staticTexts + "]";
        return ((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByIosUIAutomation(searchValue).getText();

    }

    public static void iosEditTextFields(int cell, int textFields, String what) {

        String searchValue = ".tableViews()[0].cells()[" + cell + "].textFields()[" + textFields + "]";
        ((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByIosUIAutomation(searchValue).clear();

        ((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByIosUIAutomation(searchValue).click();
        ((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByIosUIAutomation(searchValue).sendKeys(what);
        return;
    }

    public static void iosEditAccountDetail(int row, int cell, String what) {

        String searchValue = ".tableViews()[0].cells()[" + row + "].textFields()[" + cell + "]";
        ((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByIosUIAutomation(searchValue).clear();

        ((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByIosUIAutomation(searchValue).click();
        ((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByIosUIAutomation(searchValue).sendKeys(what);

        return;
    }

    public static void iosDebugTable() {
        IOSElement table = (IOSElement) ((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByClassName("UIATableView");
        List<MobileElement> rows = table.findElementsByClassName("UIATableCell");

        for (int x = 0; x < rows.size(); x++) {
            List<MobileElement> cells = rows.get(x).findElementsByClassName("UIAStaticText");
            for (int y = 0; y < cells.size(); y++)
                System.out.println("Row[" + x + "] Cell[" + y + "] = [" + cells.get(y).getText() + "]");
        }
    }

    @SuppressWarnings("unchecked")
    public static void iosHideKeyboard() {
        ((IOSDriver<IOSElement>) LocalDriverManager.getDriver()).hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Return");
    }

    public static int scrollDownFor(String what) {

        int x = 0;

        Dimension dimensions = LocalDriverManager.getDriver().manage().window().getSize();
        Double screenHeightStart = dimensions.getHeight() * 0.3;
        int scrollStart = screenHeightStart.intValue();
        //System.out.println("s=" + scrollStart);
        Double screenHeightEnd = dimensions.getHeight() * 0.2;
        int scrollEnd = screenHeightEnd.intValue();
        for (int i = 0; i < dimensions.getHeight(); i++) {
            ((IOSDriver<?>) LocalDriverManager.getDriver()).swipe(0, scrollStart, 0, scrollEnd, 100);

            for (x = 0; x < 9; x++) {
                String searchString = ".tableViews()[0].cells()[" + x + "].staticTexts()[1]";
                String viewImage = ".tableViews()[0].cells()[" + x + "].staticTexts()[0]";
                //this is because sometimes there is a viewImage on the screen that makes the index 2 instead of 1
                String checkImage = ".tableViews()[0].cells()[" + x + "].staticTexts()[2]";

                if (((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByIosUIAutomation(searchString).isDisplayed()) {

                    //Sometimes we see a ViewImage in the list and changes the staticTexts indexes.
                    if (((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByIosUIAutomation(viewImage).getText()
                        .contains("View Image"))
                        if (((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByIosUIAutomation(checkImage).getText()
                            .contains(what))
                            return x;

                    if (((IOSDriver<?>) LocalDriverManager.getDriver()).findElementByIosUIAutomation(searchString).getText()
                        .contains(what))
                        return x;
                }
            }
        }
        return x;
    }

}
