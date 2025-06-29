package com.smcc.backend_process;

import com.smcc.app_interfaces.CipherService;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * Implements a variable‐shift Caesar cipher encryption/decryption service.
 * <p>
 * This class supports:
 * <ul>
 *   <li>Encrypting a plaintext char[] by shifting letters using a repeating
 *       pattern of integer shifts.</li>
 *   <li>Decrypting a ciphertext String back to plaintext.</li>
 *   <li>Zeroing out the plaintext array after encryption for security.</li>
 *   <li>Persisting login credentials to “login.txt”.</li>
 *   <li>Appending timestamped conversation entries to “conversation.txt”.</li>
 * </ul>
 * Instances are immutable after construction and safe for use across threads.
 *
 *
 */
public final class CaesarCipher implements CipherService {

    /** The repeating pattern of shifts to apply to each character. */
    private final int[] shifts;

    /**
     * Constructs a CaesarCipher with the given shift pattern.
     * Each plaintext character is shifted by shifts[i % shifts.length].
     *
     * @param shifts an array of integer shifts; must be non‐null and non‐empty
     * @throws IllegalArgumentException if shifts is null or has length zero
     */
    public CaesarCipher(int[] shifts) {
        if (shifts == null || shifts.length == 0) {
            throw new IllegalArgumentException("Shifts must be non-null and non-empty");
        }
        this.shifts = shifts.clone();
    }

    /**
     * Encrypts the given plaintext using the Caesar cipher.
     * <p>
     * After encryption, the plaintext array is zeroed out for security.
     *
     * @param plaintext the array of characters to encrypt; must not be null
     * @return the resulting ciphertext as a String
     * @throws NullPointerException if plaintext is null
     */
    @Override
    public String encrypt(char[] plaintext) {
        Objects.requireNonNull(plaintext, "plaintext must not be null");
        String result = applyCipher(plaintext, true);
        Arrays.fill(plaintext, '\0');
        return result;
    }

    /**
     * Decrypts the given ciphertext (previously produced by this cipher).
     *
     * @param ciphertext the encrypted text to decrypt; must not be null
     * @return the decrypted plaintext as a String
     * @throws NullPointerException if ciphertext is null
     */
    @Override
    public String decrypt(String ciphertext) {
        Objects.requireNonNull(ciphertext, "ciphertext must not be null");
        return applyCipher(ciphertext.toCharArray(), false);
    }

    /**
     * Applies the Caesar cipher transformation.
     *
     * @param input   the char array to process
     * @param encrypt true to encrypt, false to decrypt
     * @return the transformed text as a String
     */
    private String applyCipher(char[] input, boolean encrypt) {
        StringBuilder out = new StringBuilder(input.length);
        for (int i = 0; i < input.length; i++) {
            char c = input[i];
            int shift = shifts[i % shifts.length] * (encrypt ? 1 : -1);
            if (c >= 'a' && c <= 'z') {
                out.append((char) ('a' + (c - 'a' + shift + 26) % 26));
            } else if (c >= 'A' && c <= 'Z') {
                out.append((char) ('A' + (c - 'A' + shift + 26) % 26));
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }

    /**
     * Writes the provided content to a file named "login.txt",
     * overwriting any existing content.
     *
     * @param c the content to write
     * @throws IOException if an I/O error occurs while writing
     */
    public static void writeToFile(String c) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("login.txt"))) {
            writer.write(c);
        }
    }

    /**
     * Reads and returns the first line from "login.txt".
     *
     * @return the line read from the file, or null if empty
     * @throws IOException if an I/O error occurs while reading
     */
    public static String readFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("login.txt"))) {
            return reader.readLine();
        }
    }

    /**
     * Appends a timestamped conversation line to "conversation.txt".
     * Each entry is prefixed with its epoch‐millisecond timestamp.
     *
     * @param conversationLine the text to append
     * @throws IOException if an I/O error occurs while writing
     */
    public static void writeToFileConversation(String conversationLine) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("conversation.txt", true))) {
            long ts = new Date().getTime();
            writer.write("[" + ts + "]:" + conversationLine);
            writer.newLine();
        }
    }
}