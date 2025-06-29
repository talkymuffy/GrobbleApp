package com.smcc.backend_process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * WordProblemParser is a central utility for parsing natural‐language
 * word‐problems in both physics and math.  It scans the input string
 * against a library of regex patterns, extracts numeric values and
 * parameters, and then assembles a concise “command” string that
 * can be handed off to your solver methods.
 *
 * Supported physics commands:
 *  - travel:d1,v1,d2,v2
 *  - projectile:u,angle,g
 *  - kinematics:d,t
 *  - force:m,a
 *  - voltage:I,R
 *  - torque:F,r
 *  - period:m,k
 *  - heat:m,c,del(T)
 *  - stress:F,A
 *  - buoyancy:ρ,V
 *  - dotproduct:x1,y1,z1,x2,y2,z2
 *
 * Supported math commands:
 *  - average:a,b,c
 *  - percent:p,x
 *  - interest:P,R,T
 *  - ratio:total,r1,r2[,r3]
 */


public class WordProblemParser {

    // --- PHYSICS PATTERNS ---
    private static final Pattern TRAVEL = Pattern.compile(
            "first\\s*(\\d+(?:\\.\\d+)?)\\s*km\\s*(?:at|with)\\s*(\\d+(?:\\.\\d+)?)\\s*km/h.*?then\\s*(\\d+(?:\\.\\d+)?)\\s*km\\s*(?:at|with)\\s*(\\d+(?:\\.\\d+)?)\\s*km/h",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern PROJECTILE = Pattern.compile(
            "projectile(?: motion)?(?:.*?)(\\d+(?:\\.\\d+)?)\\s*m/s(?:.*?)(\\d+(?:\\.\\d+)?)°(?:.*?(?:g=(\\d+(?:\\.\\d+)?)))?",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern KINEMATICS = Pattern.compile(
            "(\\d+(?:\\.\\d+)?)\\s*(?:m|km)\\s*(?:in|over)\\s*(\\d+(?:\\.\\d+)?)\\s*s",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern FORCE = Pattern.compile(
            "mass\\s*(\\d+(?:\\.\\d+)?)\\s*(?:kg)?\\s*(?:accelerat(?:es|ion)?)?\\s*at\\s*(\\d+(?:\\.\\d+)?)\\s*(?:m/s2|m/s\\^2)?",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern VOLTAGE = Pattern.compile(
            "voltage.*?current\\s*(\\d+(?:\\.\\d+)?)\\s*A.*?resistan(?:ce)?\\s*(\\d+(?:\\.\\d+)?)",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern TORQUE = Pattern.compile(
            "torque.*?force\\s*(\\d+(?:\\.\\d+)?)\\s*N.*?arm\\s*(\\d+(?:\\.\\d+)?)\\s*m",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern PERIOD_SHM = Pattern.compile(
            "period.*?mass\\s*(\\d+(?:\\.\\d+)?)\\s*kg.*?spring constant\\s*(\\d+(?:\\.\\d+)?)",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern THERMO = Pattern.compile(
            "heat.*?mass\\s*(\\d+(?:\\.\\d+)?)\\s*kg.*?specific heat\\s*(\\d+(?:\\.\\d+)?)\\s*(?:j/kg.k|j/kg°c).*?ΔT\\s*(\\d+(?:\\.\\d+)?)",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern STRESS = Pattern.compile(
            "stress.*?force\\s*(\\d+(?:\\.\\d+)?)\\s*N.*?area\\s*(\\d+(?:\\.\\d+)?)\\s*(?:m2|m²)",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern BUOYANCY = Pattern.compile(
            "buoyancy.*?density\\s*(\\d+(?:\\.\\d+)?)\\s*(?:kg/m3)?.*?volume\\s*(\\d+(?:\\.\\d+)?)",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern DOT = Pattern.compile(
            "dot product.*?\\(([-\\d.]+),\\s*([-\\d.]+)(?:,\\s*([-\\d.]+))?\\).*?\\(([-\\d.]+),\\s*([-\\d.]+)(?:,\\s*([-\\d.]+))?\\)",
            Pattern.CASE_INSENSITIVE
    );

    // --- MATH PATTERNS ---
    private static final Pattern AVERAGE = Pattern.compile(
            "average of (\\d+(?:\\.\\d+)?)[,\\s]+(\\d+(?:\\.\\d+)?)[,\\s]+and\\s+(\\d+(?:\\.\\d+)?)",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern PERCENT = Pattern.compile(
            "what is (\\d+(?:\\.\\d+)?)% of (\\d+(?:\\.\\d+)?)",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern INTEREST = Pattern.compile(
            "simple interest on (\\d+(?:\\.\\d+)?) at (\\d+(?:\\.\\d+)?)% for (\\d+(?:\\.\\d+)?) yrs?",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern RATIO = Pattern.compile(
            "divide (\\d+(?:\\.\\d+)?) in the ratio (\\d+(?:\\.\\d+)?):(\\d+(?:\\.\\d+)?)(?::(\\d+(?:\\.\\d+)?))?",
            Pattern.CASE_INSENSITIVE
    );

    // --- PARSERS ---
    public static String parsePhysicsProblem(String input) {
        Matcher m;
        if ((m = TRAVEL.matcher(input)).find()) {
            return String.format("travel:%s,%s,%s,%s",
                    m.group(1), m.group(2), m.group(3), m.group(4));
        }
        if ((m = PROJECTILE.matcher(input)).find()) {
            String g = m.group(3) != null ? m.group(3) : "9.8";
            return String.format("projectile:%s,%s,%s",
                    m.group(1), m.group(2), g);
        }
        if ((m = KINEMATICS.matcher(input)).find()) {
            return String.format("kinematics:%s,%s", m.group(1), m.group(2));
        }
        if ((m = FORCE.matcher(input)).find()) {
            return String.format("force:%s,%s", m.group(1), m.group(2));
        }
        if ((m = VOLTAGE.matcher(input)).find()) {
            return String.format("voltage:%s,%s", m.group(1), m.group(2));
        }
        if ((m = TORQUE.matcher(input)).find()) {
            return String.format("torque:%s,%s", m.group(1), m.group(2));
        }
        if ((m = PERIOD_SHM.matcher(input)).find()) {
            return String.format("period:%s,%s", m.group(1), m.group(2));
        }
        if ((m = THERMO.matcher(input)).find()) {
            return String.format("heat:%s,%s,%s", m.group(1), m.group(2), m.group(3));
        }
        if ((m = STRESS.matcher(input)).find()) {
            return String.format("stress:%s,%s", m.group(1), m.group(2));
        }
        if ((m = BUOYANCY.matcher(input)).find()) {
            return String.format("buoyancy:%s,%s", m.group(1), m.group(2));
        }
        if ((m = DOT.matcher(input)).find()) {
            // group(3) or group(6) might be null if 2D
            String z1 = m.group(3) != null ? m.group(3) : "0";
            String z2 = m.group(6) != null ? m.group(6) : "0";
            return String.format("dotproduct:%s,%s,%s,%s,%s,%s",
                    m.group(1), m.group(2), z1,
                    m.group(4), m.group(5), z2);
        }
        return null;
    }

    public static String parseMathProblem(String input) {
        Matcher m;
        if ((m = AVERAGE.matcher(input)).find()) {
            return String.format("average:%s,%s,%s",
                    m.group(1), m.group(2), m.group(3));
        }
        if ((m = PERCENT.matcher(input)).find()) {
            return String.format("percent:%s,%s",
                    m.group(1), m.group(2));
        }
        if ((m = INTEREST.matcher(input)).find()) {
            return String.format("interest:%s,%s,%s",
                    m.group(1), m.group(2), m.group(3));
        }
        if ((m = RATIO.matcher(input)).find()) {
            String c = m.group(4) != null ? m.group(4) : "";
            return String.format("ratio:%s,%s,%s,%s",
                    m.group(1), m.group(2), m.group(3), c);
        }
        return null;
    }
}