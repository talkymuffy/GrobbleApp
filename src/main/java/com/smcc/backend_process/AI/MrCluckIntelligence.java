package com.smcc.backend_process.AI;



public class MrCluckIntelligence {

    public static String processUserMessage(String input) {
            if (input == null || input.trim().isEmpty()) {
                return "Type something and I'll try to help or chat";
            }
            // Delegate to the NLP-powered core
            NaturalLanguageProcessor nlp = new NaturalLanguageProcessor();
            return nlp.analyzeText(input);
    }

}