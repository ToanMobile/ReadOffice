package com.app.office.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
    public static final String HOST = "172.25.3.147";
    public static final int LISTENER_PORT = 3000;
    private static SocketClient sc = new SocketClient();
    private Socket client;

    public static SocketClient instance() {
        return sc;
    }

    public SocketClient() {
        initConnection();
    }

    public void initConnection() {
        try {
            this.client = new Socket(HOST, 3000);
        } catch (UnknownHostException unused) {
            System.out.println("Error setting up socket connection: unknown host at 172.25.3.147:3000");
        } catch (IOException e) {
            PrintStream printStream = System.out;
            printStream.println("Error setting up socket connection: " + e);
        }
    }

    public InputStream getFile(String str) {
        try {
            OutputStream outputStream = this.client.getOutputStream();
            outputStream.write(str.getBytes());
            outputStream.flush();
            return this.client.getInputStream();
        } catch (Exception unused) {
            PrintStream printStream = System.out;
            printStream.println("Error reading from file: " + str);
            return null;
        }
    }
}
