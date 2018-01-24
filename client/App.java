package org.java2.lesson7_8.client;

import org.java2.lesson7_8.common.Configs;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        ChatClient chatClient = new ChatClient(Configs.SERVER_ADDRESS, Configs.SERVER_PORT);
        Thread thread = new Thread(chatClient);
        thread.start();

        ChatClientFrame chatClientFrame = new ChatClientFrame(chatClient);
        chatClient.setReceiveMessage(chatClientFrame);
        chatClientFrame.setVisible(true);
    }
}
