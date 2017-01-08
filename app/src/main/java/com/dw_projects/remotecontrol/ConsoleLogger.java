package com.dw_projects.remotecontrol;

import android.util.Log;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ConsoleLogger {

    @Inject
    public ConsoleLogger()
    {

    }

    public void log(String message)
    {
        Log.d("", "[" + (new Date()).toString() + "] " + message);
    }
}
