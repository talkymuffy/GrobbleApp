package com.smcc.backend_process;

import com.smcc.app_interfaces.CipherService;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

    //CaesarCipher Technique
    public final class CaesarCipher implements CipherService {

        private final int[] shifts;


        //Construction
        public CaesarCipher(int[] shifts) {
            if (shifts == null || shifts.length == 0) {
                throw new IllegalArgumentException("Shifts must be non-null and non-empty");
            }
            this.shifts = shifts.clone();
        }


        @Override
        public String encrypt(char[] plaintext) {
            Objects.requireNonNull(plaintext);
            String result = applyCipher(plaintext, true);
            Arrays.fill(plaintext, '\0');
            return result;
        }

        @Override
        public String decrypt(String ciphertext) {
            Objects.requireNonNull(ciphertext);
            return applyCipher(ciphertext.toCharArray(), false);
        }

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

        public static void writeToFile(String c) throws IOException {
            BufferedWriter writer=new BufferedWriter(new FileWriter("login.txt"));
            writer.write(c);
            writer.close();
        }
        public static String readFromFile() throws IOException {
            BufferedReader reader=new BufferedReader(new FileReader("login.txt"));
            String s= reader.readLine();
            reader.close();
            return s;
        }

        public static void writeToFileConversation(String conversationLine) throws IOException{
            BufferedWriter writer=new BufferedWriter(new FileWriter("conversation.txt",true));
            Date dateTime=new Date();
            String format="["+dateTime.getTime()+"]"+":"+conversationLine+"\n";
            writer.write(format);
            writer.close();
        }
    }

