package com.rupenmitra.sonartest;

import android.app.Activity;
import android.os.Bundle;

import com.rupenmitra.sonartest.receiver.Receiver;
import com.rupenmitra.sonartest.sender.Sender;

public class MainActivity extends Activity {

    private static final Sender SENDER = new Sender();
    private static final Receiver AUDIO_RECORD = new Receiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SENDER.generateSound();
        AUDIO_RECORD.startRecording();
    }


}

