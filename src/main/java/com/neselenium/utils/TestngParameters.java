package com.neselenium.utils;

import org.testng.ITestContext;

public class TestngParameters {

    private static String dockerAddress;
    private static String mobileType;
    private static String chromeDriver;
    private static String testLogLevel;
    private static String baseUrl;

    public static void initParams(ITestContext context) {
        setBrowserType(context.getCurrentXmlTest().getParameter("browse"));
        setDockerAddress(context.getCurrentXmlTest().getParameter("docker_address"));
        setChromeDriver(context.getCurrentXmlTest().getParameter("chromedriver"));
        setTestLogLevel(context.getCurrentXmlTest().getParameter("testLogLevel"));

    }


    public static String getBrowserType() {
        return mobileType;
    }

    public static void setBrowserType(String appType) {
        mobileType = appType;
    }

    public static String getDockerAddress() {
        return dockerAddress;
    }

    public static void setDockerAddress(String address) {
        dockerAddress = address;
    }

    public static String getChromeDriver() {
        return chromeDriver;
    }

    public static void setChromeDriver(String chromedriver) {
        chromeDriver = chromedriver;
    }

    public static String getTestLogLevel() {
        return testLogLevel;
    }

    public static void setTestLogLevel(String testloglevel) {
        testLogLevel = testloglevel;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseurl) {
        baseUrl = baseurl;
    }

    //    <parameter name="userName" value ="single.corp"></parameter>
    //    <parameter name="userNamePassword" value ="Pass12345"></parameter>
    //    <parameter name="userNameMulti" value ="hscruggsmultiuat"></parameter>
    //    <parameter name="userNameMultiPassword" value ="Smokey40"></parameter>
    //    
    //    <parameter name="testrailURL" value = "https://cri.testrail.com/"></parameter>
    //    <parameter name="testrailUserName" value = "hscruggs@clientresourcesinc.com"></parameter>
    //    <parameter name="testRailPassword" value = "Smokey32!"></parameter>
    //    <parameter name="updateTestRail" value = "no"></parameter>
    //    <parameter name="firefoxTestRun" value = "2372"></parameter>
    //    <parameter name="ScreenShotDirectory" value = "ScreenShots/"></parameter>

}
