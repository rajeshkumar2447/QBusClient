/**
 * Contains the static constant values for the application life cycle. 
 * these constants information either defined on design time or given through configuration parameter
 */

package com.intelym.qbus.client.common;

/**
 *
 * @author harinair
 */
public final class Constants {

    public static final String      QUICK_CLIENT_BUS = "RIWA_HOME",
                                    MAJOR_VERSION = "1.00",
                                    MINOR_VERSION = "000",
                                    BUILD_VERSION = "1000";
    
    public static String            QUICK_BUS_CLIENT_CONFIGURATION = "src/conf/quickbus.properties";
    
    public static String            CLEAN_START = "--cleanstart";
                            
    public static final String      ON = "true",
                                    OFF = "false";
    
    public static final boolean     ENABLE = true,
                                    DISABLE = false;
    
    public static final String      MEMORY_DB_PATH = "MEMORY_DB_PATH";      
    public static int               HUNTER_LISTEN_PORT = 8765;
    
    public static final String  QUICK_BUS_CLIENT_ON = "QUICK_BUS_CLIENT_ON";
                                
    
                                
    public static final int     STATUS_UNKNOWN = 0,
                                STATUS_SENT = 1,
                                STATUS_CONFIRM = 2,
                                STATUS_CANCEL = 3,
                                STATUS_PARTIAL = 4,
                                STATUS_TRADED = 5,
                                STATUS_REJECTED = 6,
                                STATUS_ERROR = 7;
    
    public static final String  MEMORY_COMPUTE_ADDRESS = "MEMORY_COMPUTE_ADDRESS",
                                MEMORY_COMPUTE_PORT = "MEMORY_COMPUTE_PORT";
    
    public static final String  AWAKE_TIME_FOR_RECONNECT = "AWAKE_TIME_FOR_RECONNECT",
                                TIMEOUT_FOR_RECONNECT = "TIMEOUT_FOR_RECONNECT";
    
    public static final String  KEY_FILE_WITH_PATH = "KEY_FILE_WITH_PATH";
}
