/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelym.qbus.client.connectors.tcp;

import com.intelym.client.logger.IntelymLogger;
import com.intelym.client.logger.LoggerFactory;
import com.intelym.client.utilies.BinaryInputStream;
import com.intelym.client.utilies.BinaryOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Rajesh
 */
public abstract class BaseConnector {
    private final IntelymLogger mLog = LoggerFactory.getLogger(BaseConnector.class);
    protected Socket clientSocket = null;
    protected InetAddress address = null;
    protected int port = -1;
    protected int timeout = 5000;
    protected BinaryInputStream in = null;
    protected BinaryOutputStream out = null;   
    public boolean isRunning = false;
    protected Thread clientReceiverThread;
    protected static final int MAX_ATTEMPT_RECONNECT = Integer.MAX_VALUE;
    protected AtomicInteger CURRENT_ATTEMPT_RECONNECT = new AtomicInteger(0);
    private static long TIME_BETWEEN_RECONNECT = 30000;
    protected final int START_OF_FRAME = 0xFF;
    
    public BaseConnector(){
        
    }
    
    public void setAddress(String ipAddress) throws UnknownHostException {
        this.address = InetAddress.getByName(ipAddress);
    }
    
    public void setPort(int port){
        this.port = port;
    }
    
    /**
     * generic connection implementation over TCP/IP socket
     * gets the binary streams for data process
     * @return 
     */
    public final boolean connect(){
        try{
            
            CURRENT_ATTEMPT_RECONNECT.incrementAndGet();
            if(clientSocket != null && clientSocket.isConnected())
                return true;
            mLog.info("Attempting socket connect to " + address + ":" + port);
            clientSocket = new Socket(address, port);
            mLog.info("Socket connected succesfully to " + address + ":" + port);
            clientSocket.setSoTimeout(timeout);
            clientSocket.setTcpNoDelay(true);
            clientSocket.setKeepAlive(true);
            //clientSocket.setSoLinger(true, 1);
            mLog.info("Socket established with time out of " + timeout + " milliseconds and TcpNoDelay as true");
            in = new BinaryInputStream(clientSocket.getInputStream());
            out = new BinaryOutputStream(clientSocket.getOutputStream());
            mLog.info("Streams are open for communication... with " + address +":" + port);
            isRunning = true;
            CURRENT_ATTEMPT_RECONNECT.getAndSet(0);
            return clientSocket.isConnected();
        }catch(IOException  e){
            mLog.error("Error connecting to " + address + ":" + port + " , due to " + e.getMessage() + " :: Exception Name :: " + e.getClass().getCanonicalName());
            if(mLog.isDebugEnabled())
                mLog.debug(e);
            else
                mLog.error("for detailed messages enable debug option in Log4j.properties");
            return false;
        }
    }
    
    /**
     * reconnects the socket on broadcast channel. do you wanna call this first time? lets worry about that later
     * @return true if successful socket connect
     * @throws InterruptedException 
     */
    public boolean reconnect() throws InterruptedException{
        while(CURRENT_ATTEMPT_RECONNECT.get() < MAX_ATTEMPT_RECONNECT){
            mLog.info("Attempting reconnect on socket channel, Attempt : " + CURRENT_ATTEMPT_RECONNECT.get());
            try{
                if (clientSocket != null) {
                clientSocket.close();
                in = null;
                out = null;
                clientSocket = null;
                }
            } catch(Exception e){
                return false;
            }
            boolean isConnected = connect();
            if(isConnected) { return isConnected; }
            else Thread.sleep(TIME_BETWEEN_RECONNECT);
        }
        return Boolean.FALSE;
    }
    
    /**
     * closes the socket and its dependence cleanly. 
     * @return true if successful
     */
    public final boolean close(){
        try{
           
            mLog.info("Closing socket channel gratiously.. clean the garbage");
            isRunning = Boolean.FALSE;
            if(clientSocket != null && clientSocket.isConnected())
                clientSocket.close();
            in = null;
            out = null;
            clientSocket = null;
            
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
}
