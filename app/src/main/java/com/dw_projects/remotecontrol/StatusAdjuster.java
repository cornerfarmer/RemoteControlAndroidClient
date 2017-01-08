package com.dw_projects.remotecontrol;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class StatusAdjuster {
    AbstractStatus[] statuses;
    List<StatusTargetEntry> statusMethods;

    @Inject
    public StatusAdjuster(PowerDVDStatus powerDVDStatus)
    {
        statuses = new AbstractStatus[1];
        statuses[0] = powerDVDStatus;
        collectTargetMethods();
    }

    private void collectTargetMethods()
    {
        statusMethods = new ArrayList<StatusTargetEntry>();
        for (AbstractStatus status : statuses)
        {
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(status.getClass().getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(StatusRegistration.class)) {
                    StatusRegistration annotation = method.getAnnotation(StatusRegistration.class);

                    statusMethods.add(new StatusTargetEntry(status, method, annotation));
                }
            }
        }
    }

    public void setStatus(Command command)
    {
        for (StatusTargetEntry entry : statusMethods)
        {
            if (entry.matchesCommand(command))
            {
                try {
                    entry.execute(command);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
