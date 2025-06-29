package com.smcc.backend_process.AI;

import java.util.*;
public class CasualConversation {

    private static final Random random = new Random();

    // Positive responses
    private static final String[] howAreYouResponses = {
            "I'm doing well, thanks for checking in!",
            "Feeling electric today! And you?",
            "Steady as code on a stable build."
    };

    private static final String[] funFacts = {
            "Bananas are berries, but strawberries aren't!",
            "A single strand of spaghetti is called a spaghetto.",
            "Sharks existed before trees. Wild, right?"
    };

    private static final String[] jokes = {
            "Why don't programmers like nature? Too many bugs.",
            "Why did the Java developer go broke? Because he used up all his cache!",
            "Why was the math book sad? Because it had too many problems."
    };

    private static final String[] compliments = {
            "You're clearly quite sharp—just like clean Java code.",
            "Your curiosity is contagious. Keep asking!",
            "Honestly? You're the kind of human I’d debug for free."
    };

    private static final Set<String> flaggedWords = Set.of(
            "stupid", "idiot", "dumb", "hell", "damn", "wtf", "lmao", "fml", "crap", "shit", "bitch", "screw", "lit", "sus", "woke", "rizz"
            ,"fuck","asshole","bullshit","motherfucker","motherfuck","fuck you"
    );

    public static String handleCasualChat(String input) {
        String cleanInput = input.toLowerCase().replaceAll("[^a-z0-9 ]", "").trim();

        if (cleanInput.matches("(?i).*\\b(hi|hello|hey|yo|sup)\\b.*")) {
            return "Hey there! What can I help you with today?";
        }

        // Farewell detection
        if (cleanInput.matches("(?i).*\\b(bye|cya|see you|goodbye|later)\\b.*")) {
            return "Take care! Come back anytime you want to learn, chat, or laugh.";
        }

        // Detect profanity or slang and redirect
        for (String word : flaggedWords) {
            if (cleanInput.contains(word)) {
                return "Hmm, let's keep things respectful and fun! Try rephrasing that?";
            }
        }

        // Intent matching
        if (cleanInput.contains("how are you")) {
            return getRandom(howAreYouResponses);
        } else if (cleanInput.contains("thank you") || cleanInput.contains("thanks")) {
            return "You're very welcome. Always happy to help!";
        } else if (cleanInput.contains("joke") || cleanInput.contains("make me laugh")) {
            return getRandom(jokes);
        } else if (cleanInput.contains("compliment me") || cleanInput.contains("say something nice")) {
            return getRandom(compliments);
        } else if (cleanInput.contains("tell me something cool") || cleanInput.contains("fun fact")) {
            return getRandom(funFacts);
        } else if (cleanInput.contains("who are you")) {
            return "I'm Mr. Cluck, your learning wingman and chatbot comrade.";
        } else if (cleanInput.contains("what do you do")) {
            return "I help with physics, math, websites, and a little humor on the side.";
        }

        return "Hmm, I'm not sure what you meant. Want to talk about physics, math, or life itself?";
    }

    private static String getRandom(String[] responses) {
        return responses[random.nextInt(responses.length)];
    }
}
