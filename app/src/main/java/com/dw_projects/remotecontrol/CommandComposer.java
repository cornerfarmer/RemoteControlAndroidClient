package com.dw_projects.remotecontrol;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommandComposer {
    private Command command;
    private int currentArgumentIndex;

    @Inject
    public CommandComposer()
    {

    }

    public String compose(Command command_)
    {
        command = command_;
        return getNamePart() + getArgumentsPart();
    }

    private String getNamePart()
    {
        return command.getAppName() + "_" + command.getName();
    }


    private String getArgumentsPart()
    {
        if (command.hasArguments())
        {
            return "|" + getArguments();
        }
        else
        {
            return "";
        }
    }

    private String getArguments()
    {
        String arguments = "";
        for (currentArgumentIndex = 0; currentArgumentIndex < command.getArguments().length; currentArgumentIndex++)
        {
            arguments += getCurrentArgument();
        }
        return arguments;
    }

    private String getCurrentArgument()
    {
        String argument = command.getArguments()[currentArgumentIndex];
        if (!isLastArgument())
        {
            argument += ":";
        }
        return argument;
    }

    private boolean isLastArgument()
    {
        return currentArgumentIndex == command.getArguments().length - 1;
    }
}
