package org.java2.lesson7_8.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ChatClientFrame extends JFrame implements ReceiveMessage{

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private JTextArea textArea;
    private JTextField textField;
    private ChatClient chatClient;
    private static String userName;
    private Font font = new Font("Arial", Font.PLAIN, 15);

    ChatClientFrame(ChatClient chatClient){
        JDialog dialog = setUserName();
        dialog.setVisible(true);

        this.chatClient = chatClient;
        setTitle("ChatRoom");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setSize(500,500);
        setLocationRelativeTo(null);

        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.textArea.setFont(font);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        JPanel inputPanel = new JPanel(new BorderLayout());
        add(inputPanel, BorderLayout.SOUTH);
        JButton send = new JButton("Send");

        this.textField = new JTextField();
        this.textField.setFont(font);
        inputPanel.add(send, BorderLayout.EAST);
        inputPanel.add(this.textField, BorderLayout.CENTER);

        this.textField.addActionListener(e -> sendMessage());
        send.addActionListener(e -> sendMessage());
    }

    private void sendMessage(){
        String message = this.textField.getText();
        if (!message.isEmpty()){
            try {
                this.chatClient.sendMessage(String.format("[%s]: %s", userName, message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.textField.setText("");
    }

    public static String getUserName() {
        return userName;
    }

    private JDialog noName(){
        JDialog dialog = new JDialog(this, "Warning", true);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setSize(250,100);
        dialog.setLocationRelativeTo(null);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(font);
        textArea.append("Enter your nickname!");
        JButton anotherTime = new JButton("Do it!");
        anotherTime.addActionListener(e -> dialog.setVisible(false));
        dialog.add(textArea, BorderLayout.CENTER);
        dialog.add(anotherTime, BorderLayout.SOUTH);
        return dialog;
    }

    private JDialog setUserName(){
        JDialog dialog = new JDialog(this, "Set your Nickname", true);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setSize(250,100);
        dialog.setLocationRelativeTo(null);
        JTextField name = new JTextField();
        name.setFont(font);
        name.addActionListener(e -> {
            if (!name.getText().trim().isEmpty()) {
                setName(name);
                dialog.setVisible(false);
            }
        });
        JButton apply = new JButton("Apply");
        apply.addActionListener(e -> {
            if (!name.getText().trim().isEmpty()) {
                setName(name);
                dialog.setVisible(false);
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {
            JDialog error = noName();
            error.setVisible(true);
        });
        JPanel panel = new JPanel();
        panel.add(apply, BorderLayout.WEST);
        panel.add(cancel, BorderLayout.EAST);
        dialog.add(name, BorderLayout.CENTER);
        dialog.add(panel, BorderLayout.SOUTH);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        return dialog;
    }

    private void setName(JTextField name){
        if (!name.getText().trim().isEmpty()) userName = name.getText().trim();
    }

    @Override
    public void receive(String message) {
        this.textArea.append(message);
        this.textArea.append(LINE_SEPARATOR);
    }
}
