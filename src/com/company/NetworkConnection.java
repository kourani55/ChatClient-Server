package com.company;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class NetworkConnection {

    private ConnectionThread connThread = new ConnectionThread();

    //private Consumer for when message is recieved
    private Consumer<Serializable> onRecieveCallBack;

    //takes message
    public NetworkConnection(Consumer<Serializable> onRecieveCallback) {
        this.onRecieveCallBack = onRecieveCallback;
        connThread.setDaemon(true);
    }

    //starts connection
    public void startConnection() throws Exception {
        connThread.start();
    }

    //sends data from server to client
    public void send(Serializable data) throws Exception {
        connThread.out.writeObject(data);
    }

    //closes connection
    public void closeConnection() throws Exception {
        connThread.socket.close();
    }

    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract int getPort();

    //connection thread for server and client
    private class ConnectionThread extends Thread {

        //creates private socket
        private Socket socket;
        private ObjectOutputStream out;

        //overrides
        @Override
        public void run() {
            //try-catch
            try (ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                 //creates socket
                 Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort());
                 //outputsteam to socket
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 //inputstream to socket
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);

                //while loop
                while (true) {
                    Serializable data = (Serializable) in.readObject();
                    onRecieveCallBack.accept(data);
                }
            }
            catch(Exception e) {
                onRecieveCallBack.accept("Connection closed");
            }
        }
    }
}//end abstract class
