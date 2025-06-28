package com.smcc.backend_process;

import java.io.*;

public class BackendProcess {

    public static String errorMessage;
    String ID, PASSWORD;
    public static boolean isLogin, isRegistered;

    //Initialize Basic Variables
    public BackendProcess() {
        errorMessage = ID = PASSWORD = "";
        isLogin = isRegistered = false;
    }

    // Method to receive login details
    public void receiveLoginDetails(String id, String password) {
        this.ID = id;
        this.PASSWORD = encrypt(password); // Encrypt and store
    }

    // Caesar cipher encryption with alternating shift (1, 2, 1, 2, ...)
    public static String encrypt(String password) {
        return caesarCipherDecimal(password, true);
    }

    // Caesar cipher decryption with alternating shift (-1, -2, -1, -2, ...)
    public static String decrypt(String encryptedPassword) {
        return caesarCipherDecimal(encryptedPassword, false);
    }

    // Caesar cipher helper with decimal shift simulation
    private static String caesarCipherDecimal(String input, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        int[] shifts = {1, 2}; // alternates between 1 and 2 (1.5 avg)
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int shift = shifts[i % 2];
            if (!encrypt) shift = -shift;
            if (c >= 'a' && c <= 'z') {
                int base = 'a';
                result.append((char) (base + (c - base + shift + 26) % 26));
            } else if (c >= 'A' && c <= 'Z') {
                int base = 'A';
                result.append((char) (base + (c - base + shift + 26) % 26));
            } else {
                result.append(c); // Non-letters unchanged
            }
        }
        return result.toString();
    }

    // Check login details: returns true if credentials match
    public boolean checkLoginDetails(String inputID, String inputPassword) {
        String encryptedInputPassword = encrypt(inputPassword);
        if (this.ID != null && this.PASSWORD != null &&
            this.ID.equals(inputID) && this.PASSWORD.equals(encryptedInputPassword)) {
            isLogin = true;
            return true;
        } else {
            isLogin = false;
            errorMessage = "Invalid username or password";
            return false;
        }
    }
}
