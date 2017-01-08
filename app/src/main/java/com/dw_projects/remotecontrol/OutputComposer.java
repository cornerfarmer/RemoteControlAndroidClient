package com.dw_projects.remotecontrol;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OutputComposer {
    private String output;

    @Inject
    public OutputComposer()
    {

    }

    public String compose(List<String> commandStrings) throws Exception {
        output = "";
        addCommandStrings(commandStrings);
        addOutputEnd();
        return output;
    }

    private void addCommandStrings(List<String> commandStrings) throws Exception {
        for (String commandString : commandStrings)
        {
            addCommandString(commandString);
            addCommandSeparator();
        }
    }

    private void addCommandString(String commandString) throws Exception {
        if (commandString.contains(";"))
            throw new Exception("The commandString must not contain a ';'! (" + commandString + ")");
        output += commandString;
    }

    private void addCommandSeparator()
    {
        output += ",";
    }

    private void addOutputEnd()
    {
        output += ";";
    }
}
