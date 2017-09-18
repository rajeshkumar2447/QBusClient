/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelym.qbus.client.packet.event;

import org.json.simple.JSONObject;

/**
 *
 * @author Rajesh
 */
public interface QEvent {
    // raise this in the event of successful connection with Quick Server
    void OnConnect();
    // raise this in the event on forceful disconnection
    void OnDisconnect(EventDetails details);
    // raise this in the event of any error, implied disconnection also
    void OnError(EventDetails details);
    // raise this when single packet arrives
    void OnPacketArrived(JSONObject jsonObj);
}
