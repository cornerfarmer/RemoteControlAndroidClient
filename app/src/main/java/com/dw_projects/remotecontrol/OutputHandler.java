package com.dw_projects.remotecontrol;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.Semaphore;

@Singleton
public class OutputHandler {
    public List<String> buffer;
    @Inject OutputComposer outputComposer;
    @Inject CommandComposer commandComposer;
    @Inject ConsoleLogger logger;

    private Semaphore bufferLock;

    @Inject
    public OutputHandler()
    {
        buffer = new ArrayList<String>();
        bufferLock = new Semaphore(1, true);
    }

    public String getBufferedOutput() throws Exception
    {
        bufferLock.acquire();
        String output = outputComposer.compose(buffer);
        buffer.clear();
        bufferLock.release();

        return output;
    }

    public void addOutputCommand(Command command) throws InterruptedException
    {
        logger.log("Sended command: " + command);
        String commandString = commandComposer.compose(command);
        bufferLock.acquire();
        buffer.add(commandString);
        bufferLock.release();
    }
}
