package com.dw_projects.remotecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

import javax.inject.Inject;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class WindowsActivity extends AbstractRemoteControlActivity {
    private ScaleGestureDetector mScaleDetector;
    private float mLastTouchX;
    private float mLastTouchY;
    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;
    private long startClickTime;
    private long lastClickTime;
    private boolean dragging;
    private Handler handler = new Handler();

    @Inject WindowsCommandExecuter windowsCommandExecuter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windows);

        createNavBar();

        ((RemoteControlApplication) getApplication()).component().inject(this);
        ((RemoteControlApplication) getApplication()).setCurrentActivity(this);

        mScaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener());

        View myView = findViewById(R.id.content_windows);
        myView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent ev) {

                // Let the ScaleGestureDetector inspect all events.
                mScaleDetector.onTouchEvent(ev);

                final int action = MotionEventCompat.getActionMasked(ev);

                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        if (startClickTime - lastClickTime < 200) {
                            windowsCommandExecuter.mouseLeftDown();
                            dragging = true;
                        }

                        // Remember where we started (for dragging)
                        mLastTouchX = x;
                        mLastTouchY = y;
                        // Save the ID of this pointer (for dragging)
                        mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        // Find the index of the active pointer and fetch its position
                        final int pointerIndex =
                                MotionEventCompat.findPointerIndex(ev, mActivePointerId);

                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);

                        // Calculate the distance moved
                        final float dx = x - mLastTouchX;
                        final float dy = y - mLastTouchY;

                        windowsCommandExecuter.mouseMove((int)dx / 2, (int)dy / 2);

                        // Remember this touch position for the next move event
                        mLastTouchX = x;
                        mLastTouchY = y;

                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        if (dragging) {
                            windowsCommandExecuter.mouseLeftUp();
                            dragging = false;
                        } else if (mActivePointerId != INVALID_POINTER_ID) {
                            long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                            if (clickDuration < 200) {
                                handler.postDelayed(click, 250);
                                lastClickTime = Calendar.getInstance().getTimeInMillis();
                            }
                        }

                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_POINTER_UP: {

                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

                        if (pointerId == mActivePointerId) {
                            // This was our active pointer going up. Choose a new
                            // active pointer and adjust accordingly.
                            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                            mLastTouchX = MotionEventCompat.getX(ev, newPointerIndex);
                            mLastTouchY = MotionEventCompat.getY(ev, newPointerIndex);
                            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                        }
                        break;
                    }
                }
                return true;
            }
        });
    }

    private Runnable click = new Runnable(){
        public void run() {
            if (!dragging) {
                windowsCommandExecuter.mouseLeftDown();
                windowsCommandExecuter.mouseLeftUp();
            }
        }
    };

    @Override
    public void refresh() {

    }

}
