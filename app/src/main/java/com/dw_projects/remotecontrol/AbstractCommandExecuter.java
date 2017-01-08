package com.dw_projects.remotecontrol;

import javax.inject.Inject;

public abstract class AbstractCommandExecuter {
    @Inject OutputHandler outputHandler;
    @Inject Listener listener;

    protected void sendCommand(String name)
    {
        outputHandler.addOutputCommand(new Command(getAppName(), name));
    }

    protected void sendCommand(String name, String[] arguments)
    {
        outputHandler.addOutputCommand(new Command(getAppName(), name, arguments));
    }

    protected abstract String getAppName();
}
