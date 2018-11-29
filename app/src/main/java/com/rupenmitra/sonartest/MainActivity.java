package com.rupenmitra.sonartest;

import android.app.Activity;
import android.os.Bundle;

import com.rupenmitra.sonartest.receiver.Receiver;
import com.rupenmitra.sonartest.sender.Sender;

public class MainActivity extends Activity {

    private final Receiver AUDIO_RECORD;
    private final Sender SENDER;

    public MainActivity() {
        super();
        SENDER = SenderSingleton.INSTANCE.getSender();
        AUDIO_RECORD = ReceiverSingleton.INSTANCE.getReceiver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SENDER.generateSound();
    }


}

