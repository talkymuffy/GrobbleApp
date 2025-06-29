package com.smcc.backend_process.AI;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class LinkProcessor {

    private static final Map<String, String> searchEngines = Map.of(
            "google", "https://www.google.com/search?q=%s",
            "youtube", "https://www.youtube.com/results?search_query=%s",
            "duckduckgo", "https://duckduckgo.com/?q=%s",
            "bing", "https://www.bing.com/search?q=%s",
            "edge", "https://www.bing.com/search?q=%s" // Edge uses Bing
    );

    private static final Map<String, String> websiteShortcuts = Map.of(
            "github", "https://github.com/%s",
            "stackoverflow", "https://stackoverflow.com/search?q=%s",
            "reddit", "https://www.reddit.com/r/%s",
            "wikipedia", "https://en.wikipedia.org/wiki/%s"
    );

    public static String interpretSearch(String userInput) {
        String input = userInput.toLowerCase();

        // Search intent: "search for <X> in <Y>"
        if (input.contains("search for") && input.contains(" in ")) {
            String raw = input.replace("search for", "").trim();
            String[] parts = raw.split(" in ");
            if (parts.length == 2) {
                String keyword = urlEncode(parts[0].trim());
                String site = parts[1].trim().toLowerCase();
                if (searchEngines.containsKey(site)) {
                    return "Here's what I found:\n" + String.format(searchEngines.get(site), keyword);
                }
            }
        }

        // Shortcut intent: "open github home" or "lookup wiki topic"
        for (String site : websiteShortcuts.keySet()) {
            if (input.contains("open " + site)) {
                return "Opening homepage:\nhttps://" + site + ".com";
            } else if (input.contains(site) && input.contains(" for ")) {
                String[] split = input.split(" for ");
                if (split.length == 2) {
                    String keyword = urlEncode(split[1].trim());
                    return " Looking it up on " + site + ":\n" + String.format(websiteShortcuts.get(site), keyword);
                }
            }
        }

        // Literal link input
        if (input.contains("http://") || input.contains("https://") || input.contains("www.")) {
            return "That looks like a URL:\n" + extractLink(userInput);
        }

        return null; // No URL or search intent found
    }

    private static String extractLink(String text) {
        return text.replaceAll(".*(http[s]?://\\S+).*", "$1");
    }

    private static String urlEncode(String text) {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }
}
