package com.dw_projects.remotecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

import javax.inject.Inject;

public abstract class AbstractRemoteControlActivity extends AppCompatActivity {

    public abstract void refresh();
}
