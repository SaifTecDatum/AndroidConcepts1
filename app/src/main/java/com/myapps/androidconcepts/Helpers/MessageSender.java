package com.myapps.androidconcepts.Helpers;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender extends AsyncTask<String, Void, Void> {

    //Youtube Link: https://www.youtube.com/watch?v=29y4X65ZUwE
    //TCP/IP Sockets - Send and Receive Data from Android Device to PC & PC to device.
    //completed half video, have to complete remaining half. Need to download NetBeans to run output on PC.

    Socket socket;
    DataOutputStream dataOutputStream;
    PrintWriter printWriter;

    @Override
    protected Void doInBackground(String... strings) {

        String message = strings[0];

        try {
            socket = new Socket("192.168.0.107", 7800);
            printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.write(message);
            printWriter.flush();
            printWriter.close();
            socket.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }




}
