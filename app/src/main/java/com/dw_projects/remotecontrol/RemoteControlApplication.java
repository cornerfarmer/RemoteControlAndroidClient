package com.dw_projects.remotecontrol;

import android.app.Application;
import android.os.Handler;

import dagger.Component;
import javax.inject.Inject;
import javax.inject.Singleton;

public class RemoteControlApplication extends Application {

    @Inject Listener listener;

    protected PowerDVD currentActivity;


    @Singleton
    @Component
    public interface ApplicationComponent {
        void inject(PowerDVD powerDVD);
        void inject(RemoteControlApplication application);
    }
    private ApplicationComponent component;

    @Inject
    public RemoteControlApplication()
    {

    }

    @Override public void onCreate() {
        super.onCreate();

        component = DaggerRemoteControlApplication_ApplicationComponent.builder()
                .build();
        component.inject(this);
        listener.startListening();

        handler.postDelayed(runnable, 1);
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            listener.refresh();
            handler.postDelayed(runnable, 1000);
            if (currentActivity != null)
                currentActivity.refresh();
        }
    };

    public void setCurrentActivity(PowerDVD currentActivity){
        this.currentActivity = currentActivity;
    }

    public ApplicationComponent component() {
        return component;
    }

    public Listener getListener()
    {
        return listener;
    }

}