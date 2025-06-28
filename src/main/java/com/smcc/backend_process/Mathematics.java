package com.smcc.backend_process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mathematics {

    public static String autoSolve(String equation) {
        StringBuilder chatOutput = new StringBuilder();
        String eq = equation.trim();

        // Bayes format
        Matcher bayesMat = Pattern.compile("^\\s*bayes\\s*\\(([^,]+),([^,]+),([^\\)]+)\\)\\s*$", Pattern.CASE_INSENSITIVE).matcher(eq);
        if (bayesMat.matches()) return solveBayes(bayesMat, chatOutput);

        // Probability
        Matcher probMat = Pattern.compile("^\\s*P\\s*=\\s*([\\d\\.]+)\\s*/\\s*([\\d\\.]+)\\s*$", Pattern.CASE_INSENSITIVE).matcher(eq);
        if (probMat.matches()) return solveProbability(probMat, chatOutput);

        Matcher probFuncMat = Pattern.compile("^\\s*probability\\s*\\(([^,]+),([^\\)]+)\\)\\s*$", Pattern.CASE_INSENSITIVE).matcher(eq);
        if (probFuncMat.matches()) return solveProbability(probFuncMat, chatOutput);

        // Trigonometry
        Matcher trigMat = Pattern.compile("^\\s*([a-zA-Z]+)\\s*\\(\\s*([-+]?\\d*\\.?\\d+)\\s*\\)\\s*$")
                .matcher(eq);
        if (trigMat.matches()) return solveTrigonometry(trigMat, chatOutput);

        // Fractions
        Matcher fracMat = Pattern.compile("^\\s*(-?\\d+\\s*/\\s*-?\\d+|\\d+)\\s*([+\\-*/])\\s*(-?\\d+\\s*/\\s*-?\\d+|\\d+)\\s*$")
                .matcher(eq);
        if (fracMat.matches()) return solveFractionOperation(fracMat, chatOutput);

        // Arithmetic
        Matcher arithMat = Pattern.compile("^\\s*(-?\\d*\\.?\\d+)\\s*([+\\-*/])\\s*(-?\\d*\\.?\\d+)\\s*$")
                .matcher(eq);
        if (arithMat.matches()) return solveArithmetic(arithMat, chatOutput);

        // HCF/LCM
        Matcher hcfLcmMat = Pattern.compile("^\\s*(hcf|lcm)\\s*\\(([^,]+),([^\\)]+)\\)\\s*$",
                Pattern.CASE_INSENSITIVE).matcher(eq);
        if (hcfLcmMat.matches()) return solveHcfLcm(hcfLcmMat, chatOutput);

        // Inequality
        Matcher ineqMat = Pattern.compile("^([-+]?\\d*\\.?\\d*)\\*?x([+\\-]\\d*\\.?\\d+)?(<=|>=|<|>)0$")
                .matcher(eq.replaceAll("\\s+", ""));
        if (ineqMat.matches()) return solveInequality(ineqMat, chatOutput);

        // Quadratic
        Matcher quadMat = Pattern.compile("^([-+]?\\d*\\.?\\d*)x\\^2([+\\-]\\d*\\.?\\d*)x([+\\-]\\d*\\.?\\d*)=0$")
                .matcher(eq.replaceAll("\\s+", ""));
        if (quadMat.matches()) return solveQuadratic(quadMat, chatOutput);

        // Linear
        Matcher linMat = Pattern.compile("^([-+]?\\d*\\.?\\d*)x([+\\-]\\d*\\.?\\d*)=0$").matcher(eq.replaceAll("\\s+", ""));
        if (linMat.matches()) return solveLinear(linMat, chatOutput);

        return "Could not understand or solve the equation. Please check the format.";
    }

    // Solver implementations — all return String

    private static String solveBayes(Matcher m, StringBuilder out) {
        double pA = Double.parseDouble(m.group(1).trim());
        double pBgivenA = Double.parseDouble(m.group(2).trim());
        double pB = Double.parseDouble(m.group(3).trim());

        if (pB == 0) {
            return "Undefined: P(B) cannot be zero.";
        }

        double result = (pBgivenA * pA) / pB;
        out.append("P(A|B) = ").append(result);
        return out.toString();
    }

    private static String solveQuadratic(Matcher m, StringBuilder out) {
        double A = Double.parseDouble(m.group(1).isEmpty() ? "1" : m.group(1));
        double B = Double.parseDouble(m.group(2).replace(" ", ""));
        double C = Double.parseDouble(m.group(3).replace(" ", ""));
        double D = B * B - 4 * A * C;

        if (D > 0) {
            double r1 = (-B + Math.sqrt(D)) / (2 * A);
            double r2 = (-B - Math.sqrt(D)) / (2 * A);
            out.append("Two real roots: ").append(r1).append(", ").append(r2);
        } else if (D == 0) {
            out.append("One real root: ").append(-B / (2 * A));
        } else {
            out.append("No real roots.");
        }
        return out.toString();
    }

    private static String solveLinear(Matcher m, StringBuilder out) {
        double A = Double.parseDouble(m.group(1).isEmpty() ? "1" : m.group(1));
        double B = Double.parseDouble(m.group(2).replace(" ", ""));
        if (A == 0) {
            out.append(B == 0 ? "Infinite solutions." : "No solution.");
        } else {
            out.append("x = ").append(-B / A);
        }
        return out.toString();
    }

    private static String solveInequality(Matcher m, StringBuilder out) {
        double A = Double.parseDouble(m.group(1).isEmpty() ? "1" : m.group(1));
        double B = Double.parseDouble(m.group(2) == null ? "0" : m.group(2).replace(" ", ""));
        String op = m.group(3);
        out.append("Solution: x ").append(op).append(" ").append(-B / A);
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
        return b == 0 ? a : gcd(b, a % b);
    }

    private static String solveArithmetic(Matcher m, StringBuilder out) {
        double a = Double.parseDouble(m.group(1));
        double b = Double.parseDouble(m.group(3));
        String op = m.group(2);
        double res;

        switch (op) {
            case "+":
                res = a + b;
                break;
            case "-":
                res = a - b;
                break;
            case "*":
                res = a * b;
                break;
            case "/":
                res = b != 0 ? a / b : Double.NaN;
                break;
            default:
                return "Invalid operation.";
        }

        out.append("Result: ").append(res);
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
            case "+":
                num = n1 * d2 + n2 * d1;
                den = d1 * d2;
                break;
            case "-":
                num = n1 * d2 - n2 * d1;
                den = d1 * d2;
                break;
            case "*":
                num = n1 * n2;
                den = d1 * d2;
                break;
            case "/":
                num = n1 * d2;
                den = n2 * d1;
                break;
            default:
                return "Invalid operation.";
        }

        int g = gcd(num, den);
        out.append("Result: ").append(num / g).append("/").append(den / g);
        return out.toString();
    }

    private static String solveTrigonometry(Matcher m, StringBuilder out) {
        String func = m.group(1).toLowerCase();
        double angle = Math.toRadians(Double.parseDouble(m.group(2)));
        switch (func) {
            case "sin":
                out.append("sin: ").append(Math.sin(angle));
                break;
            case "cos":
                out.append("cos: ").append(Math.cos(angle));
                break;
            case "tan":
                out.append("tan: ").append(Math.tan(angle));
                break;
            default:
                out.append("Unsupported trigonometric function.");
                break;
        }
        return out.toString();
    }

    private static String solveProbability(Matcher m, StringBuilder out) {
        double f = Double.parseDouble(m.group(1).trim());
        double t = Double.parseDouble(m.group(2).trim());

        if (t == 0) {
            return "Invalid: Total outcomes cannot be zero.";
        }

        double probability = f / t;
        out.append("Probability = ").append(f).append(" / ").append(t)
                .append(" = ").append(probability);
        return out.toString();
    }
}