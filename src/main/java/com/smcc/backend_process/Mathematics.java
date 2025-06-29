package com.smcc.backend_process;

import com.smcc.app_interfaces.SolverFunction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Mathematics is your central dispatcher for all math‐related inputs.
 * At the top, it recognizes custom commands from WordProblemParser
 * (average:, percent:, interest:, ratio:) and handles them directly.
 * If no custom command is found, it falls back to the regex‐based
 * arithmetic, algebra, trigonometry, Bayes, etc., and finally equations.
 */


public class Mathematics implements SolverFunction {

        // === PATTERN DEFINITIONS ===

        // 1) Probability & Bayes
        private static final Pattern BAYES = Pattern.compile(
                "(?i)^bayes\\(([^,]+),([^,]+),([^\\)]+)\\)$");
        private static final Pattern PROB_FRACTION = Pattern.compile("^P=(\\d+)/(\\d+)$");
        private static final Pattern PROB_FUNC     = Pattern.compile("(?i)^probability\\(([^,]+),([^\\)]+)\\)$");

        // 2) Trig & Roots
        private static final Pattern TRIG      = Pattern.compile("(?i)^(sin|cos|tan)\\((-?\\d*\\.?\\d+)\\)$");
        private static final Pattern SQRT      = Pattern.compile("(?i)^(sqrt|√)\\((-?\\d*\\.?\\d+)\\)$");
        private static final Pattern CBRT      = Pattern.compile("(?i)^(cbrt|∛)\\((-?\\d*\\.?\\d+)\\)$");

        // 3) Arithmetic & Fractions
        private static final Pattern FRACTION_OP = Pattern.compile("(-?\\d+/\\d+)([+\\-*/])(-?\\d+/\\d+)");
        private static final Pattern ARITHMETIC  = Pattern.compile("^(-?\\d*\\.?\\d+)([+\\-*/])(-?\\d*\\.?\\d+)$");

        // 4) HCF/LCM
        private static final Pattern HCF_LCM = Pattern.compile("(?i)^(hcf|lcm)\\((\\d+),(\\d+)\\)$");

        // 5) Equation watermark
        private static final Pattern HAS_EQUALS = Pattern.compile(".*=.*");


        public static String autoSolve(String input) {
            if (input == null || input.trim().isEmpty()) {
                return "Empty input.";
            }

            // Normalize and remove whitespace
            String eq = input.trim().replaceAll("\\s+", "");
            StringBuilder out = new StringBuilder();

            // 1) Direct pattern-matched shortcuts
            if (SolverFunction.matchAndSolve(BAYES.pattern(),    eq, 0, out, Mathematics::solveBayes))
                return out.toString();
            if (SolverFunction.matchAndSolve(PROB_FRACTION.pattern(), eq, 0, out, Mathematics::solveProbability))
                return out.toString();
            if (SolverFunction.matchAndSolve(PROB_FUNC.pattern(),     eq, 0, out, Mathematics::solveProbability))
                return out.toString();

            if (SolverFunction.matchAndSolve(TRIG.pattern(),   eq, 0, out, Mathematics::solveTrigonometry))
                return out.toString();
            if (SolverFunction.matchAndSolve(SQRT.pattern(),   eq, 0, out, Mathematics::solveRoot))
                return out.toString();
            if (SolverFunction.matchAndSolve(CBRT.pattern(),   eq, 0, out, Mathematics::solveRoot))
            return out.toString();

            if (SolverFunction.matchAndSolve(FRACTION_OP.pattern(), eq, 0, out, Mathematics::solveFractionOperation))
                return out.toString();
            if (SolverFunction.matchAndSolve(ARITHMETIC.pattern(),  eq, 0, out, Mathematics::solveArithmetic))
                return out.toString();

            if (SolverFunction.matchAndSolve(HCF_LCM.pattern(), eq, 0, out, Mathematics::solveHcfLcm))
                return out.toString();

            // 2) Simplify root literals inside a larger expression (optional)
            eq = simplifyRoots(eq);

            // 3) Equation mode (if there’s an '=' sign)
            if (HAS_EQUALS.matcher(eq).matches()) {
                return solveEquation(eq);
            }

            // 4) Single numeric value
            if (eq.matches("^-?\\d+(\\.\\d+)?$")) {
                return "Result: " + eq;
            }

            // 5) Fallback
            return "Could not understand or solve: \"" + input + "\". Please check format.";
        }

