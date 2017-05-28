package com.dw_projects.remotecontrol;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

import java.io.Console;

import javax.inject.Inject;

public abstract class AbstractRemoteControlActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Class[] activities = {PowerDVDNotOpenActivity.class, WindowsActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void createNavBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Toolbar.LayoutParams params = new Toolbar.LayoutParams(130, Toolbar.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd(20);
        params.setMarginStart(20);
        params.gravity = Gravity.END;

        Button goNextAppButton = new Button(this);
        goNextAppButton.setLayoutParams(params);
        goNextAppButton.setBackgroundResource(R.drawable.ic_keyboard_arrow_right_black_24dp);
        goNextAppButton.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 255, 255, 255)));
        goNextAppButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startNextActivity(1);
            }
        });
        toolbar.addView(goNextAppButton);

        Button goPrevAppButton = new Button(this);
        goPrevAppButton.setLayoutParams(params);
        goPrevAppButton.setBackgroundResource(R.drawable.ic_keyboard_arrow_left_black_24dp);
        goPrevAppButton.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 255, 255, 255)));
        goPrevAppButton.setPadding(0, 0, 500, 0);
        goPrevAppButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startNextActivity(-1);
            }
        });
        toolbar.addView(goPrevAppButton);
    }


    private void startNextActivity(int offset)
    {
        int current = 0;
        for (int i = 0; i < activities.length; i++)
        {
            if (activities[i].isInstance(this))
                current = i;
        }

        int next = current + offset;
        if (next < 0)
            next += activities.length;
        next %= activities.length;

        Intent myIntent = new Intent(this, activities[next]);
        startActivity(myIntent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_power_dvd) {
            Intent myIntent = new Intent(this, PowerDVDNotOpenActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_windows) {
            Intent myIntent = new Intent(this, WindowsActivity.class);
            startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public abstract void refresh();
}
