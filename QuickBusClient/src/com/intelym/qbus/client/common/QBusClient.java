/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelym.qbus.client.common;

import com.intelym.client.logger.IntelymLogger;
import com.intelym.client.logger.LoggerFactory;
import com.intelym.qbus.client.connectors.tcp.TcpMessenger;
import com.intelym.qbus.client.packet.event.EventDetails;
import com.intelym.qbus.client.packet.event.QEvent;
import org.json.simple.JSONObject;

/**
 *
 * @author Rajesh
 */
public class QBusClient implements Handler {

    private TcpMessenger tcpMessenger = null;
    //private static Handler mData;
    private QEvent qEvent;
    private String address;
    private int port;
    private String username;
    private String password;
    private final static IntelymLogger mLog = LoggerFactory.getLogger(QBusClient.class);
    
    private QBusClient(QBusClientBuilder builder) {
        this.qEvent = builder.qEvent;
        this.address = builder.address;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
    }
    
//    public static Handler GetInstance()
//    {
//        if (mData == null)
//        {
//            mData = new QBusClient();
//        }
//        return mData;
//    }
    
    @Override
    public boolean Connect() {
        try
            {
                this.tcpMessenger = new TcpMessenger(this.qEvent);
                this.tcpMessenger.setAddress(this.address);
                this.tcpMessenger.setPort(this.port);
                this.tcpMessenger.connect();
                new Thread(this.tcpMessenger, Constants.Q_BUS_CLIENT).start();
                return true;

            }catch(Exception e)
            {
                mLog.error("Connection fail :: " + e.getMessage());
                return false;
            }
    }

    @Override
    public boolean Disconnect() {
        return this.tcpMessenger.close();
    }

//    @Override
//    public void OnError(EventDetails eDetails) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void OnDisconnect(EventDetails eDetails) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public void SetEnableLogging(String logPath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//    
//    @Override
//    public void setEventHandler(QEvent qEvent) {
//        this.quickEvent = qEvent;
//    }
    
//    @Override
//    public void onDataArrived(JSONObject jsonObj) {
//        qEvent.OnPacketArrived(jsonObj);
//    }

    @Override
    public void send(JSONObject jsonObj) {
        this.tcpMessenger.sendPacket(jsonObj);
    }
    
    public static class QBusClientBuilder {
        private QEvent qEvent;
        private String address;
        private int port;
        private String username;
        private String password;
        
        public QBusClientBuilder(String address, int port) {
            this.address = address;
            this.port = port;
        }
        
        public QBusClientBuilder setPort() {
            
            return this;
        }
        
        public QBusClientBuilder setQuickEvent(QEvent qEvent) {
            this.qEvent = qEvent;
            return this;
        }
        
        public QBusClientBuilder setUserCredentials(String username, String password) {
            this.username = username;
            this.password = password;
            return this;
        }
        
        public QBusClient build() {
            QBusClient qBusClient = new QBusClient(this);
            validateQBusClientObj(qBusClient);
            return qBusClient;
        }
        
        private void validateQBusClientObj(QBusClient qBusClient) {
            //Do some basic validations to check
            //if user object does not break any assumption of system
            if (qBusClient.qEvent == null) {
                mLog.error("quickEvent is required field.");
            }
            
            if (this.username != null && !this.username.isEmpty()) {
                mLog.error("User Name can not be blank or null.");
            }
            
            if (this.password != null && !this.password.isEmpty()) {
                mLog.error("Password can not be blank or null.");
            }
        }
    }
}
