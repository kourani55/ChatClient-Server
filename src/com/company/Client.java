package com.company;

import java.io.Serializable;
import java.util.function.Consumer;

public class Client extends NetworkConnection{

    private String ip;
    private int port;

    public Client(String ip, int port, Consumer<Serializable> onRecieveCallback) {
        super(onRecieveCallback);
        this.ip = ip;
        this.port = port;
    }

    //override
    @Override
    //protected isServer returns
    protected boolean isServer() {
        return false;
    }

    //override
    @Override
    //protected string IP returns IP
    protected String getIP() {
        return ip;
    }

    //override
    @Override
    //protected getPort returns port
    protected int getPort() {
        return port;
    }

}//end class
