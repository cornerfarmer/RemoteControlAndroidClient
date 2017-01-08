package com.dw_projects.remotecontrol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class StatusTargetEntry {
    private AbstractStatus targetObject;
    private Method targetMethod;
    private StatusRegistration targetInfo;
    private Class<?>[] arguments;

    public StatusTargetEntry(AbstractStatus targetObject_, Method targetMethod_, StatusRegistration targetInfo_)
    {
        targetObject = targetObject_;
        targetMethod = targetMethod_;
        targetInfo = targetInfo_;
        arguments = targetMethod.getParameterTypes();
    }

    public Object execute(Command command) throws InvocationTargetException, IllegalAccessException {
        Object[] convertedArguments = new Object[arguments.length];
        for (int i = 0; i < arguments.length; i++)
        {
            if (arguments[i].equals(boolean.class))
                convertedArguments[i] = Boolean.parseBoolean(command.getArguments()[i]);
            else if (arguments[i].equals(int.class))
                convertedArguments[i] = Integer.parseInt(command.getArguments()[i]);
        }
        return targetMethod.invoke(targetObject, convertedArguments);
    }

    public String getStatusName()
    {
        return targetInfo.name();
    }

    public String getAppName()
    {
        return targetObject.getAppName();
    }

    public boolean matchesCommand(Command command)
    {
        if (!Objects.equals(targetInfo.name(), command.getName()))
            return false;

        if (arguments.length != command.getArguments().length)
            return false;

        for (int i = 0; i < arguments.length; i++)
        {
            try
            {
                if (arguments[i].equals(boolean.class))
                    Boolean.parseBoolean(command.getArguments()[i]);
                else if (arguments[i].equals(int.class))
                    Integer.parseInt(command.getArguments()[i]);
            }
            catch (Exception e)
            {
                return false;
            }
        }

        return true;
    }
}
