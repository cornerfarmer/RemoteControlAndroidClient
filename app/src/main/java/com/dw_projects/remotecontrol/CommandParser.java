package com.dw_projects.remotecontrol;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommandParser {
    private String[] commandParts;
    private String commandString;
    private String commandTitle;

    @Inject
    CommandParser()
    {

    }

    public Command parseCommand(String commandString_) throws Exception {
        commandString = commandString_;
        validateCommandString();
        if (commandHasArguments())
            return parseCommandWithArguments();
        else
            return parseCommandWithoutArguments();
    }

    private void validateCommandString() throws Exception {
        if (commandString.length() == 0)
        {
            throw new Exception("The given commandString is empty.");
        }
    }

    private boolean commandHasArguments()
    {
        return commandString.contains("|");
    }

    private Command parseCommandWithArguments() throws Exception {
        computeCommandParts();
        commandTitle = getNameOfCommandWithArguments();
        return new Command(getAppName(), getCommandName(), getArgumentsOfCommandWithArguments());
    }

    public String getAppName() throws Exception {
        if (!commandTitle.contains("_"))
        {
            throw new Exception("The command title '" + commandTitle + "' does not contain any app name.");
        }
        return commandTitle.substring(0, commandTitle.indexOf("_"));
    }

    public String getCommandName() throws Exception {
        if (!commandTitle.contains("_"))
        {
            throw new Exception("The command title '" + commandTitle + "' does not contain any command name.");
        }
        return commandTitle.substring(commandTitle.indexOf("_") + 1);
    }

    private void computeCommandParts() throws Exception {
        commandParts = commandString.split("\\|");
        if (commandParts.length != 2)
        {
            throw new Exception("The command '" + commandString + "' contains more than one |.");
        }
    }

    private String getNameOfCommandWithArguments() throws Exception {
        if (commandParts[0].length() == 0)
        {
            throw new Exception("The given name of the given commandString '" + commandString + "' is empty.");
        }
        return commandParts[0];
    }


    private String[] getArgumentsOfCommandWithArguments() throws Exception {
        String[] arguments = splitArgumentString();
        validateArguments(arguments);
        return arguments;
    }

    private String[] splitArgumentString()
    {
        if (commandParts[1].contains(":"))
            return commandParts[1].split(":");
        else if (commandParts[1].length() > 0)
            return new String[] { commandParts[1] };
        else
            return new String[0];
    }

    private void validateArguments(String[] arguments) throws Exception {
        for (String argument : arguments)
        {
            if (argument.length() == 0)
            {
                throw new Exception("At least one argument of the given commandString '" + commandString + "' is empty.");
            }
        }
    }

    private Command parseCommandWithoutArguments() throws Exception {
        commandTitle = commandString;
        return new Command(getAppName(), getCommandName());
    }
}
