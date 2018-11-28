package com.rupenmitra.sonartest.receiver;

import android.os.Environment;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

class AudioListener {

    private static final Deque<Complex[]> DEQUE = new ArrayDeque<>();

    public void listenAudioShortStream(short[] audioStream) {
        double[] doubles = shortsToDouble(audioStream);
        Complex[] fourierTransform = new FastFourierTransformer(DftNormalization.STANDARD).transform(doubles, TransformType.FORWARD);
        DEQUE.add(fourierTransform);
    }

    private static double[] shortsToDouble(short[] audioStream) {
        double[] doubles = new double[audioStream.length];
        for (int i = 0; i < audioStream.length; i++) {
            doubles[i] = audioStream[i] / 32768.0;
        }
        return doubles;
    }

    public static Deque<Complex[]> getDEQUE() {
        return DEQUE;
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

    private static void writeAudioDataToFile(short[] audioStream) {

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

        try(FileOutputStream os = new FileOutputStream(filePath)) {
            System.out.println("Short writing to file " + audioStream.length);
            byte bData[] = short2byte(audioStream);
            os.write(bData, 0, bData.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
