package com.dw_projects.remotecontrol;

/**
 * Created by Dominik on 07.01.2017.
 */

public class Command {
    private String name;
    private String appName;
    private String[] arguments;

    public Command()
    {
        appName = "";
        name = "";
        arguments = new String[0];
    }

    public Command(String appName_, String name_)
    {
        appName = appName_;
        name = name_;
        arguments = new String[0];
    }

    public Command(String appName_, String name_, String[] arguments_)
    {
        appName = appName_;
        name = name_;
        arguments = arguments_;
    }

    public String getName()
    {
        return name;
    }
    public String getAppName()
    {
        return appName;
    }

    public String[] getArguments()
    {
        return arguments;
    }

    public boolean hasArguments()
    {
        return arguments.length != 0;
    }

    public String toString()
    {
        return appName + "_" + name + "(" + arguments.toString() + ")";
    }
}
