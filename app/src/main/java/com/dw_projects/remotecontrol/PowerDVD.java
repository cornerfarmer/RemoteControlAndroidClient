package com.dw_projects.remotecontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import javax.inject.Inject;

public class PowerDVD extends AppCompatActivity {

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

        ((RemoteControlApplication) getApplication()).component().inject(this);
        ((RemoteControlApplication) getApplication()).setCurrentActivity(this);


        ((SeekBar)findViewById(R.id.volumeSeekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

                if (fromUser) {
                    powerDVDCommandExecuter.setVolume(progress);
                }
            }
        });
    }

    public void refresh()
    {
        SeekBar volumeSeekBar = (SeekBar)findViewById(R.id.volumeSeekBar);
        volumeSeekBar.setProgress(powerDVDStatus.getVolume());
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


}
