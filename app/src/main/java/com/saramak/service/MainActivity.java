package com.saramak.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ServiceCallback {


    private ServiceConnection connection;
    private TextView stausTextView;
    private Button bindButton;
    private Button unbindButton;
    private MyService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(getServiceIntent());
        stausTextView = (TextView) findViewById(R.id.stats);
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
}

