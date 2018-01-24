package org.java2.lesson7_8.server;

import org.java2.lesson7_8.client.ChatClientFrame;

import java.io.*;
import java.net.Socket;

public class ChatServerClient implements Runnable{

    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private ChatServer server;
    private String UserName;

    ChatServerClient(Socket socket, ChatServer chatServer) throws IOException {
        setUserName();
        this.socket = socket;
        this.server = chatServer;
        this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public void sendMessage(String s) throws IOException {
        this.writer.write(s);
        this.writer.newLine();
        this.writer.flush();
    }

    public String getUserName() {
        return UserName;
    }

    private void setUserName() {
        UserName = ChatClientFrame.getUserName();
    }

    @Override
    public void run() {
        while (true){
            try {
                if (reader != null) {
                    String s = this.reader.readLine();
                    for (ChatServerClient chatServerClient : this.server.getClients()) {
                        chatServerClient.sendMessage(s);
                    }
                }
            } catch (IOException e) {
                reader = null;
            }
        }
    }
}
