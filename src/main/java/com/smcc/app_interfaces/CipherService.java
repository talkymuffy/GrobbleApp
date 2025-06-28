package com.smcc.app_interfaces;

    public interface CipherService {
        String encrypt(char[] plaintext);

        String decrypt(String ciphertext);
    }