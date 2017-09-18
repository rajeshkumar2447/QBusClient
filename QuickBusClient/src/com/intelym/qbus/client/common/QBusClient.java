/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelym.qbus.client.common;

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
    private static Handler mData;
    private QEvent quickEvent;
    
    public static Handler GetInstance()
    {
        if (mData == null)
        {
            mData = new QBusClient();
        }
        return mData;
    }
    
    @Override
    public boolean Connect(String address, int port) {
        try
            {
                this.tcpMessenger = new TcpMessenger(this);
                this.tcpMessenger.setAddress(address);
                this.tcpMessenger.setPort(port);
                this.tcpMessenger.connect();
                new Thread(this.tcpMessenger, "QbusClient").start();
                return true;

            }catch(Exception e)
            {
                return false;
            }
    }

    @Override
    public boolean Disconnect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void OnError(EventDetails eDetails) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void OnDisconnect(EventDetails eDetails) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetEnableLogging(String logPath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setEventHandler(QEvent qEvent) {
        this.quickEvent = qEvent;
    }

    @Override
    public void SetUserCredentials(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDataArrived(JSONObject jsonObj) {
        this.tcpMessenger.sendPacket(jsonObj);
    }

    @Override
    public void onDataSend(JSONObject jsonObj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
