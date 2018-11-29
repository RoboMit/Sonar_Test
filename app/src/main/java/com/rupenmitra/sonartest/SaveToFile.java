package com.rupenmitra.sonartest;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

public class SaveToFile {
    public static void saveToFile(String fileName, byte[] input) {
        String externalSdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String finalPath = externalSdCardPath + "/" + fileName;
        File storageFile = new File(finalPath);

        if(storageFile.exists()) {
            storageFile.delete();
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(byte b : input) {
            int x = b;
            stringBuilder.append(x);
            stringBuilder.append(" ");
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(storageFile))) {

            /*for(byte b : input) {
                int x = b;
                bufferedWriter.write(x);
                bufferedWriter.write(" ");
            }*/

            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Completed writing to the file: " + storageFile);
        }
    }

    public static void saveToFile(String fileName, short[] input) {
        String externalSdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String finalPath = externalSdCardPath + "/" + fileName;
        File storageFile = new File(finalPath);

        if(storageFile.exists()) {
            storageFile.delete();
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(short b : input) {
            int x = b;
            stringBuilder.append(x);
            stringBuilder.append(" ");
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(storageFile))) {

            /*for(byte b : input) {
                int x = b;
                bufferedWriter.write(x);
                bufferedWriter.write(" ");
            }*/

            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Completed writing to the file: " + storageFile);
        }
    }

    public static void saveToFile(String fileName, Short[] input) {
        String externalSdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String finalPath = externalSdCardPath + "/" + fileName;
        File storageFile = new File(finalPath);

        if(storageFile.exists()) {
            storageFile.delete();
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(short b : input) {
            int x = b;
            stringBuilder.append(x);
            stringBuilder.append(" ");
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(storageFile))) {

            /*for(byte b : input) {
                int x = b;
                bufferedWriter.write(x);
                bufferedWriter.write(" ");
            }*/

            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Completed writing to the file: " + storageFile);
        }
    }

    public static void saveToFile(String fileName, Double[] input) {
        String externalSdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String finalPath = externalSdCardPath + "/" + fileName;
        File storageFile = new File(finalPath);

        if(storageFile.exists()) {
            storageFile.delete();
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(Double b : input) {
            stringBuilder.append(b);
            stringBuilder.append(" ");
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(storageFile))) {
            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Completed writing to the file: " + storageFile);
        }
    }
}
