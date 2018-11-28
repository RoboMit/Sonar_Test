package com.rupenmitra.sonartest.sender;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;

public class Sender {

    private static final double FINAL_AMPLITUDE = 0.8 * 32767; // 32767 is highest positive integer for signed integer
    private static final double FREQ_OF_TONE = 300;
    private static final int DURATION = 60;
    private static final int SAMPLE_RATE = 44100; // This means one second has 44100 number of samples
    private static final int NUM_SAMPLES = DURATION * SAMPLE_RATE;

    private static final double TICKS_PER_SECOND = SAMPLE_RATE / FREQ_OF_TONE;
    private static final double MULTIPLIER = 2 * Math.PI / TICKS_PER_SECOND;

    private static final Handler HANDLER = new Handler();

    public void generateSound() {
        // Use a new tread as this can take a while
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        playSound(genTone());
                    }
                });
            }
        });
        thread.start();
    }

    private byte[] genTone() {
        final short sample[] = new short[NUM_SAMPLES];
        final byte generatedSnd[] = new byte[2 * NUM_SAMPLES];

        // fill out the array
        for (int i = 0; i < NUM_SAMPLES; ++i) {
            // amp * Math.sin(2*pi*i*(frequencyOfTone/SampleRate))
            // multiplier is 2*pi*(freq/sample_rate)
            sample[i] = (short) (FINAL_AMPLITUDE * Math.sin(MULTIPLIER * i));
        }

        // convert to 16 bit pcm sound array, assumes the sample buffer is normalised.
        int idx = 0;
        for (final short val : sample) {
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
