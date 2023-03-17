package com.company;

import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends NetworkConnection{

    //creates port
    private int port;

    //public server
    public Server(int port, Consumer<Serializable> onRecieveCallback) {
        super(onRecieveCallback);
        this.port = port;
    }
    //override
    @Override
    //boolean isServer to return true
    protected boolean isServer() {
        return true;
    }

    //override
    @Override
    //String getIP returns nothing
    protected String getIP() {
        return null;
    }

    //override
    @Override
    //returns port
    protected int getPort() {
        return port;
    }

}//end class
