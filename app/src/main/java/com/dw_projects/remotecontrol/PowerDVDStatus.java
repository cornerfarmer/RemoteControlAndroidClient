package com.dw_projects.remotecontrol;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PowerDVDStatus extends AbstractStatus {

    private boolean opened;
    private boolean muted;
    private int volume;

    @Inject
    public PowerDVDStatus()
    {

    }

    @StatusRegistration(name = "Open")
    public void setOpened(boolean opened)
    {
        this.opened = opened;
    }

    @StatusRegistration(name = "Vol")
    public void setVolume(int volume)
    {
        this.volume = volume;
    }

    @StatusRegistration(name = "Mute")
    public void setMuted(boolean muted)
    {
        this.muted = muted;
    }

    public String getAppName()
    {
        return "PDVD";
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isMuted() {
        return muted;
    }

    public int getVolume() {
        return volume;
    }
}
