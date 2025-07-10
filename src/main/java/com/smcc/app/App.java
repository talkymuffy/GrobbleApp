package com.smcc.app;


import com.smcc.backend_process.AI.MrCluckIntelligence;
import com.smcc.backend_process.AudioSystem;
import com.smcc.backend_process.CaesarCipher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class App {
    JFrame FRAME;
    JPanel MAIN_CONTAINER;
    CardLayout cardLayout;

    // Login
    JTextField LOGIN_TEXT_FIELD;
    JPasswordField LOGIN_PASSWORD_FIELD;
    private JLabel MESSAGE_LABEL;
    JPanel LOGIN_PANEL;


    ImageIcon icon;
    // Register
    JPanel REGISTER_PANEL;

    //MAIN
    JPanel MAIN;


    private App() {
        FRAME = new JFrame("Grobble App");
        FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        FRAME.setSize(1024, 624);
        FRAME.setLocationRelativeTo(null);

        //Loading Assets

        ImageIcon logoIcon = new ImageIcon("logo.png"); // Transparent PNG
        Image scaledImage = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon newlogoIcon = new ImageIcon(scaledImage);
        icon = newlogoIcon;

        FRAME.setIconImage(newlogoIcon.getImage());

        cardLayout = new CardLayout();
        MAIN_CONTAINER = new JPanel(cardLayout);


        LOGIN_PANEL = createLoginPanel();
        REGISTER_PANEL = createRegisterPanel();
        MAIN = createChatTypePanel("Mr. Cluck's Chat!");

        MAIN_CONTAINER.add(LOGIN_PANEL, "Login");
        MAIN_CONTAINER.add(REGISTER_PANEL, "Register");
        MAIN_CONTAINER.add(MAIN, "Main");


        FRAME.add(MAIN_CONTAINER);
        FRAME.setVisible(true);
    }

    private JPanel createChatTypePanel(String title) {
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBackground(new Color(45, 45, 45));
        chatPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                title,
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                Color.WHITE
        ));

        // Chat message display area
        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBackground(new Color(35, 35, 35));
        messageArea.setForeground(Color.WHITE);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input + Send
        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        inputField.setBackground(new Color(50, 50, 50));
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton sendButton = getJButton(inputField, messageArea);

        inputField.addActionListener(e -> sendButton.doClick()); // Send on Enter
        sendButton.addActionListener(e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                messageArea.append("You: " + text + "\n");
                inputField.setText("");


                try {
                    CaesarCipher.writeToFileConversation(text);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                String response = MrCluckIntelligence.processUserMessage(text);
                if(response.equals( "I'm not sure how to respond to that yet, but I'm learning!")){
                    AudioSystem.playTrade();
                }
                else{
                    AudioSystem.playPlop();
                }
                messageArea.setFont(new Font(Font.SANS_SERIF,Font.BOLD,14));
                messageArea.append("Mr. Cluck: " + response + "\n");
                try {
                    CaesarCipher.writeToFileConversation(response);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                messageArea.setCaretPosition(messageArea.getDocument().getLength());
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(45, 45, 45));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        chatPanel.add(scrollPane, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        return chatPanel;
    }

    private static JButton getJButton(JTextField inputField, JTextArea messageArea) {
        JButton sendButton = new JButton("Send");
        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Action on send
        sendButton.addActionListener(e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                messageArea.append("You: " + text + "\n");
                inputField.setText("");
                // Scroll to bottom
                messageArea.setCaretPosition(messageArea.getDocument().getLength());
            }
        });
        return sendButton;
    }


    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));

        JLabel logoLabel = new JLabel(icon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 0))); // Spacer


        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        LOGIN_TEXT_FIELD = new JTextField(15);
        LOGIN_TEXT_FIELD.setFont(fieldFont);
        LOGIN_PASSWORD_FIELD = new JPasswordField(15);
        LOGIN_PASSWORD_FIELD.setFont(fieldFont);
        MESSAGE_LABEL = new JLabel(" ");
        MESSAGE_LABEL.setForeground(Color.WHITE);
        MESSAGE_LABEL.setFont(labelFont);

        panel.add(createStyledInput("Username:", LOGIN_TEXT_FIELD, labelFont));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createStyledInput("Password:", LOGIN_PASSWORD_FIELD, labelFont));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(labelFont);
        loginBtn.addActionListener(new LoginAction());

        JButton registerBtn = new JButton("New? Register Now!");
        registerBtn.setBackground(new Color(100, 149, 237));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setFont(labelFont);
        registerBtn.addActionListener(e -> {
            cardLayout.show(MAIN_CONTAINER, "Register");
        });


        JPanel dualButtonPanel = new JPanel();
        dualButtonPanel.setLayout(new BorderLayout());
        dualButtonPanel.setBackground(new Color(30, 30, 30));


        JPanel loginWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginWrapper.setBackground(new Color(30, 30, 30));
        loginWrapper.add(loginBtn);

        JPanel registerWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        registerWrapper.setBackground(new Color(30, 30, 30));
        registerWrapper.add(registerBtn);

        dualButtonPanel.add(loginWrapper, BorderLayout.CENTER);
        dualButtonPanel.add(registerWrapper, BorderLayout.EAST);

        panel.add(dualButtonPanel);

        JPanel msgPanel = new JPanel();
        msgPanel.setBackground(new Color(30, 30, 30));
        msgPanel.add(MESSAGE_LABEL);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(msgPanel);



        return panel;

    }


    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(80, 50, 80, 50));

        JLabel logoLabel = new JLabel(icon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        JTextField username = new JTextField(15);
        JTextField email = new JTextField(15);
        JPasswordField password = new JPasswordField(15);
        JPasswordField confirmPassword = new JPasswordField(15);
        JLabel status = new JLabel(" ");
        status.setForeground(Color.WHITE);
        status.setFont(labelFont);

        panel.add(createStyledInput("Username:", username, labelFont));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createStyledInput("Email:", email, labelFont));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createStyledInput("Password:", password, labelFont));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createStyledInput("Confirm Password:", confirmPassword, labelFont));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton backToLogin = new JButton("← Back to Login");
        backToLogin.setBackground(new Color(105, 105, 105));
        backToLogin.setForeground(Color.WHITE);
        backToLogin.setFont(labelFont);
        backToLogin.setFocusPainted(false);
        backToLogin.addActionListener(e -> cardLayout.show(MAIN_CONTAINER, "Login"));

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(70, 130, 180));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(labelFont);
        registerBtn.setFocusPainted(false);

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName=username.getText();
                String email_String=email.getText();
                String pass = new String(password.getPassword());
                String confirm = new String(confirmPassword.getPassword());

                JDialog dialog = new JDialog(FRAME, "Gobble Notice", false); // false = non-modal
                dialog.setSize(500, 250);
                dialog.setUndecorated(false); // You can set to true for a frameless look

                JPanel msgPanel = new JPanel();
                msgPanel.setBackground(new Color(30, 30, 30));
                msgPanel.add(status);


                dialog.add(msgPanel);
                if (!pass.equals(confirm)) {
                    status.setText("Passwords do not match.");
                } else {
                    String userDetails=userName+" "+email_String+" "+pass;
                    try {
                        CaesarCipher.writeToFile(new CaesarCipher(new int[]{1,2}).encrypt(userDetails.toCharArray()));
                        status.setText("Registration successful. Go Back To Login To Enter!");
                    } catch (IOException ex) {
                        status.setText("Error! File Not Found!");
                        throw new RuntimeException(ex);
                    }

                }

                // Position the dialog just beneath the FRAME
                dialog.setLocation(FRAME.getX()*2, FRAME.getY()*2);
                dialog.setVisible(true);

            }
        });

        // Wrap both buttons in right-aligned panel
        JPanel dualButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        dualButtonPanel.setBackground(new Color(30, 30, 30));
        dualButtonPanel.add(registerBtn);
        dualButtonPanel.add(backToLogin);
        panel.add(dualButtonPanel);


        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(30, 30, 30));
        btnPanel.add(registerBtn);
        panel.add(btnPanel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));





        return panel;
    }

    private JPanel createStyledInput(String label, JTextField field, Font labelFont) {
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(Color.LIGHT_GRAY);
        jLabel.setFont(labelFont);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(30, 30, 30));
        panel.add(jLabel, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return panel;
    }

    private class LoginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = LOGIN_TEXT_FIELD.getText();
            String password = new String(LOGIN_PASSWORD_FIELD.getPassword());

            try {

                String encryptedDetails = CaesarCipher.readFromFile();
                String decryptedDetails = new CaesarCipher(new int[]{1, 2}).decrypt(encryptedDetails);


                String[] tokens = decryptedDetails.trim().split(" ");
                if (tokens.length != 3) {
                    System.out.println("Decrypted login file contents: [" + decryptedDetails + "]");
                    throw new IllegalStateException("Invalid login file format");
                }

                String storedUser = tokens[0];
                String storedPassword = tokens[2];

                if (storedUser.equals(username) && storedPassword.equals(password)) {
                    cardLayout.show(MAIN_CONTAINER, "Main");

                } else {
                    System.out.println("Decrypted login file contents: [" + decryptedDetails + "]");
                    showDialog("Details Do Not Match! Try Again");
                }
            } catch (IOException ex) {
                showDialog("Error! Try Restarting the App Or Register Yourself First!");
                ex.printStackTrace();
            }
        }

        private void showDialog(String message) {
            JDialog dialog = new JDialog(FRAME, "Gobble Notice", false);
            dialog.setSize(400, 150);
            dialog.setLocationRelativeTo(FRAME);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            JPanel msgPanel = new JPanel();
            msgPanel.setBackground(new Color(30, 30, 30));
            JLabel status = new JLabel(message);
            status.setForeground(Color.WHITE);
            status.setFont(new Font("Segoe UI", Font.BOLD, 14));
            msgPanel.add(status);

            dialog.add(msgPanel);
            dialog.setVisible(true);
        }
    }


    public static void init() {
        new App();
    }
}