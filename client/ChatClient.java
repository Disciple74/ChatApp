package org.java2.lesson7_8.client;

import java.io.*;
import java.net.Socket;

public class ChatClient implements Runnable{
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private ReceiveMessage receiveMessage;


    ChatClient(String host, int port) throws IOException {
        this.socket = new Socket(host,port);
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    public void sendMessage(String message) throws IOException {
        this.writer.write(message);
        this.writer.newLine();
        this.writer.flush();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = reader.readLine();
                this.receiveMessage.receive(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setReceiveMessage(ReceiveMessage receiveMessage) {
        this.receiveMessage = receiveMessage;
    }
}
