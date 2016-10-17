package com.saramak.service;

public interface ServiceCallback {

    void onConnected(MyService service);

    void onDisconnect();
}
