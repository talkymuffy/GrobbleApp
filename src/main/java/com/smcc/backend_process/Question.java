package com.smcc.backend_process;


public class Question {
    private String rawInput;
    private String topic;
    private String summary;
    private boolean isWikiQuery;

    public Question(String rawInput) {
        this.rawInput = rawInput.trim();
        this.isWikiQuery = detectWikiIntent(rawInput);
        this.topic = extractTopic(rawInput);
    }

    private boolean detectWikiIntent(String input) {
        input = input.toLowerCase();
        return input.contains("wiki") || input.contains("wikipedia") ||
                input.startsWith("tell me about") || input.startsWith("what is") ||
                input.startsWith("who is") || input.startsWith("define") ||
                input.contains("search wikipedia for");
    }

    private String extractTopic(String input) {
        // Very basic logic – can be upgraded to NLP
        input = input.replaceAll("(?i)(tell me about|search wikipedia for|what is|who is|define)", "").trim();
        return input.replaceAll("[^a-zA-Z0-9 '\\-]", "").strip();
    }

    // === Getters and Setters ===
    public String getRawInput() { return rawInput; }
    public String getTopic() { return topic; }
    public boolean isWikiQuery() { return isWikiQuery; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getSummary() { return summary; }
}