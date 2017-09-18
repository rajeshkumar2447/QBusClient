/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelym.qbus.client.common;

import com.intelym.qbus.client.packet.event.EventDetails;
import com.intelym.qbus.client.packet.event.QEvent;
import org.json.simple.JSONObject;

/**
 *
 * @author Rajesh
 */
public interface Handler {
    void setEventHandler(QEvent qEvent);
    void SetUserCredentials(String username, String password);
    boolean Connect(String address, int port);
    boolean Disconnect();
    void OnError(EventDetails eDetails);
    void OnDisconnect(EventDetails eDetails);
    void SetEnableLogging(String logPath);
    void onDataArrived(JSONObject jsonObj);
    void onDataSend(JSONObject jsonObj);
}
