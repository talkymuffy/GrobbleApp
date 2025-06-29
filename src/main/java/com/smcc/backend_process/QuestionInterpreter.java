package com.smcc.backend_process;

public class QuestionInterpreter {

    public static String dectQuestion(String input) {
        if (input == null || input.trim().isEmpty()) return "Empty input.";

        String lower = input.trim().toLowerCase();
        String expression = extractExpression(input);

        // Expanded Physics Keyword Detection
        boolean isPhysics =
                lower.contains("physics") || lower.contains("solve physics") ||
                        lower.startsWith("phy-") ||
                        lower.contains("projectile") || lower.contains("range") || lower.contains("maximum height") ||
                        lower.contains("newton") || lower.contains("force") || lower.contains("inertia") || lower.contains("laws of motion") ||
                        lower.contains("voltage") || lower.contains("current") || lower.contains("resistance") ||
                        lower.contains("magnet") || lower.contains("flux") || lower.contains("magnetic field") ||
                        lower.contains("gravity") || lower.contains("gravitational") || lower.contains("weight") ||
                        lower.contains("torque") || lower.contains("moment of inertia") || lower.contains("angular") ||
                        lower.contains("velocity") || lower.contains("speed") || lower.contains("displacement") || lower.contains("acceleration") ||
                        lower.contains("shm") || lower.contains("spring constant") || lower.contains("oscillation") ||
                        lower.contains("thermo") || lower.contains("heat") || lower.contains("temperature change") ||
                        lower.contains("elastic") || lower.contains("strain") || lower.contains("stress") ||
                        lower.contains("buoyancy") || lower.contains("viscosity") || lower.contains("fluid") ||
                        lower.contains("vector") || lower.contains("dot product") || lower.contains("angle between");

        // Math Intent Detection (unchanged)
        boolean isMath = lower.contains("solve") ||
                lower.contains("equate") ||
                (lower.contains("find") && lower.contains("value")) ||
                lower.matches(".*\\d.*[+\\-*/^=x].*\\d.*");

        if (isPhysics) {
            return (expression != null && !expression.isEmpty())
                    ? Physics.autoSolvePhysics(expression)
                    : "Formatting error: Include a physics expression after ':' or '-'.";
        }

        if (isMath) {
            return (expression != null && !expression.isEmpty())
                    ? Mathematics.autoSolve(expression)
                    : "Formatting error: Include a math expression after ':' or '-'.";
        }

        return "Sorry, I couldn’t tell if your question was about math or physics. Try using keywords like 'solve', 'physics:', or 'find value...'";
    }

    private static String extractExpression(String input) {
        if (input.contains(":")) return input.substring(input.indexOf(':') + 1).trim();
        if (input.contains("-")) return input.substring(input.indexOf('-') + 1).trim();

        // NEW: Handle "find value of ..." pattern
        String lower = input.toLowerCase().trim();
        if (lower.startsWith("find value of")) {
            return input.substring(lower.indexOf("find value of") + "find value of".length()).trim();
        }

        return input.trim(); // fallback
    }

}