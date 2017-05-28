package com.dw_projects.remotecontrol;

import javax.inject.Inject;

public abstract class AbstractCommandExecuter {
    @Inject OutputHandler outputHandler;
    @Inject Listener listener;

    protected void sendCommand(String name)
    {
        try {
            outputHandler.addOutputCommand(new Command(getAppName(), name));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void sendCommand(String name, String[] arguments)
    {
        try {
            outputHandler.addOutputCommand(new Command(getAppName(), name, arguments));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract String getAppName();
}
