package com.rupenmitra.sonartest.sender;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;

public class Sender {

    private static final int DURATION = 60; // seconds
    private static final int SAMPLE_RATE = 44100;
    private static final int NUM_SAMPLES = DURATION * SAMPLE_RATE;

    private static final double FREQ_OF_TONE = 300; // hz
    private static final Handler HANDLER = new Handler();


    public void generateSound() {
        // Use a new tread as this can take a while
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final byte[] frequencyArray = genTone();
                HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        playSound(frequencyArray);
                    }
                });
            }
        });
        thread.start();
    }

    private byte[] genTone() {
        final double sample[] = new double[NUM_SAMPLES];
        final byte generatedSnd[] = new byte[2 * NUM_SAMPLES];

        // fill out the array
        for (int i = 0; i < NUM_SAMPLES; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (SAMPLE_RATE / FREQ_OF_TONE));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        return generatedSnd;
    }

    private void playSound(byte[] generatedSnd) {
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, NUM_SAMPLES,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }
}
