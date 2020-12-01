package com.thduc.instafake.config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4J2PropertiesConfig {
    private static Logger logger = LogManager.getLogger(Log4J2PropertiesConfig.class);

    public void performSomeTask(Object parameter1, Object parameter2, Object parameter3, Object parameter4) {
        logger.info("performSomeTask() [START] [PARAM] parameter1={}, parameter2={}, parameter3={}, parameter4={}", parameter1, parameter2, parameter3, parameter4);
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");
        logger.trace("This is a trace message");
        logger.info("This is a info message with {}", "test");
    }
}
