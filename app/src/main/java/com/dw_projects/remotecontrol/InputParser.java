package com.dw_projects.remotecontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InputParser {

    private List<String> commandStrings;

    @Inject
    public InputParser()
    {

    }

    public List<String> parse(String input) throws Exception {
        commandStrings = new ArrayList<String>();

        input = validateInputTermination(input);
        parseCommandStrings(input);

        return commandStrings;
    }


    private String validateInputTermination(String input) throws Exception {
        if (input.length() == 0 || !input.endsWith(";"))
        throw new Exception("The given input '" + input + "' does not terminate correctly!");
        return input.substring(0, input.length() - 1);
    }



    private void parseCommandStrings(String input) throws Exception {
        while (isCommandLeft(input))
        {
            addNextCommandString(input);
            input = proceedToNextCommandString(input);
        }
    }

    private boolean isCommandLeft(String input)
    {
        return !Objects.equals(input, "");
    }

    private void addNextCommandString(String input) throws Exception {
        String commandString = getNextCommandString(input);
        if (commandString.length() > 0)
        {
            commandStrings.add(commandString);
        }
    }

    private String getNextCommandString(String input) throws Exception {
        if (!input.contains(","))
            throw new Exception("The given input '" + input + "' does not terminate correctly!");
        return input.substring(0, input.indexOf(","));
    }

    private String proceedToNextCommandString(String input)
    {
        return input.substring(input.indexOf(",") + 1);
    }
}
