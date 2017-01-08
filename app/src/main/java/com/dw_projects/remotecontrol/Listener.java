package com.dw_projects.remotecontrol;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import javax.inject.Singleton;
import javax.inject.Inject;

@Singleton
public class Listener {

    private TCPClient mTcpClient;

    @Inject InputHandler inputHandler;
    @Inject OutputHandler outputHandler;

    @Inject
    public Listener()
    {

    }

    public void startListening()
    {
        new connectTask().execute("");
    }


    public void sendMessagesInQueue()
    {
        try {
            new sendTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, outputHandler.getBufferedOutput());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh()
    {
        sendMessagesInQueue();
    }

    public class sendTask extends AsyncTask<String,String,TCPClient> {

        @Override
        protected TCPClient doInBackground(String... message) {
            mTcpClient.sendMessage(message[0]);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }


    public class connectTask extends AsyncTask<String,String,TCPClient> {

        @Override
        protected TCPClient doInBackground(String... message) {

            //we create a TCPClient object and
            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mTcpClient.run();

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
