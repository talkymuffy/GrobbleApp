package com.smcc.app_interfaces;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FunctionalInterface
public interface SolverFunction {
    String apply(Matcher matcher, StringBuilder out);


    static boolean matchAndSolve(String regex, String input, int flags, StringBuilder out, SolverFunction solver) {
        Pattern pattern = Pattern.compile(regex, flags);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            out.append(solver.apply(matcher, out));
            return true;
        }
        return false;
    }

}