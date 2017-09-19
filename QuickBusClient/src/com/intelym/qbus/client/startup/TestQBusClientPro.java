/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelym.qbus.client.startup;

import com.intelym.client.configuration.ClientConfiguration;
import com.intelym.client.logger.LoggerFactory;
import com.intelym.client.logger.IntelymLogger;
import com.intelym.qbus.client.common.Constants;
import com.intelym.qbus.client.common.Handler;
import com.intelym.qbus.client.common.QBusClient;
import com.intelym.qbus.client.packet.event.EventDetails;
import com.intelym.qbus.client.packet.event.QEvent;
import org.json.simple.JSONObject;

/**
 *
 * @author Rajesh
 */
public class TestQBusClientPro implements QEvent {
    private static final IntelymLogger mLog = LoggerFactory.getLogger(TestQBusClientPro.class);
    //private static ClientConfiguration configuration = null;
    private Handler handler;
    
    public TestQBusClientPro(){
        printVersionInfo();
        //initialize();
        startProcessor();           
    }
    
    private void startProcessor() {
        try {
//            handler = QBusClient.GetInstance();
//            handler.setEventHandler(this);
            handler = new QBusClient.QBusClientBuilder("192.168.1.111",8282).setQuickEvent(this).setUserCredentials("d", "d").build();
            if(handler.Connect()){
                mLog.info("Connection Successfuly Initialized");
            } else {
                mLog.info("Connection Failed");
            }
        } catch (Exception e) {
            mLog.error("Error Message :: " + e.getMessage());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TestQBusClientPro();
    }
    
    /**
     * Prints the version info, please update the method on every release.
     */
    private void printVersionInfo(){
        mLog.info("************ Quick Client Bus (messaging extension for Intelym) ********************");
        mLog.info("************ Version "+Constants.MAJOR_VERSION + "." + Constants.MINOR_VERSION + " Build " + Constants.BUILD_VERSION + "  *******");
        mLog.info("Quick Client Bus is initializing");
    }

    @Override
    public void OnConnect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void OnDisconnect(EventDetails details) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void OnError(EventDetails details) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void OnPacketArrived(JSONObject jsonObj) {
        System.out.println("Event OnPacketArrived()");
        handler.send(jsonObj);
        
        System.out.println("Call onDataSend()");
    }

}
