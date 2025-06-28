package com.smcc.backend_process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Physics {

    public static String autoSolvePhysics(String question) {
        question = question.toLowerCase().trim();


        // --- End FAQ/Definition Handling ---

        // Topic detection to computation
        if (question.contains("projectile") || question.contains("range") || question.contains("maximum height")) {
            return solveProjectileMotion(question);
        } else if (question.contains("newton") || question.contains("force") || question.contains("inertia") || question.contains("laws of motion")) {
            return solveLawsOfMotion(question);
        } else if (question.contains("voltage") || question.contains("current") || question.contains("resistance")) {
            return solveElectricity(question);
        } else if (question.contains("magnet") || question.contains("flux") || question.contains("magnetic field")) {
            return solveMagnetism(question);
        } else if (question.contains("gravity") || question.contains("gravitational") || question.contains("weight")) {
            return solveGravitation(question);
        } else if (question.contains("torque") || question.contains("moment of inertia") || question.contains("angular")) {
            return solveRotationalMotion(question);
        } else if (question.contains("velocity") || question.contains("speed") || question.contains("displacement") || question.contains("acceleration")) {
            return solveKinematics(question);
        } else if (question.contains("shm") || question.contains("spring constant") || question.contains("oscillation")) {
            return solveSHM(question);
        } else if (question.contains("thermo") || question.contains("heat") || question.contains("temperature change")) {
            return solveThermodynamics(question);
        } else if (question.contains("elastic") || question.contains("strain") || question.contains("stress")) {
            return solveElasticity(question);
        } else if (question.contains("buoyancy") || question.contains("viscosity") || question.contains("fluid")) {
            return solveFluidMechanics(question);
        } else if (question.contains("vector") || question.contains("dot product") || question.contains("angle between")) {
            return solveVectors(question);
        }

        return "I couldn't detect the topic yet. Try asking about motion, electricity, or magnetism with values included!";
    }

    // The following solve* methods remain unchanged

    private static String solveProjectileMotion(String question) {
        Pattern vPat = Pattern.compile("velocity\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern aPat = Pattern.compile("angle\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher v = vPat.matcher(question), a = aPat.matcher(question);
        if (v.find() && a.find()) {
            double velocity = Double.parseDouble(v.group(2));
            double angle = Double.parseDouble(a.group(2));
            double range = (velocity * velocity * Math.sin(Math.toRadians(2 * angle))) / 9.8;
            return "Range = " + String.format("%.2f", range) + " meters";
        }
        return "Please provide both velocity and angle.";
    }
    private static String solveLawsOfMotion(String question) {
        Pattern mPat = Pattern.compile("mass\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern aPat = Pattern.compile("acceleration\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher m = mPat.matcher(question), a = aPat.matcher(question);
        if (m.find() && a.find()) {
            double mass = Double.parseDouble(m.group(2));
            double acc = Double.parseDouble(a.group(2));
            return "Force = mass × acceleration = " + (mass * acc) + " N";
        }
        return "Try: 'Find force if mass is 5 kg and acceleration is 2 m/s²'";
    }
    private static String solveElectricity(String question) {
        Pattern vPat = Pattern.compile("voltage\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern rPat = Pattern.compile("resistance\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher v = vPat.matcher(question), r = rPat.matcher(question);
        if (v.find() && r.find()) {
            double V = Double.parseDouble(v.group(2));
            double R = Double.parseDouble(r.group(2));
            return "Current = V / R = " + (V / R) + " A";
        }
        return "Try something like: 'Voltage is 10V, resistance is 5Ω'";
    }
    private static String solveMagnetism(String question) {
        Pattern iPat = Pattern.compile("current\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern lPat = Pattern.compile("length\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern bPat = Pattern.compile("magnetic field\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern aPat = Pattern.compile("angle\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher i = iPat.matcher(question);
        Matcher l = lPat.matcher(question);
        Matcher b = bPat.matcher(question);
        Matcher a = aPat.matcher(question);

        if (i.find() && l.find() && b.find() && a.find()) {
            double I = Double.parseDouble(i.group(2));
            double L = Double.parseDouble(l.group(2));
            double B = Double.parseDouble(b.group(2));
            double theta = Double.parseDouble(a.group(2));

            double force = I * L * B * Math.sin(Math.toRadians(theta));
            return "Magnetic force = I × L × B × sin(θ) = " + String.format("%.2f", force) + " N";
        }

        return "Please provide values for current, length, magnetic field, and angle (e.g., 'Current is 3 A, length is 2 m, magnetic field is 0.5 T, angle is 30°')";
    }
    private static String solveGravitation(String question) {
        Pattern mPat = Pattern.compile("mass\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher m = mPat.matcher(question);
        if (m.find()) {
            double mass = Double.parseDouble(m.group(2));
            return "Weight = mass × g = " + (mass * 9.8) + " N";
        }
        return "Try: 'Find weight if mass is 10 kg'";
    }
    private static String solveRotationalMotion(String question) {
        Pattern iPat = Pattern.compile("moment of inertia\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern aPat = Pattern.compile("angular acceleration\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher i = iPat.matcher(question), a = aPat.matcher(question);
        if (i.find() && a.find()) {
            double I = Double.parseDouble(i.group(2));
            double alpha = Double.parseDouble(a.group(2));
            return "Torque = I × α = " + (I * alpha) + " Nm";
        }
        return "Mention moment of inertia and angular acceleration to compute torque.";
    }
    private static String solveKinematics(String question) {
        Pattern uPat = Pattern.compile("initial velocity\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern aPat = Pattern.compile("acceleration\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern tPat = Pattern.compile("time\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher u = uPat.matcher(question), a = aPat.matcher(question), t = tPat.matcher(question);
        if (u.find() && a.find() && t.find()) {
            double uVal = Double.parseDouble(u.group(2));
            double acc = Double.parseDouble(a.group(2));
            double time = Double.parseDouble(t.group(2));
            return "Final velocity = u + at = " + (uVal + acc * time) + " m/s";
        }
        return "Try: 'Initial velocity is 5 m/s, acceleration is 2 m/s², time is 3 s'";
    }
    private static String solveSHM(String question) {
        Pattern mPat = Pattern.compile("mass\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern kPat = Pattern.compile("spring constant\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher m = mPat.matcher(question), k = kPat.matcher(question);
        if (m.find() && k.find()) {
            double mass = Double.parseDouble(m.group(2));
            double ki = Double.parseDouble(k.group(2));
            double T = 2 * Math.PI * Math.sqrt(mass / ki);
            return "Time period = 2π√(m/k) = " + String.format("%.2f", T) + " s";
        }
        return "Try: 'Mass is 1 kg, spring constant is 4 N/m'";
    }
    private static String solveThermodynamics(String question) {
        Pattern mPat = Pattern.compile("mass\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern cPat = Pattern.compile("specific heat\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern dTPat = Pattern.compile("temperature change\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher m = mPat.matcher(question), c = cPat.matcher(question), dt = dTPat.matcher(question);
        if (m.find() && c.find() && dt.find()) {
            double mass = Double.parseDouble(m.group(2));
            double cVal = Double.parseDouble(c.group(2));
            double deltaT = Double.parseDouble(dt.group(2));
            return "Q = mcΔT = " + (mass * cVal * deltaT) + " J";
        }
        return "Say: 'Mass is 2 kg, specific heat is 4200 J/kg°C, temperature change is 5°C'";
    }
    private static String solveElasticity(String question) {
        Pattern fPat = Pattern.compile("force\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern aPat = Pattern.compile("area\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern dlPat = Pattern.compile("extension\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern lPat = Pattern.compile("original length\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher f = fPat.matcher(question), a = aPat.matcher(question),
                dl = dlPat.matcher(question), l = lPat.matcher(question);

        if (f.find() && a.find() && dl.find() && l.find()) {
            double force = Double.parseDouble(f.group(2));
            double area = Double.parseDouble(a.group(2));
            double extension = Double.parseDouble(dl.group(2));
            double length = Double.parseDouble(l.group(2));

            double stress = force / area;
            double strain = extension / length;
            double youngs = stress / strain;

            return "Young's modulus = " + String.format("%.2f", youngs) + " N/m²";
        }
        return "Try giving force, area, extension, and original length.";
    }
    private static String solveFluidMechanics(String question) {
        Pattern fPat = Pattern.compile("force\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern aPat = Pattern.compile("area\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher f = fPat.matcher(question), a = aPat.matcher(question);
        if (f.find() && a.find()) {
            double F = Double.parseDouble(f.group(2));
            double A = Double.parseDouble(a.group(2));
            double P = F / A;
            return "Pressure = Force / Area = " + String.format("%.2f", P) + " Pa";
        }
        return "Provide force and area values, like: 'Force is 100, area is 0.5'";
    }
    private static String solveVectors(String question) {
        Pattern aPat = Pattern.compile("vector\\s*a\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern bPat = Pattern.compile("vector\\s*b\\s*(is)?\\s*(\\d+(\\.\\d+)?)");
        Pattern anglePat = Pattern.compile("angle\\s*(is)?\\s*(\\d+(\\.\\d+)?)");

        Matcher a = aPat.matcher(question);
        Matcher b = bPat.matcher(question);
        Matcher angle = anglePat.matcher(question);

        if (a.find() && b.find() && angle.find()) {
            double A = Double.parseDouble(a.group(2));
            double B = Double.parseDouble(b.group(2));
            double theta = Double.parseDouble(angle.group(2));

            double dot = A * B * Math.cos(Math.toRadians(theta));
            return "Dot product = A × B × cos(θ) = " + String.format("%.2f", dot);
        }

        return "Please provide magnitudes of Vector A, Vector B, and the angle between them (e.g., 'A is 5, B is 4, angle is 60°')";
    }
}
