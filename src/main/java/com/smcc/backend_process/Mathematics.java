package com.smcc.backend_process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mathematics {
    // ... [All previous methods remain unchanged] ...

    /**
     * Accepts a string equation, detects its type (quadratic, linear equation, linear inequality, arithmetic, fraction, probability, bayes, trigonometry),
     * and solves it using the appropriate method.
     * Supported:
     * - Quadratic: ax^2 + bx + c = 0
     * - Linear: ax + b = 0
     * - Linear inequalities: ax + b < 0, ax + b <= 0, ax + b > 0, ax + b >= 0
     * - Arithmetic: a + b, a - b, a * b, a / b
     * - Fraction: a/b + c/d, a/b - c/d, a/b * c/d, a/b / c/d
     * - Probability: P = fav / total
     * - Bayes: bayes(pA, pBgivenA, pBgivenNotA)
     * - Trigonometry: sin(x), cos(x), tan(x), asin(x), arcsin(x), etc.
     */
    public static void autoSolve(String equation) {
        String eq = equation.trim();

        // Try Bayes format: bayes(pA, pBgivenA, pBgivenNotA)
        Pattern bayesPat = Pattern.compile("^\\s*bayes\\s*\\(([^,]+),([^,]+),([^\\)]+)\\)\\s*$", Pattern.CASE_INSENSITIVE);
        Matcher bayesMat = bayesPat.matcher(eq);
        if (bayesMat.matches()) {
            solveBayes(bayesMat.group(1), bayesMat.group(2), bayesMat.group(3));
            return;
        }

        // Try probability: P = a / b or probability(a, b)
        Pattern probPat = Pattern.compile(
            "^\\s*P\\s*=\\s*([\\d\\.]+)\\s*/\\s*([\\d\\.]+)\\s*$", Pattern.CASE_INSENSITIVE);
        Matcher probMat = probPat.matcher(eq);
        if (probMat.matches()) {
            solveProbability(probMat.group(1), probMat.group(2));
            return;
        }
        Pattern probFuncPat = Pattern.compile(
            "^\\s*probability\\s*\\(([^,]+),([^\\)]+)\\)\\s*$", Pattern.CASE_INSENSITIVE);
        Matcher probFuncMat = probFuncPat.matcher(eq);
        if (probFuncMat.matches()) {
            solveProbability(probFuncMat.group(1), probFuncMat.group(2));
            return;
        }

        // Try trigonometry: sin(x), cos(x), tan(x), asin(x), arcsin(x), etc.
        Pattern trigPat = Pattern.compile("^\\s*([a-zA-Z]+)\\s*\\(\\s*([-+]?\\d*\\.?\\d+)\\s*\\)\\s*$");
        Matcher trigMat = trigPat.matcher(eq);
        if (trigMat.matches()) {
            solveTrigonometry(trigMat.group(2), trigMat.group(1));
            return;
        }

        // Try fraction arithmetic: a/b [+, -, *, /] c/d
        Pattern fracPat = Pattern.compile(
            "^\\s*(-?\\d+\\s*/\\s*-?\\d+|\\d+)\\s*([+\\-*/])\\s*(-?\\d+\\s*/\\s*-?\\d+|\\d+)\\s*$");
        Matcher fracMat = fracPat.matcher(eq);
        if (fracMat.matches()) {
            solveFractionOperation(fracMat.group(1), fracMat.group(3), fracMat.group(2));
            return;
        }

        // Try integer arithmetic: a + b, a - b, a * b, a / b
        Pattern arithPat = Pattern.compile(
            "^\\s*(-?\\d*\\.?\\d+)\\s*([+\\-*/])\\s*(-?\\d*\\.?\\d+)\\s*$");
        Matcher arithMat = arithPat.matcher(eq);
        if (arithMat.matches()) {
            solveArithmetic(arithMat.group(1), arithMat.group(3), arithMat.group(2));
            return;
        }

        // Try HCF/LCM: hcf(a, b) or lcm(a, b)
        Pattern hcfLcmPat = Pattern.compile("^\\s*(hcf|lcm)\\s*\\(([^,]+),([^\\)]+)\\)\\s*$", Pattern.CASE_INSENSITIVE);
        Matcher hcfLcmMat = hcfLcmPat.matcher(eq);
        if (hcfLcmMat.matches()) {
            solveHcfLcm(hcfLcmMat.group(2), hcfLcmMat.group(3));
            return;
        }

        // Try linear inequality: ax + b < 0 or <=, >, >=
        Pattern ineqPat = Pattern.compile(
            "^\\s*([-+]?\\d*\\.?\\d*)\\s*\\*?x\\s*([+\\-]\\s*\\d*\\.?\\d+)?\\s*(<|<=|>|>=)\\s*0\\s*$");
        Matcher ineqMat = ineqPat.matcher(eq.replaceAll("\\s+", ""));
        if (ineqMat.matches()) {
            String a = ineqMat.group(1).isEmpty() ? "1" : ineqMat.group(1);
            String b = ineqMat.group(2) == null ? "0" : ineqMat.group(2).replace(" ", "");
            String op = ineqMat.group(3);
            solveInequality(a, b, op);
            return;
        }

        // Try quadratic: ax^2 + bx + c = 0
        Pattern quadPat = Pattern.compile(
            "^\\s*([-+]?\\d*\\.?\\d*)x\\^2\\s*([+\\-]\\s*\\d*\\.?\\d*)x\\s*([+\\-]\\s*\\d*\\.?\\d*)\\s*=\\s*0\\s*$");
        Matcher quadMat = quadPat.matcher(eq.replaceAll("\\s+", ""));
        if (quadMat.matches()) {
            String a = quadMat.group(1).isEmpty() ? "1" : quadMat.group(1);
            String b = quadMat.group(2).replace(" ", "");
            String c = quadMat.group(3).replace(" ", "");
            solveQuadratic(a, b, c);
            return;
        }

        // Try linear: ax + b = 0
        Pattern linPat = Pattern.compile(
            "^\\s*([-+]?\\d*\\.?\\d*)x\\s*([+\\-]\\s*\\d*\\.?\\d*)\\s*=\\s*0\\s*$");
        Matcher linMat = linPat.matcher(eq.replaceAll("\\s+", ""));
        if (linMat.matches()) {
            String a = linMat.group(1).isEmpty() ? "1" : linMat.group(1);
            String b = linMat.group(2).replace(" ", "");
            solveLinear(a, b);
            return;
        }

        // If not detected
        System.out.println("Could not detect or solve the equation type. Please check your input.");
    }

    // ... [Other methods: solveQuadratic, solveLinear, solveInequality, etc. remain unchanged] ...
}
