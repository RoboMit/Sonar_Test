package com.rupenmitra.sonartest.receiver;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    //Conversion of short to byte
    private static byte[] short2byte(short[] sData) {
        int shortArraySize = sData.length;
        byte[] bytes = new byte[shortArraySize * 2];

        for (int i = 0; i < shortArraySize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
        }

        return bytes;
    }

    private static void writeAudioDataToFile() {

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/test.wav";

        try {
            File file = new File(filePath);
            final boolean created = file.createNewFile();
            if(!created) {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        short sData[] = new short[BUFFER_ELEMENTS_TO_RECORD];

        try(FileOutputStream os = new FileOutputStream(filePath)) {
            while (isRecording.get()) {
                RECORDER.read(sData, 0, sData.length);
                System.out.println("Short writing to file " + sData.length);
                byte bData[] = short2byte(sData);
                os.write(bData, 0, BYTES_PER_ELEMENT * BUFFER_ELEMENTS_TO_RECORD);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        isRecording.set(false);
        RECORDER.stop();
        RECORDER.release();
    }

}