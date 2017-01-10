package com.dw_projects.remotecontrol;

import android.os.SystemClock;
import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class TCPClient {

    private String serverMessage;//your computer IP address
    public static final int SERVERPORT = 10;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
    private int status = 0;

    PrintWriter out;
    BufferedReader in;
    OutputHandler outputHandler;

    /**
     *  Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TCPClient(OnMessageReceived listener, OutputHandler outputHandler) {
        mMessageListener = listener;
        this.outputHandler = outputHandler;
    }

    /**
     * Sends the message entered by client to the server
     * @param message text entered by client
     */
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            out.print(message);
            out.flush();
        }
    }

    public int getStatus() {
        return status;
    }

    public void stopClient(){
        mRun = false;
    }

    public void run(String serverIp) {

        status = 1;

        mRun = true;

        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(serverIp);

            Log.e("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVERPORT);

            try {
                status = 2;
                //send the message to the server
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                Log.e("TCP Client", "C: Sent.");

                Log.e("TCP Client", "C: Done.");

                //receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    SystemClock.sleep(10);
                    sendMessage(outputHandler.getBufferedOutput());


                    char[] buffer = new char[100];
                    String message = "";
                    int len;
                    do {
                        len = in.read(buffer);
                        message += String.copyValueOf(buffer, 0, len);
                    } while (in.ready());

                    if (!Objects.equals(message, "")) {
                        //call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(message);
                    }
                    serverMessage = null;

                }

                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");

            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }

        status = 0;

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }

}
