package com.smcc.backend_process.AI;

import com.smcc.backend_process.Question;
import com.smcc.backend_process.QuestionInterpreter;
import io.github.fastily.jwiki.core.Wiki;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NaturalLanguageProcessor is the top‐level gateway for all user messages.
 * It now also handles casual greetings, even if they're followed by a
 * secondary query joined with "then" or punctuation.
 *
 * Flow:
 *  1) Greeting only (e.g. "Hi!", "Hello.") → reply greeting.
 *  2) Greeting + question (e.g. "Hi! then What is a bomb?")
 *     reply greeting + process question.
 *  3) Wikipedia queries via Question + JWiki.
 *  4) Link searches via LinkProcessor.
 *  5) All other queries → QuestionInterpreter.dectQuestion().
 *
 */
public class NaturalLanguageProcessor {

    // Matches a greeting, capturing any trailing text
    private static final Pattern GREETING_SPLIT = Pattern.compile(
            "(?i)^(hi|hello|hey)[!\\.\\s]*(.*)$"
    );
    // Detects pure greeting
    private static final Pattern GREETING_ONLY = Pattern.compile(
            "(?i)^(hi|hello|hey)[!\\.\\s]*$"
    );

    /**
     * Analyze and route the input text.
     *
     * @param input raw user message
     * @return the assistant's response
     */
    public String analyzeText(String input) {
        if (input == null || input.isBlank()) {
            return "I'm listening... feel free to ask anything.";
        }

        String trimmed = input.trim();

        // 1) Greeting only?  e.g. "Hi!", "hello"
        if (GREETING_ONLY.matcher(trimmed).matches()) {
            return randomGreeting();
        }

        // 2) Greeting + follow-on?  e.g. "Hi! then what is a bomb?"
        Matcher gm = GREETING_SPLIT.matcher(trimmed);
        if (gm.matches() && !gm.group(2).isBlank()) {
            String greet = gm.group(1);
            String rest  = gm.group(2).trim();

            // Build combined response
            StringBuilder resp = new StringBuilder();
            resp.append(capitalize(greet)).append("! ");
            // Process the remainder as a full query
            resp.append(analyzeText(rest));
            return resp.toString();
        }

        // 3) Wikipedia queries (fast‐track)
        Question q = new Question(trimmed);
        if (q.isWikiQuery()) {
            Wiki wiki = new Wiki.Builder().build();
            String summary = wiki.getTextExtract(q.getTopic());
            if (summary == null || summary.isBlank()) {
                return "I couldn’t find a good summary for \"" + q.getTopic() + "\".";
            }
            return summary;
        }

        // 4) Link / URL requests
        if (looksLikeLink(trimmed)) {
            String link = LinkProcessor.interpretSearch(trimmed);
            return (link != null)
                    ? link
                    : "Sorry, I couldn't recognize the website you're trying to open.";
        }

        // 5) Delegate everything else
        return QuestionInterpreter.dectQuestion(trimmed);
    }

    /**
     *  picks a friendly greeting.
     */
    private String randomGreeting() {
        String[] options = {
                "Hello there!",
                "Hi! How can I assist you today?",
                "Hey! What's on your mind?"
        };
        int idx = (int) (Math.random() * options.length);
        return options[idx];
    }

    /**
     * Capitalize first letter of a word (for consistency).
     */
    private String capitalize(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();
    }

    /**
     * Heuristic to detect web/search links.
     */
    private boolean looksLikeLink(String s) {
        String lower = s.toLowerCase();
        return lower.matches(".*(https?://|www\\.|\\.com|youtube\\.com|github\\.com|stackoverflow).*")
                || lower.startsWith("search for ")
                || lower.startsWith("open ");
    }
}