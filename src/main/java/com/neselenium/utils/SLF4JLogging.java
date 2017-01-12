package com.neselenium.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;
import org.testng.ITestContext;

public class SLF4JLogging {

    private static ThreadLocal<Logger> logger = new ThreadLocal<Logger>();

    public static void initLogger(final ITestContext context, final String className) {

        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, Utils.getParam( "testLogLevel"));
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        Logger logger = LoggerFactory.getLogger(className);
        setLogger(logger);
        //return logger;
    }


    public static Logger getLogger() {
        return logger.get();
    }

    public static void setLogger(Logger log) {
        logger.set(log);
    }
}
