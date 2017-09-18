/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelym.qbus.client.connectors.tcp;

import com.intelym.client.configuration.ClientConfiguration;
import com.intelym.client.logger.IntelymLogger;
import com.intelym.client.logger.LoggerFactory;
import com.intelym.qbus.client.common.Handler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Rajesh
 */
public class TcpMessenger extends BaseConnector implements Runnable {
    private final static IntelymLogger mLog = LoggerFactory.getLogger(ClientConfiguration.class);
    private Handler handler;
    
    public TcpMessenger(Handler handler) {
        this.handler = handler;
    }
    
    @Override
    public void run() {
        
        try {
                while (isRunning){
                    try{
                        int startOfFram = in.readByte() & 0xFF;
                        
                        if (startOfFram == START_OF_FRAME) {
                            int packetLength = in.readShort();
                            if (packetLength > 0) {
                                String jsonString = in.readChars(packetLength);
                                JSONParser parser = new JSONParser();
                                receivePacket((JSONObject)parser.parse(jsonString));
                            } else {
                                mLog.info("JSON Packet length : " + packetLength);
                            }
                        }
                    } catch(Exception e) {
                        
                    }
                }
        } catch(Exception e) {
            
        }
    }
    
    private void receivePacket(JSONObject jsonObj) {
        handler.onDataArrived(jsonObj);
    }
    
    public void sendPacket(JSONObject jsonObject) {
        try {
            if (jsonObject.containsKey("header") && jsonObject.containsKey("body")) {
                String jsonString = jsonObject.toString();
                out.writeByte(0xFF);
                out.write(jsonString.length());
                out.write(jsonString.getBytes());   
            } else {
                mLog.info("Wrong JSON Packet : " + jsonObject.toString());
            }
            
        } catch(Exception e) {
            mLog.error("Wrong JSON Packet :: " + jsonObject);
        }
    }
}
