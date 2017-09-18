/*
 * Configuration owner for mCube Gateway
 */
package com.intelym.client.configuration;

import com.intelym.client.logger.LoggerFactory;
import com.intelym.client.logger.IntelymLogger;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;
import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @copyright reserved to Intelym
 * @author Hari Nair
 * @since Sep 2017
 * @version 1.0
 */
public class ClientConfiguration {
    
    private final static IntelymLogger mLog = LoggerFactory.getLogger(ClientConfiguration.class);
    private FastMap<String, String> configMap; // The Map to keep the configuration information
    private String splitter = "\\,";
    private static ClientConfiguration engineConfiguration;
    private ClientConfiguration(String config) throws Exception{
        configMap = new FastMap<String, String>();
        readClientConfiguration(config);
        
    }
    
    /** Create a singleton class by following methods, first with config file, second without config file 
     * @param config string the path
     * @return ClientConfiguration the static configuration instance
     * @throws java.lang.Exception
     */
    
    public static ClientConfiguration newInstance(String config) throws Exception{
        if(engineConfiguration == null){
            mLog.info("Initializing engine configuration, configuration file is " + config);
            engineConfiguration = new ClientConfiguration(config);
        }
        return engineConfiguration;
    }
    
    public static ClientConfiguration getInstance() throws Exception{ return engineConfiguration; }
        
    /**
     * Reads the configuration file for the engine from the given file as input
     * @param config the file where the configs to be read
     * @throws Exception 
     */
    private void readClientConfiguration(String config) throws Exception{
        if(config == null) {
            throw new Exception("Invalid engine configuration information");
        }
        try{
            RandomAccessFile rFile = new RandomAccessFile(config, "r");
            if(rFile == null){
                mLog.warn("configuration file " + config + " not found, system is exiting");
                System.exit(0);
            }
            String tmp = null;
            while((tmp = rFile.readLine()) != null){
                String[] mapped = tmp.split("=");
                if(mapped.length < 2){
                    //throw new Exception("Invalid data in engine configuration information, " + tmp);
                }
                else {
                    String key = mapped[0].toUpperCase().trim();
                    String value = mapped[1].trim();
                    configMap.put(key, value);
                }
            }
            mLog.info("Configuration file has been read succesfully..");
        }catch(IOException ex){
            mLog.error("Failed to read configuration file " + ex.getMessage());
            throw ex;
        }
    }
    
    /**
     * returns the value for the given key from the configuration
     * @param key
     * @return 
     */
    public String getString(String key){
        if(key == null) { return ""; }
        if(!configMap.containsKey(key)) { return ""; }
        return configMap.get(key.toUpperCase());
    }
    
    /**
     * caller should know when to use this method, 
     * to be used to read multiple entry data
     * @param key
     * @return 
     */
    public List getList(String key){
        if(key == null) {
            return null;
        }
        String tmp = configMap.get(key.toUpperCase());
        if(tmp == null) {
            return null;
        }
        String listableTmp[];
        listableTmp = tmp.split(splitter);
        List<String> list = new FastList<String>();
        list.addAll(Arrays.asList(listableTmp));
        return list;
    }

    public Integer getTimeout() {
        String tmp = getString("WS.Timeout");
        if(tmp == null){
            return 5000;
        }
        else{
            return Integer.parseInt(tmp);
        }
    }
    
    public String getSplitter(){
        return (getString("Splitter") == null) ? splitter : getString("Splitter");
    }
}
