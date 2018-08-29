package com.saramak.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ServiceCallback {

    public static final String TAG = "Life";
    private ServiceConnection connection;
    private TextView stausTextView;
    private Button bindButton;
    private Button unbindButton;
    private MyService service;
    private CountDownTimer timerSS =  new CountDownTimer(Long.MAX_VALUE, 1000){

        @Override
        public void onTick(long millisUntilFinished) {
            stausTextView2.setText(++j + " times");
        }

        @Override
        public void onFinish() {

        }
    };
    private CountDownTimer timerPR =  new CountDownTimer(Long.MAX_VALUE, 1000){

        @Override
        public void onTick(long millisUntilFinished) {
            stausTextView3.setText(++k + " times");
        }

        @Override
        public void onFinish() {

        }
    };
    private TextView stausTextView2;
    private TextView stausTextView3;
    private int k =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        startService(getServiceIntent());
        stausTextView = (TextView) findViewById(R.id.stats);
        stausTextView2 = (TextView) findViewById(R.id.status2);
        stausTextView3 = (TextView) findViewById(R.id.status3);
        stausTextView.setText("service " + service);
        connection = new MyServiceConnection(this);
        bindButton = (Button) findViewById(R.id.buttonBind);
        bindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bindService(getServiceIntent(), connection, Service.BIND_AUTO_CREATE);
            }
        });
        unbindButton = (Button) findViewById(R.id.buttonUnBind);
        unbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);
            }
        });

    }





    public Intent getServiceIntent() {
        return new Intent(this, MyService.class);
    }

    int i = 0;
    int j = 0;
    @Override
    public void onConnected(MyService service) {
        this.service = service;
        stausTextView.setText("bind " + (++i) + service );
    }

    @Override
    public void onDisconnect() {
        stausTextView.setText("unbind " + --i + " " + service);
    }


    static class MyServiceConnection implements ServiceConnection {

        private final ServiceCallback callback;

        public MyServiceConnection(ServiceCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalBinder ls = (MyService.LocalBinder) service;
            callback.onConnected(ls.getService());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            callback.onDisconnect();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        timerSS.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        timerSS.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        timerPR.cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        timerPR.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
    }
}


