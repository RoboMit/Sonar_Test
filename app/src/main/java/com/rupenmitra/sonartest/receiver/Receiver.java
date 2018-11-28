package com.rupenmitra.sonartest.receiver;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.util.concurrent.atomic.AtomicBoolean;


public class Receiver {

    private static final int SAMPLING_FREQUENCY = 44100;
    private static final int BUFFER_ELEMENTS_TO_RECORD = 4096;
    private static final int BYTES_PER_ELEMENT = 2;

    private static final int MIN_BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLING_FREQUENCY,
            AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

    private static final AudioRecord RECORDER = new AudioRecord.Builder()
            .setAudioSource(MediaRecorder.AudioSource.MIC)
            .setAudioFormat(new AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(SAMPLING_FREQUENCY)
                    .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
                    .build())
            .setBufferSizeInBytes(BYTES_PER_ELEMENT * BUFFER_ELEMENTS_TO_RECORD)
            .build();

    private static final AudioListener AUDIO_LISTENER = new AudioListener();

    private static final Thread RECORDING_THREAD = new Thread(new Runnable() {
        @Override
        public void run() {
            writeAudioDataToFile();
        }
    }, "Recorder Thread");

    private static final AtomicBoolean isRecording = new AtomicBoolean(false);

    static {

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RECORDER.stop();
                    RECORDER.release();
                    RECORDING_THREAD.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public void startRecording() {
        RECORDER.startRecording();
        isRecording.set(true);
        RECORDING_THREAD.start();
    }



    private static void writeAudioDataToFile() {

        short[] audioData = new short[BUFFER_ELEMENTS_TO_RECORD];

        while (isRecording.get()) {
            RECORDER.read(audioData, 0, audioData.length);
            AUDIO_LISTENER.listenAudioShortStream(audioData);
        }
    }

    private void stopRecording() {
        isRecording.set(false);
        RECORDER.stop();
        RECORDER.release();
    }

}