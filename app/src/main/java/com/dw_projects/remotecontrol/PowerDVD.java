package com.dw_projects.remotecontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

import javax.inject.Inject;

public class PowerDVD extends AbstractRemoteControlActivity {

    @Inject PowerDVDCommandExecuter powerDVDCommandExecuter;
    @Inject PowerDVDStatus powerDVDStatus;

    @Inject
    public PowerDVD()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_dvd);

        createNavBar();

        ((RemoteControlApplication) getApplication()).component().inject(this);
        ((RemoteControlApplication) getApplication()).setCurrentActivity(this);


        ((SeekArc)findViewById(R.id.volumeSeekBar)).setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekArc seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekArc seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

                if (fromUser) {
                    powerDVDCommandExecuter.setVolume(progress);
                    ((TextView)findViewById(R.id.volumeTextView)).setText(Integer.toString(progress));
                }
            }
        });

        refresh();
    }

    public void refresh()
    {
        if (!powerDVDStatus.isOpened()) {
            Intent myIntent = new Intent(this, PowerDVDNotOpenActivity.class);
            startActivity(myIntent);
        }

        SeekArc volumeSeekBar = (SeekArc)findViewById(R.id.volumeSeekBar);
        volumeSeekBar.setProgress(powerDVDStatus.getVolume());

        TextView volumeText = (TextView)findViewById(R.id.volumeTextView);
        volumeText.setText(Integer.toString(powerDVDStatus.getVolume()));

        volumeSeekBar.setEnabled(!powerDVDStatus.isMuted());
       //volumeText.setEnabled(!powerDVDStatus.isMuted());
    }

    public void open(View view)
    {
        powerDVDCommandExecuter.open();
    }

    public void up(View view)
    {
        powerDVDCommandExecuter.up();
    }

    public void down(View view)
    {
        powerDVDCommandExecuter.down();
    }

    public void left(View view)
    {
        powerDVDCommandExecuter.left();
    }

    public void right(View view)
    {
        powerDVDCommandExecuter.right();
    }

    public void menu(View view)
    {
        powerDVDCommandExecuter.menu();
    }

    public void playPause(View view)
    {
        powerDVDCommandExecuter.playPause();
    }

    public void next(View view)
    {
        powerDVDCommandExecuter.next();
    }

    public void prev(View view)
    {
        powerDVDCommandExecuter.prev();
    }

    public void ok(View view)
    {
        powerDVDCommandExecuter.ok();
    }

    public void mute(View view)
    {
        powerDVDCommandExecuter.mute();
    }


}
