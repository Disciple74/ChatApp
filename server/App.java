package org.java2.lesson7_8.server;

import org.java2.lesson7_8.common.Configs;

import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String exit = "exit";
        ChatServer chatServer = new ChatServer(Configs.SERVER_PORT);
        String fromConsole;
        Thread thread = new Thread(chatServer);
        thread.start();
        System.out.println("Hello, this is a simple chat server!\nType \"exit\" to exit");
        while ((fromConsole = scanner.nextLine().trim()) != null){
            if(exit.equalsIgnoreCase(fromConsole)) break;
            if("users".equalsIgnoreCase(fromConsole.trim()))
                System.out.println(String.format("We got [%s] users connected", chatServer.getClients().size()));

        }
        System.out.println("Bye!");
        System.exit(0);
    }
}
