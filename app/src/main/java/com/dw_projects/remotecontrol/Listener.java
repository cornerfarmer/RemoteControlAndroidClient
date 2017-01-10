package com.dw_projects.remotecontrol;

import android.os.AsyncTask;

import javax.inject.Singleton;
import javax.inject.Inject;

@Singleton
public class Listener {

    private TCPClient tcpClient;
    public String serverIp = "192.168.178.56";

    @Inject InputHandler inputHandler;
    @Inject OutputHandler outputHandler;

    @Inject
    public Listener()
    {

    }

    public void startListening()
    {
        if (isStopped()) {
            new connectTask().execute("");
        }
    }

    public boolean isConnected()
    {
        return tcpClient != null && tcpClient.getStatus() == 2;
    }

    public boolean isRetrying()
    {
        return tcpClient != null && tcpClient.getStatus() == 1;
    }

    public boolean isStopped()
    {
        return tcpClient == null || tcpClient.getStatus() == 0;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }


    public class connectTask extends AsyncTask<String,String,TCPClient> {

        @Override
        protected TCPClient doInBackground(String... message) {

            //we create a TCPClient object and
            tcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            }, outputHandler);
            tcpClient.run(serverIp);

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            try {
                inputHandler.handleInput(values[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
