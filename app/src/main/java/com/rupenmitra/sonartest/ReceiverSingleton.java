package com.rupenmitra.sonartest;

import com.rupenmitra.sonartest.receiver.Receiver;
import com.rupenmitra.sonartest.sender.Sender;

public enum ReceiverSingleton {
    INSTANCE;

    private final Receiver receiver;

    ReceiverSingleton() {
        receiver = new Receiver();
    }

    public Receiver getReceiver() {
        return receiver;
    }

}
