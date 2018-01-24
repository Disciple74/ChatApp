package org.java2.lesson7_8.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements Runnable{

    private ServerSocket serverSocket;

    private List<ChatServerClient> clients = new ArrayList<>();

    ChatServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (true){
            try {
                Socket accept = this.serverSocket.accept();
                ChatServerClient chatServerClient = new ChatServerClient(accept, this);
                Thread thread = new Thread(chatServerClient);
                thread.setDaemon(true);
                thread.start();
                if(!accept.isClosed()) {
                    for (ChatServerClient client : this.clients) {
                        client.sendMessage(String.format("New user added: [%s]", client.getUserName()));
                    }
                    this.clients.add(chatServerClient);
                }
            } catch (Throwable t) {
                System.out.println("User has been disconnected");
            }
        }
    }

    public List<ChatServerClient> getClients() {
        return this.clients;
    }
}
