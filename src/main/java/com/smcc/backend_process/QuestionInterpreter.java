package com.smcc.backend_process;

/**
 * QuestionInterpreter routes user queries:
 * 1) physics word‐problems via WordProblemParser → Physics.autoSolvePhysics
 * 2) math word‐problems via WordProblemParser → Mathematics.autoSolve
 * 3) pure‐expression math → Mathematics.autoSolve
 * 4) Wikipedia lookups via Question + JWiki
 * 5) fallback to casual conversation prompt
 */
public class QuestionInterpreter {

    public static String dectQuestion(String input) {
        if (input == null || input.isBlank()) {
            return "Hmm, I didn't catch that. Can you rephrase it?";
        }

        // 0) Normalize and strip common math verbs once
        String normalized = input
                .replaceFirst("(?i)^(solve|calculate|evaluate|find value of)[-:\\s]*", "")
                .trim();

        // 1) Physics word-problems
        String physCmd = WordProblemParser.parsePhysicsProblem(normalized);
        if (physCmd != null) {
            return Physics.autoSolvePhysics(physCmd);
        }

        // 2) Math word-problems
        String mathCmd = WordProblemParser.parseMathProblem(normalized);
        if (mathCmd != null) {
            return Mathematics.autoSolve(mathCmd);
        }

        // 3) Pure-expression math
        if (looksLikeMath(normalized)) {
            return Mathematics.autoSolve(normalized);
        }

        // 4) Wiki queries
        Question q = new Question(normalized);
        if (q.isWikiQuery()) {
            return q.getSummary();
        }

        // 5) Fallback casual
        return "I couldn’t quite tell if you're asking about physics, math, or something else. "
                + "You can try: `solve x^2+5x+6=0` or `what is average speed if...`";
    }

    private static boolean looksLikeMath(String s) {
        // detect digits combined with math operators or x^n forms
        String noSpace = s.replaceAll("\\s+", "");
        return noSpace.matches(".*\\d+.*([+\\-*/^=x]).*");
    }
}