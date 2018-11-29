package com.rupenmitra.sonartest;

import com.rupenmitra.sonartest.sender.Sender;

public enum SenderSingleton {

    INSTANCE;

    private final Sender sender;

    SenderSingleton() {
        sender = new Sender();
    }

    public Sender getSender() {
        return sender;
    }
}
