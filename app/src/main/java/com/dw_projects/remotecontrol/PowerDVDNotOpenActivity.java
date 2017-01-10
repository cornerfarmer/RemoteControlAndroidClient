package com.dw_projects.remotecontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

import javax.inject.Inject;

public class PowerDVDNotOpenActivity extends AbstractRemoteControlActivity {

    @Inject PowerDVDCommandExecuter powerDVDCommandExecuter;
    @Inject PowerDVDStatus powerDVDStatus;

    @Inject
    public PowerDVDNotOpenActivity()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_dvdnot_open);

        createNavBar();

        ((RemoteControlApplication) getApplication()).component().inject(this);
        ((RemoteControlApplication) getApplication()).setCurrentActivity(this);

        refresh();
    }

    public void open(View view)
    {
        powerDVDCommandExecuter.open();
    }


    public void refresh() {
        if (powerDVDStatus.isOpened()) {
            Intent myIntent = new Intent(this, PowerDVD.class);
            startActivity(myIntent);
        }
    }
}
