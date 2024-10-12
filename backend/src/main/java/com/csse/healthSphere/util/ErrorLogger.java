package com.csse.healthSphere.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorLogger {
    private static final Logger logger = LoggerFactory.getLogger(ErrorLogger.class);

    /**
     * Logs the provided error message using the logger.
     *
     * Example usage:
     * <pre>{@code
     * @
     * public class MyService {
     *     @Autowired
     *     private ErrorLogger errorLogger;
     *
     *     public void doSomething() {
     *         try {
     *             // ... do something that might throw an exception
     *         } catch (Exception e) {
     *             errorLogger.logError(e.getMessage()); // Logging the error message
     *         }
     *     }
     * }
     * }</pre>
     *
     * @param errorMessage The error message to be logged.
     */
    public static void logError(String errorMessage) {
        System.out.println(errorMessage);
        logger.error(errorMessage);
    }
}