    /**
     * Attempts to solve an equation of one of three forms:
     *   1) Linear:     a·x + b = c
     *   2) Quadratic:  a·x² + b·x + c = 0
     *   3) Inequality: a·x + b  op  c   (op ∈ {<,≤,>,≥})
     *
     * @param eq  the equation string, with no spaces (e.g. "5x+3=18", "x^2-3x+2=0", "2x-4<=8")
     * @return a human‐readable solution or an unrecognized‐format message
     */
    private static String solveEquation(String eq) {
       //Linear
        Pattern linear = Pattern.compile(
                "^([+-]?(?:\\d*\\.?\\d+)?)[xX]([+-]?\\d*\\.?\\d*)=([+-]?\\d*\\.?\\d+)$"
        );
        Matcher m = linear.matcher(eq);
        if (m.matches()) {

            String aStr = m.group(1);
            double a = (aStr.isEmpty() || aStr.equals("+") || aStr.equals("-"))
                    ? (aStr.equals("-") ? -1 : 1)
                    : Double.parseDouble(aStr);


            double b = m.group(2).isEmpty() ? 0 : Double.parseDouble(m.group(2));
            double c = Double.parseDouble(m.group(3));

            double x = (c - b) / a;
            return String.format("x = %.4f", x);
        }

        // 2) Quadratic:
        Pattern quad = Pattern.compile(
                "^([+-]?\\d*\\.?\\d+)[xX]\\^2([+-]?\\d*\\.?\\d+)[xX]([+-]?\\d*\\.?\\d+)=0$"
        );
        StringBuilder out = new StringBuilder();
        if (SolverFunction.matchAndSolve(quad.pattern(), eq, 0, out, Mathematics::solveQuadratic)) {
            return out.toString();
        }

        // 3) Inequality:
        Pattern ineq = Pattern.compile(
                "^([+-]?(?:\\d*\\.?\\d+)?)[xX]([+-]?\\d*\\.?\\d*)(<=|>=|<|>)([+-]?(?:\\d*\\.?\\d+))$"
        );
        m = ineq.matcher(eq);
        if (m.matches()) {
            return solveInequality(
                    m.group(1),  // a
                    m.group(2),  // b
                    m.group(3),  // operator
                    m.group(4)   // c
            );
        }

        // 4) Fallback
        return "Equation detected but unrecognized format: \"" + eq + "\".";
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

    private static String solveRoot(Matcher m, StringBuilder out) {
        String func = m.group(1).toLowerCase();
        double value = Double.parseDouble(m.group(2));
        double result;

        switch (func) {       //UnI-  U+221A and U+221B(Sq and Cb rt)
            case "sqrt", "√", "square_root_of" -> {
                if (value < 0) return "Square root of negative number is imaginary.";
                result = Math.sqrt(value);
                out.append("√").append(value).append(" = ").append(String.format("%.4f", result));
            }
            case "cbrt", "∛", "cube_root_of" -> {
                result = Math.cbrt(value);
                out.append("∛").append(value).append(" = ").append(String.format("%.4f", result));
            }
            default -> {
                return "Unsupported root function.";
            }
        }
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
    private static String simplifyRoots(String input) {
        // sqrt(x) ➜ decimal
        Pattern sqrtPattern = Pattern.compile("sqrt\\(([^)]+)\\)");
        Matcher sqrtMatcher = sqrtPattern.matcher(input);
        StringBuffer sqrtBuffer = new StringBuffer();

        while (sqrtMatcher.find()) {
            try {
                double val = Double.parseDouble(sqrtMatcher.group(1));
                String result = String.valueOf(Math.sqrt(val));
                sqrtMatcher.appendReplacement(sqrtBuffer, Matcher.quoteReplacement(result));
            } catch (Exception e) {
                sqrtMatcher.appendReplacement(sqrtBuffer, Matcher.quoteReplacement("sqrt(" + sqrtMatcher.group(1) + ")"));
            }
        }
        sqrtMatcher.appendTail(sqrtBuffer);
        input = sqrtBuffer.toString();

        // x^(1/2) ➜ decimal
        Pattern powerHalf = Pattern.compile("([\\d.]+)\\^\\(1/2\\)");
        Matcher powerMatcher = powerHalf.matcher(input);
        StringBuffer powerBuffer = new StringBuffer();

        while (powerMatcher.find()) {
            try {
                double val = Double.parseDouble(powerMatcher.group(1));
                String result = String.valueOf(Math.sqrt(val));
                powerMatcher.appendReplacement(powerBuffer, Matcher.quoteReplacement(result));
            } catch (Exception e) {
                powerMatcher.appendReplacement(powerBuffer, Matcher.quoteReplacement(powerMatcher.group()));
            }
        }
        powerMatcher.appendTail(powerBuffer);
        return powerBuffer.toString();
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

    private static String solveInequality(String aStr, String bStr, String op, String cStr) {
        // parse A (handle implicit ±1)
        double a;
        if (aStr == null || aStr.isEmpty() || aStr.equals("+")) {
            a = 1;
        } else if (aStr.equals("-")) {
            a = -1;
        } else {
            a = Double.parseDouble(aStr);
        }

        // parse B (empty → 0)
        double b = (bStr == null || bStr.isEmpty())
                ? 0
                : Double.parseDouble(bStr);

        // parse C
        double c = Double.parseDouble(cStr);

        // compute rhs = (c – b)/a
        double rhs = (c - b) / a;

        // if A is negative, flip the inequality direction
        String finalOp = op;
        if (a < 0) {
            finalOp = switch (op) {
                case "<"  -> ">";
                case "<=" -> ">=";
                case ">"  -> "<";
                case ">=" -> "<=";
                default   -> op;
            };
        }

        return String.format("Solution: x %s %.4f", finalOp, rhs);
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
