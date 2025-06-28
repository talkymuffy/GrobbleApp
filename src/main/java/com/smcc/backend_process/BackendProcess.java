package com.smcc.backend_process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.concurrent.*;

/**
 * Core cipher interface.
 */
public interface CipherService {
    String encrypt(char[] plaintext);
    String decrypt(String ciphertext);
}

/**
 * Caesar cipher with parameterized alternating shifts.
 */
public final class CaesarCipher implements CipherService {
    private static final Logger logger = LoggerFactory.getLogger(CaesarCipher.class);
    private final int[] shifts;

    public CaesarCipher(int[] shifts) {
        if (shifts == null || shifts.length == 0) {
            throw new IllegalArgumentException("Shifts must be non-null and non-empty");
        }
        this.shifts = shifts.clone();
    }

    @Override
    public String encrypt(char[] plaintext) {
        Objects.requireNonNull(plaintext, "Plaintext must not be null");
        String result = applyCipher(plaintext, true);
        Arrays.fill(plaintext, '\0');  // wipe
        return result;
    }

    @Override
    public String decrypt(String ciphertext) {
        Objects.requireNonNull(ciphertext, "Ciphertext must not be null");
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
        logger.debug("{} of [{}] → [{}]",
                     encrypt ? "Encrypt" : "Decrypt",
                     new String(input), out);
        return out.toString();
    }
}

/**
 * Immutable user record.
 */
public final class UserRecord {
    private final String id;
    private final String encryptedPassword;

    public UserRecord(String id, String encryptedPassword) {
        this.id = Objects.requireNonNull(id, "ID must not be null");
        this.encryptedPassword = Objects.requireNonNull(encryptedPassword, "Password must not be null");
    }

    public String getId() { return id; }
    public String getEncryptedPassword() { return encryptedPassword; }
}

/**
 * Simple thread-safe in-memory store.
 */
public interface UserStore {
    void save(UserRecord user);
    Optional<UserRecord> findById(String id);
}

public final class InMemoryUserStore implements UserStore {
    private final Map<String, UserRecord> map = new ConcurrentHashMap<>();

    @Override
    public void save(UserRecord user) {
        map.put(user.getId(), user);
    }

    @Override
    public Optional<UserRecord> findById(String id) {
        return Optional.ofNullable(map.get(id));
    }
}

/**
 * Handles registration & login using a CipherService + UserStore.
 */
public final class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private final CipherService cipher;
    private final UserStore store;

    public LoginService(CipherService cipher, UserStore store) {
        this.cipher = Objects.requireNonNull(cipher);
        this.store = Objects.requireNonNull(store);
    }

    public boolean register(String id, char[] password) {
        validateId(id);
        validatePassword(password);
        if (store.findById(id).isPresent()) {
            logger.warn("Registration failed: {} already exists", id);
            return false;
        }
        String encrypted = cipher.encrypt(password);
        store.save(new UserRecord(id, encrypted));
        logger.info("User {} registered", id);
        return true;
    }

    public boolean login(String id, char[] password) {
        validateId(id);
        validatePassword(password);
        Optional<UserRecord> user = store.findById(id);
        if (!user.isPresent()) {
            logger.warn("Login failed: {} not found", id);
            wipe(password);
            return false;
        }
        String encryptedInput = cipher.encrypt(password);
        boolean success = encryptedInput.equals(user.get().getEncryptedPassword());
        wipe(password);
        logger.info("Login attempt for {}: {}", id, success ? "SUCCESS" : "FAILURE");
        return success;
    }

    private void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID must not be empty");
        }
    }

    private void validatePassword(char[] pwd) {
        if (pwd == null || pwd.length == 0) {
            throw new IllegalArgumentException("Password must not be empty");
        }
    }

    private void wipe(char[] pwd) {
        Arrays.fill(pwd, '\0');
    }
}

/**
 * Demo with “unique Minecraft” user IDs.
 */
public class Main {
    public static void main(String[] args) {
        int[] pattern = {1, 2};  // your alternating shifts
        CipherService cipher = new CaesarCipher(pattern);
        UserStore store = new InMemoryUserStore();
        LoginService auth = new LoginService(cipher, store);

        // Minecraft‐style registrations
        char[] pwd1 = "diamondPickaxe".toCharArray();
        auth.register("EnderDragon", pwd1);

        char[] pwd2 = "netherPortal".toCharArray();
        auth.register("CreeperKing", pwd2);

        // Login attempts
        char[] attempt1 = "diamondPickaxe".toCharArray();
        System.out.println("EnderDragon login: " + auth.login("EnderDragon", attempt1));

        char[] attempt2 = "wrongPassword".toCharArray();
        System.out.println("CreeperKing login: " + auth.login("CreeperKing", attempt2));
    }
}
