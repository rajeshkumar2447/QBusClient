package com.intelym.client.logger;
import org.apache.log4j.PropertyConfigurator;
/**
 *
 * @author Hari Nair
 * @since Mar 2012
 * @version 1.0
 */
public class LoggerFactory {

    private static boolean configured = false;

    public static IntelymLogger getLogger(Class className) {
        if (configured) {
            return new IntelymLogger(className);
        }
        else {
            PropertyConfigurator.configure("src/conf/log4j.properties");
            configured = true;
            return new IntelymLogger(className);
        }
    }
    
    public static IntelymLogger getMessageLogger(Class className){
        if (configured) {
            return new IntelymLogger(className);
        }
        else {
            PropertyConfigurator.configure("messagelog4j.properties");
            configured = true;
            return new IntelymLogger(className);
        }
    }
}
