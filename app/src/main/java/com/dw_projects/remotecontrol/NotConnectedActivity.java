package com.dw_projects.remotecontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import javax.inject.Inject;

public class NotConnectedActivity extends AbstractRemoteControlActivity {

    @Inject Listener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_connected);

        ((RemoteControlApplication) getApplication()).component().inject(this);
        ((RemoteControlApplication) getApplication()).setCurrentActivity(this);

        ((EditText)findViewById(R.id.IpField)).setText(listener.getServerIp());
    }

    public void refresh() {
        if (listener.isConnected()) {
            Intent myIntent = new Intent(this, PowerDVDNotOpenActivity.class);
            startActivity(myIntent);
        } else if (listener.isStopped()) {
            listener.startListening();
        }
    }

    public void tryReconnect() {
        listener.startListening();
    }
}
