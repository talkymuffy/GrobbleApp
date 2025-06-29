package com.smcc.backend_process;

import com.smcc.app_interfaces.SolverFunction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mathematics implements SolverFunction {

    public static String autoSolve(String input)  {
        if (input == null || input.trim().isEmpty()) return "Empty input.";

        String eq = input.trim().replaceAll("\\s+", "");
        StringBuilder out = new StringBuilder();

        // === Probabilities ===
        if (SolverFunction.matchAndSolve("(?i)^bayes\\(([^,]+),([^,]+),([^\\)]+)\\)$", eq, 0, out, Mathematics::solveBayes)) return out.toString();
        if (SolverFunction.matchAndSolve("^P=([\\d.]+)/([\\d.]+)$", eq, 0, out, Mathematics::solveProbability)) return out.toString();
        if (SolverFunction.matchAndSolve("(?i)^probability\\(([^,]+),([^\\)]+)\\)$", eq, 0, out, Mathematics::solveProbability)) return out.toString();

        // === Trigonometric Functions ===
        if (SolverFunction.matchAndSolve("(?i)^(sin|cos|tan)\\((-?\\d*\\.?\\d+)\\)$", eq, 0, out, Mathematics::solveTrigonometry)) return out.toString();

        // === Fractions ===
        if (SolverFunction.matchAndSolve("(-?\\d+/\\d+)([+\\-*/])(-?\\d+/\\d+)", eq, 0, out, Mathematics::solveFractionOperation)) return out.toString();

        // === Basic Arithmetic ===
        if (SolverFunction.matchAndSolve("(-?\\d*\\.?\\d+)([+\\-*/])(-?\\d*\\.?\\d+)", eq, 0, out, Mathematics::solveArithmetic)) return out.toString();

        // === HCF and LCM ===
        if (SolverFunction.matchAndSolve("(?i)^(hcf|lcm)\\(([^,]+),([^\\)]+)\\)$", eq, 0, out, Mathematics::solveHcfLcm)) return out.toString();

        // === Algebra: Linear, Inequalities, Quadratics ===
        if (SolverFunction.matchAndSolve("([-+]?\\d*\\.?\\d*)x([+\\-]\\d*\\.?\\d*)=0", eq, 0, out, Mathematics::solveLinear)) return out.toString();
        if (SolverFunction.matchAndSolve("([-+]?\\d*\\.?\\d*)\\*?x([+\\-]\\d*\\.?\\d+)?(<=|>=|<|>)0", eq, 0, out, Mathematics::solveInequality)) return out.toString();
        if (SolverFunction.matchAndSolve("([-+]?\\d*\\.?\\d*)x\\^2([+\\-]\\d*\\.?\\d*)x([+\\-]\\d*\\.?\\d*)=0", eq, 0, out, Mathematics::solveQuadratic)) return out.toString();

        // === Catch-All ===
        return "Could not understand or solve the equation. Please check the format.";
    }


    private static String solveProbability(Matcher m, StringBuilder out) {
        double favourable = Double.parseDouble(m.group(1).trim());
        double total = Double.parseDouble(m.group(2).trim());

        if (total == 0) {
            return "Invalid: Total outcomes cannot be zero.";
        }

        double probability = favourable / total;
        out.append("Probability = ")
                .append(favourable).append(" / ").append(total)
                .append(" = ").append(String.format("%.4f", probability));
        return out.toString();
    }

    private static String solveBayes(Matcher m, StringBuilder out) {
        double pA = Double.parseDouble(m.group(1).trim());
        double pBgivenA = Double.parseDouble(m.group(2).trim());
        double pB = Double.parseDouble(m.group(3).trim());

        if (pB == 0) return "Undefined: P(B) cannot be zero.";

        double result = (pBgivenA * pA) / pB;
        out.append("P(A|B) = ").append(String.format("%.4f", result));
        return out.toString();
    }

    private static String solveQuadratic(Matcher m, StringBuilder out) {
        double A = Double.parseDouble(m.group(1).isEmpty() ? "1" : m.group(1));
        double B = Double.parseDouble(m.group(2));
        double C = Double.parseDouble(m.group(3));
        double D = B * B - 4 * A * C;

        if (D > 0) {
            double r1 = (-B + Math.sqrt(D)) / (2 * A);
            double r2 = (-B - Math.sqrt(D)) / (2 * A);
            out.append("Two real roots: ").append(String.format("%.2f", r1)).append(", ").append(String.format("%.2f", r2));
        } else if (D == 0) {
            out.append("One real root: ").append(String.format("%.2f", -B / (2 * A)));
        } else {
            out.append("No real roots.");
        }
        return out.toString();
    }

    private static String solveLinear(Matcher m, StringBuilder out) {
        double A = Double.parseDouble(m.group(1).isEmpty() ? "1" : m.group(1));
        double B = Double.parseDouble(m.group(2));
        out.append(A == 0 ? (B == 0 ? "Infinite solutions." : "No solution.") : "x = " + String.format("%.2f", -B / A));
        return out.toString();
    }

    private static String solveInequality(Matcher m, StringBuilder out) {
        double A = Double.parseDouble(m.group(1).isEmpty() ? "1" : m.group(1));
        double B = Double.parseDouble(m.group(2) == null ? "0" : m.group(2));
        String op = m.group(3);
        out.append("Solution: x ").append(op).append(" ").append(String.format("%.2f", -B / A));
        return out.toString();
    }

    private static String solveHcfLcm(Matcher m, StringBuilder out) {
        int a = Integer.parseInt(m.group(2).trim());
        int b = Integer.parseInt(m.group(3).trim());
        int hcf = gcd(a, b);
        int lcm = (a * b) / hcf;
        out.append("HCF: ").append(hcf).append(", LCM: ").append(lcm);
        return out.toString();
    }

    private static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    private static String solveArithmetic(Matcher m, StringBuilder out) {
        double a = Double.parseDouble(m.group(1));
        double b = Double.parseDouble(m.group(3));
        String op = m.group(2);
        double res = switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> (b != 0) ? a / b : Double.NaN;
            default -> Double.NaN;
        };
        out.append("Result: ").append(String.format("%.4f", res));
        return out.toString();
    }

    private static String solveFractionOperation(Matcher m, StringBuilder out) {
        String[] f1 = m.group(1).split("/");
        String[] f2 = m.group(3).split("/");
        int n1 = Integer.parseInt(f1[0].trim()), d1 = Integer.parseInt(f1[1].trim());
        int n2 = Integer.parseInt(f2[0].trim()), d2 = Integer.parseInt(f2[1].trim());
        String op = m.group(2).trim();

        int num = 0, den = 0;
        switch (op) {
            case "+" -> {
                num = n1 * d2 + n2 * d1;
                den = d1 * d2;
            }
            case "-" -> {
                num = n1 * d2 - n2 * d1;
                den = d1 * d2;
            }
            case "*" -> {
                num = n1 * n2;
                den = d1 * d2;
            }
            case "/" -> {
                num = n1 * d2;
                den = n2 * d1;
            }
            default -> out.append("Invalid operation.");
        }

        int g = gcd(num, den);
        out.append("Result: ").append(num / g).append("/").append(den / g);
        return out.toString();
    }

    private static String solveTrigonometry(Matcher m, StringBuilder out) {
        String func = m.group(1).toLowerCase();
        double angle = Math.toRadians(Double.parseDouble(m.group(2)));
        double result;

        switch (func) {
            case "sin" -> result = Math.sin(angle);
            case "cos" -> result = Math.cos(angle);
            case "tan" -> result = Math.tan(angle);
            default -> {
                return "Unsupported trigonometric function. Try sin(x), cos(x), or tan(x).";
            }
        }

        out.append(func).append("(").append(m.group(2)).append("°) = ")
                .append(String.format("%.4f", result));
        return out.toString();
    }

    @Override
    public String apply(Matcher matcher, StringBuilder out) {
        return "";
    }
}
