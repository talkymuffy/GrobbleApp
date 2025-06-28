package com.smcc.backend_process;

public class Mathematics {
    //Solve Quadractice, linear eq, inequations, add subtract multiply divide hcf lcm sin cos, tan acos,a sin,acosec and all
    //Fractions,probability(inc. bayes theorem)
    /**
 * Parses three strings as the a, b, c coefficients of a quadratic equation
 * and prints its roots (real or complex). Handles invalid input gracefully.
 *
 * @param aStr the coefficient a (must be a nonzero number)
 * @param bStr the coefficient b
 * @param cStr the coefficient c
 */
public static void solveQuadratic(String aStr, String bStr, String cStr) {
    double a, b, c;
    try {
        a = Double.parseDouble(aStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for 'a'. Must be a number.");
        return;
    }
    if (a == 0) {
        System.out.println("❌ Coefficient 'a' cannot be zero (not a quadratic).");
        return;
    }

    try {
        b = Double.parseDouble(bStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for 'b'. Must be a number.");
        return;
    }

    try {
        c = Double.parseDouble(cStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for 'c'. Must be a number.");
        return;
    }

    double D = b * b - 4 * a * c;
    if (D > 0) {
        double r1 = (-b + Math.sqrt(D)) / (2 * a);
        double r2 = (-b - Math.sqrt(D)) / (2 * a);
        System.out.printf("✔ Two distinct real roots: %.6f, %.6f%n", r1, r2);
    } else if (D == 0) {
        double r = -b / (2 * a);
        System.out.printf("✔ One real (double) root: %.6f%n", r);
    } else {
        double real = -b / (2 * a);
        double imag = Math.sqrt(-D) / (2 * a);
        System.out.printf(
            "✔ Two complex roots: %.6f + %.6fi and %.6f - %.6fi%n",
            real, imag, real, imag
        );
    }
}
    /**
 * Parses two strings as the a, b coefficients of a linear equation
 * a·x + b = 0, handles invalid input, and prints the solution status.
 *
 * @param aStr the coefficient a (must be a number)
 * @param bStr the coefficient b (must be a number)
 */
public static void solveLinear(String aStr, String bStr) {
    double a, b;

    // Parse a
    try {
        a = Double.parseDouble(aStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for 'a'. Must be a valid number.");
        return;
    }

    // Parse b
    try {
        b = Double.parseDouble(bStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for 'b'. Must be a valid number.");
        return;
    }

    // Handle special cases when a == 0
    if (a == 0) {
        if (b == 0) {
            System.out.println("✔ Infinite solutions (0·x + 0 = 0).");
        } else {
            System.out.println("❌ No solution (0·x + " + b + " = 0 is impossible).");
        }
        return;
    }

    // Normal case: one unique solution
    double x = -b / a;
    System.out.printf("✔ Unique solution: x = %.6f%n", x);
}
    /**
 * Parses two strings as the a, b coefficients of a linear inequality
 * a·x + b [<, <=, >, >=] 0, handles invalid a/b/operator input,
 * and prints the solution set.
 *
 * @param aStr the coefficient a (must be a valid number)
 * @param bStr the coefficient b (must be a valid number)
 * @param op   the relation operator: one of "<", "<=", ">", ">="
 */
public static void solveInequality(String aStr, String bStr, String op) {
    double a, b;

    // parse a
    try {
        a = Double.parseDouble(aStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for 'a'. Must be a number.");
        return;
    }
    // parse b
    try {
        b = Double.parseDouble(bStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for 'b'. Must be a number.");
        return;
    }
    // validate operator
    if (!op.equals("<") && !op.equals("<=") && !op.equals(">") && !op.equals(">=")) {
        System.out.println("❌ Invalid operator '" + op + "'. Use one of <, <=, >, >=.");
        return;
    }

    // special case: a == 0 → b [op] 0
    if (a == 0) {
        boolean holds;
        switch (op) {
            case "<":  holds = b <  0; break;
            case "<=": holds = b <= 0; break;
            case ">":  holds = b >  0; break;
            default:   holds = b >= 0; break;
        }
        if (holds) {
            System.out.println("✔ Inequality holds for all real x.");
        } else {
            System.out.println("❌ No solution in ℝ.");
        }
        return;
    }

    // general case: a·x + b [op] 0  →  x [flipped-op?] −b/a
    double bound = -b / a;
    String relation;
    boolean flip = a < 0;               // flip direction if a < 0
    switch (op) {
        case "<":
            relation = flip ? ">"  : "<";
            break;
        case "<=":
            relation = flip ? ">=" : "<=";
            break;
        case ">":
            relation = flip ? "<"  : ">";
            break;
        default: // >=
            relation = flip ? "<=" : ">=";
            break;
    }

    System.out.printf(
        "✔ Solution: x %s %.6f%n",
        relation, bound
    );
}
    /**
 * Parses two strings as operands and an operator, performs
 * addition (+), subtraction (−), multiplication (×) or division (÷),
 * handles invalid input and division-by-zero, and prints the result.
 *
 * @param aStr the first operand (must be a valid number)
 * @param bStr the second operand (must be a valid number)
 * @param op   the operator: one of "+", "-", "*", "/"
 */
public static void solveArithmetic(String aStr, String bStr, String op) {
    double a, b;

    // parse first operand
    try {
        a = Double.parseDouble(aStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for first number. Must be a valid number.");
        return;
    }

    // parse second operand
    try {
        b = Double.parseDouble(bStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for second number. Must be a valid number.");
        return;
    }

    // validate operator
    if (!op.equals("+") && !op.equals("-") && !op.equals("*") && !op.equals("/")) {
        System.out.println("❌ Invalid operator '" + op + "'. Use one of +, -, *, /.");
        return;
    }

    // handle division by zero
    if (op.equals("/") && b == 0) {
        System.out.println("❌ Division by zero is not allowed.");
        return;
    }

    // compute result
    double result;
    switch (op) {
        case "+": result = a + b; break;
        case "-": result = a - b; break;
        case "*": result = a * b; break;
        default:  // "/"
            result = a / b;
    }

    // print formatted result
    System.out.printf("✔ Result: %.6f %s %.6f = %.6f%n", a, op, b, result);
}
    /**
 * Parses two strings as integer inputs, computes and prints
 * their Highest Common Factor (HCF) and Least Common Multiple (LCM).
 * Handles invalid input and special zero cases.
 *
 * @param aStr the first integer (may include sign)
 * @param bStr the second integer (may include sign)
 */
public static void solveHcfLcm(String aStr, String bStr) {
    long a, b;
    // parse first number
    try {
        a = Long.parseLong(aStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for first integer. Must be a valid whole number.");
        return;
    }
    // parse second number
    try {
        b = Long.parseLong(bStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for second integer. Must be a valid whole number.");
        return;
    }

    // take absolute values for computation
    a = Math.abs(a);
    b = Math.abs(b);

    // special case: both zero
    if (a == 0 && b == 0) {
        System.out.println("❌ HCF and LCM are undefined for (0, 0).");
        return;
    }

    // compute HCF via Euclidean algorithm
    long x = a, y = b;
    while (y != 0) {
        long tmp = x % y;
        x = y;
        y = tmp;
    }
    long hcf = x; // now x = gcd(a, b)

    // compute LCM: if either is zero, LCM = 0
    long lcm = (a == 0 || b == 0) ? 0 : (a / hcf) * b;

    System.out.printf("✔ HCF(%d, %d) = %d%n", a, b, hcf);
    System.out.printf("✔ LCM(%d, %d) = %d%n", a, b, lcm);
}
    /**
 * Parses a string as input for a trigonometric or inverse trigonometric function,
 * validates the operator and domain, computes the value (in radians), and prints it.
 *
 * Supported ops:
 *   "sin", "cos", "tan"        – input is angle in radians
 *   "asin", "acos", "atan",    – input is ratio, result in radians
 *   "arctan"                   – alias for "atan"
 *
 * @param valStr the numeric input (angle in radians or ratio)
 * @param op     the function: sin, cos, tan, asin, acos, atan, or arctan
 */
public static void solveTrigonometry(String valStr, String op) {
    double x;
    // parse input
    try {
        x = Double.parseDouble(valStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid number '" + valStr + "'.");
        return;
    }

    // normalize operator to lowercase
    op = op.trim().toLowerCase();

    // validate operator
    if (!op.equals("sin") &&
        !op.equals("cos") &&
        !op.equals("tan") &&
        !op.equals("asin") &&
        !op.equals("acos") &&
        !op.equals("atan") &&
        !op.equals("arctan")) {
        System.out.println("❌ Invalid function '" + op +
            "'. Use sin, cos, tan, asin, acos, atan, or arctan.");
        return;
    }

    // domain check for asin/acos
    if ((op.equals("asin") || op.equals("acos")) && (x < -1 || x > 1)) {
        System.out.println("❌ Domain error: " + op + " input must be in [-1, 1].");
        return;
    }

    // compute
    double result;
    switch (op) {
        case "sin":   result = Math.sin(x);   break;
        case "cos":   result = Math.cos(x);   break;
        case "tan":   result = Math.tan(x);   break;
        case "asin":  result = Math.asin(x);  break;
        case "acos":  result = Math.acos(x);  break;
        case "atan":  result = Math.atan(x);  break;
        default:      // unreachable
            throw new IllegalStateException("Unexpected op: " + op);
    }

    System.out.printf("✔ %s(%.6f) = %.6f%n", op, x, result);
}
    /**
 * Parses two strings as fractions, performs addition, subtraction,
 * multiplication or division, handles invalid input (bad format,
 * zero denominator, division-by-zero), reduces to lowest terms,
 * and prints the result.
 *
 * @param f1Str the first fraction ("a/b" or integer "a")
 * @param f2Str the second fraction ("c/d" or integer "c")
 * @param op    the operator: "+", "-", "*", "/"
 */
public static void solveFractionOperation(String f1Str, String f2Str, String op) {
    long n1, d1, n2, d2;
    // parse first fraction
    try {
        long[] f1 = parseFraction(f1Str);
        n1 = f1[0];  d1 = f1[1];
    } catch (IllegalArgumentException e) {
        System.out.println("❌ Invalid first fraction: " + e.getMessage());
        return;
    }
    // parse second fraction
    try {
        long[] f2 = parseFraction(f2Str);
        n2 = f2[0];  d2 = f2[1];
    } catch (IllegalArgumentException e) {
        System.out.println("❌ Invalid second fraction: " + e.getMessage());
        return;
    }
    // validate operator
    if (!op.equals("+") && !op.equals("-") && !op.equals("*") && !op.equals("/")) {
        System.out.println("❌ Invalid operator '" + op + "'. Use one of +, -, *, /.");
        return;
    }
    // division by zero check
    if (op.equals("/") && n2 == 0) {
        System.out.println("❌ Division by zero (second fraction is 0).");
        return;
    }

    // compute result numerator & denominator
    long rn, rd;
    switch (op) {
        case "+":
            rn = n1 * d2 + n2 * d1;
            rd = d1 * d2;
            break;
        case "-":
            rn = n1 * d2 - n2 * d1;
            rd = d1 * d2;
            break;
        case "*":
            rn = n1 * n2;
            rd = d1 * d2;
            break;
        default: // "/"
            rn = n1 * d2;
            rd = d1 * n2;
    }

    // fix sign: keep denominator positive
    if (rd < 0) {
        rn = -rn;
        rd = -rd;
    }
    // reduce to lowest terms
    long g = gcd(Math.abs(rn), rd);
    rn /= g;
    rd /= g;

    // format result
    String result = (rd == 1) ? String.valueOf(rn) : rn + "/" + rd;
    System.out.printf("✔ %s %s %s = %s%n", f1Str, op, f2Str, result);
}

/** Parses a fraction string "num/den" or "num". Returns [num, den]. */
private static long[] parseFraction(String s) {
    String str = s.trim();
    String[] parts = str.split("/", 2);
    try {
        long num = Long.parseLong(parts[0].trim());
        long den = (parts.length == 2)
                   ? Long.parseLong(parts[1].trim())
                   : 1L;
        if (den == 0) throw new IllegalArgumentException("denominator zero");
        return new long[]{num, den};
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("not a valid integer");
    }
}

/** Computes gcd via Euclidean algorithm (denominator always ≥0). */
private static long gcd(long a, long b) {
    while (b != 0) {
        long t = a % b;
        a = b;
        b = t;
    }
    return a;
}
    /**
 * Parses two strings as counts (favorable and total), computes
 * the probability P = favorable/total, handles invalid input
 * (non-numeric, negatives, total=0, favorable>total), and prints it.
 *
 * @param favStr   number of favorable outcomes
 * @param totalStr total number of outcomes
 */
public static void solveProbability(String favStr, String totalStr) {
    double fav, total;
    // parse favorable
    try {
        fav = Double.parseDouble(favStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for favorable count. Must be a number.");
        return;
    }
    // parse total
    try {
        total = Double.parseDouble(totalStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ Invalid input for total count. Must be a number.");
        return;
    }
    // validate
    if (total <= 0) {
        System.out.println("❌ Total must be > 0.");
        return;
    }
    if (fav < 0 || fav > total) {
        System.out.println("❌ Favorable must be in [0, total].");
        return;
    }
    // compute & print
    double p = fav / total;
    System.out.printf("✔ Probability P = %.6f (%s/%s)%n", p, favStr, totalStr);
}

/**
 * Parses three strings as P(A), P(B|A), P(B|¬A), validates domain [0,1],
 * computes posterior P(A|B) by Bayes’ theorem:
 *   P(A|B) = P(B|A)·P(A) / [P(B|A)·P(A) + P(B|¬A)·(1–P(A))],
 * handles invalid input and zero‐denominator, and prints it.
 *
 * @param pAStr     prior probability P(A)
 * @param pBAStr    likelihood P(B|A)
 * @param pBAcStr   likelihood P(B|¬A)
 */
public static void solveBayes(String pAStr, String pBAStr, String pBAcStr) {
    double pA, pBgivenA, pBgivenNotA;
    try {
        pA            = Double.parseDouble(pAStr.trim());
        pBgivenA      = Double.parseDouble(pBAStr.trim());
        pBgivenNotA   = Double.parseDouble(pBAcStr.trim());
    } catch (NumberFormatException e) {
        System.out.println("❌ All inputs must be valid numbers in [0,1].");
        return;
    }
    // domain check
    if (!(pA >= 0 && pA <= 1 &&
          pBgivenA >= 0 && pBgivenA <= 1 &&
          pBgivenNotA >= 0 && pBgivenNotA <= 1)) {
        System.out.println("❌ All probabilities must lie in [0,1].");
        return;
    }
    // compute denominator
    double pNotA = 1 - pA;
    double denom = pBgivenA * pA + pBgivenNotA * pNotA;
    if (denom == 0) {
        System.out.println("❌ Denominator zero: P(B) = 0, cannot apply Bayes.");
        return;
    }
    // Bayes’ theorem
    double posterior = (pBgivenA * pA) / denom;
    System.out.printf("✔ P(A|B) = %.6f [= (%.6f·%.6f)/%.6f]%n",
                      posterior, pBgivenA, pA, denom);
}
}
