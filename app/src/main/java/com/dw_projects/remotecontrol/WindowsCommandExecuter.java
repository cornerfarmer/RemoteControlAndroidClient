package com.dw_projects.remotecontrol;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WindowsCommandExecuter extends AbstractCommandExecuter {

    @Inject
    public WindowsCommandExecuter()
    {

    }

    public void mouseMove(int dx, int dy)
    {
        String[] arg = {Integer.toString(dx), Integer.toString(dy)};
        sendCommand("MouseMove", arg);
    }

    public void mouseLeftDown()
    {
        sendCommand("MouseLeftDown");
    }

    public void mouseLeftUp()
    {
        sendCommand("MouseLeftUp");
    }

    public void mouseRightDown()
    {
        sendCommand("MouseRightDown");
    }

    public void mouseRightUp()
    {
        sendCommand("MouseRightUp");
    }

    public void keyDown(int keyCode)
    {
        String[] arg = {Integer.toString(keyCode)};
        sendCommand("KeyDown", arg);
    }

    public void keyUp(int keyCode)
    {
        String[] arg = {Integer.toString(keyCode)};
        sendCommand("KeyUp", arg);
    }

    @Override
    protected String getAppName() {
        return "MK";
    }
}
