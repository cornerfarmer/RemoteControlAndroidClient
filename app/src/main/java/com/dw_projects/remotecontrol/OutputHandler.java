package com.dw_projects.remotecontrol;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OutputHandler {
    public List<String> buffer;
    @Inject OutputComposer outputComposer;
    @Inject CommandComposer commandComposer;
    @Inject ConsoleLogger logger;

    @Inject
    public OutputHandler()
    {
        buffer = new ArrayList<String>();
    }

    public String getBufferedOutput() throws Exception {
        String output = outputComposer.compose(buffer);
        buffer.clear();
        return output;
    }

    public void addOutputCommand(Command command)
    {
        logger.log("Sended command: " + command);
        String commandString = commandComposer.compose(command);
        buffer.add(commandString);
    }
}
