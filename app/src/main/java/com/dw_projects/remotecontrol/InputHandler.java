package com.dw_projects.remotecontrol;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InputHandler {

    @Inject CommandParser commandParser;

    @Inject InputParser inputParser;

    @Inject StatusAdjuster statusAdjuster;

    @Inject ConsoleLogger logger;

    @Inject
    InputHandler()
    {

    }

    public void handleInput(String input) throws Exception {
        List<String> commandStrings = inputParser.parse(input);
        for (String commandString : commandStrings)
        {
            handleCommandString(commandString);
        }
    }

    private void handleCommandString(String commandString) throws Exception {
        handleCommand(commandParser.parseCommand(commandString));
    }

    private void handleCommand(Command command)
    {
        logger.log("Recieved command: " + command);
        statusAdjuster.setStatus(command);
    }
}
