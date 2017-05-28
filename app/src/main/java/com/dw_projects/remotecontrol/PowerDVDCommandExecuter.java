package com.dw_projects.remotecontrol;

import android.icu.util.Output;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PowerDVDCommandExecuter extends AbstractCommandExecuter {

    @Inject
    public PowerDVDCommandExecuter()
    {

    }

    public void open()
    {
        sendCommand("Open");
    }

    public void up()
    {
        sendCommand("Up");
    }

    public void right()
    {
        sendCommand("Right");
    }

    public void left()
    {
        sendCommand("Left");
    }

    public void down()
    {
        sendCommand("Down");
    }

    public void ok()
    {
        sendCommand("OK");
    }

    public void menu()
    {
        sendCommand("Menu");
    }

    public void playPause()
    {
        sendCommand("PlayPause");
    }

    public void next()
    {
        sendCommand("Next");
    }

    public void prev()
    {
        sendCommand("Prev");
    }

    public void setVolume(int volume)
    {
        String[] arg = {Integer.toString(volume)};
        sendCommand("Vol", arg);
    }

    public void mute()
    {
        sendCommand("SwitchMute");
    }

    @Override
    protected String getAppName() {
        return "PDVD";
    }
}
