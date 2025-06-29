package com.smcc.backend_process.AI;

import com.smcc.backend_process.Mathematics;
import com.smcc.backend_process.Physics;
import com.smcc.backend_process.PhysicsFAQ;
import com.smcc.backend_process.QuestionInterpreter;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class MrCluckIntelligence {

    public static String processUserMessage(String input) {
        String text = input.trim().toLowerCase();

        // Greetings
        String[] greetings = {"hi", "hello", "hey", "greetings", "namaste", "hola"};
        for (String greet : greetings) {
            if (text.contains(greet)) {
                return "Hello there! How can I help you today?";
            }
        }

        // Farewells
        String[] farewells = {"bye", "goodbye", "see you", "later", "take care"};
        for (String bye : farewells) {
            if (text.contains(bye)) {
                return "Goodbye! Talk to you soon.";
            }
        }

        // Web App Commands
        String[] commands = {"close", "open"};
        String[] sites = {"youtube", "google", "facebook", "instagram", "twitter", "x", "spotify", "link"};

        if (text.contains("open")) {
            for (String site : sites) {
                if (text.contains(site)) {
                    return launchWebsite(site);
                }
                else
                {
                    String url= text.substring(text.indexOf('-')+1);
                    if (url != null) {
                        try {
                            Desktop.getDesktop().browse(URI.create(url));
                            return "Opening " + "link" + "...";
                        } catch (IOException e) {
                            return "Failed to open " + "link" + ".";
                        }
                    }
                }
            }
        } else if (text.contains("close")) {
            if (text.contains("app")) {
                System.exit(0); // Closes Java app
                return "";
            }
        }

        //Info Section
        if (text.contains("physics help") || text.contains("all physics formulas") || text.contains("physics topics")) {
            return getPhysicsConcepts();
        }
        if (text.contains("math examples") || text.contains("example math inputs")) {
            return getMathSampleInputs();
        }
        if (text.contains("physics examples") || text.contains("example physics inputs")) {
            return getPhysicsSampleInputs();
        }

        String topic;
        // For definitions
        if (text.startsWith("what is ") || text.startsWith("define ") || text.contains("definition of")) {
            topic = text
                    .replace("what is", "")
                    .replace("define", "")
                    .replace("definition of", "")
                    .trim();
            String definition = PhysicsFAQ.getDefinitionAndFAQs(topic);

            if (definition != null) return definition;
        }


        // For FAQs
        if (text.startsWith("faq about") || text.startsWith("faqs about")) {
            topic = text
                    .replace("faq about", "")
                    .replace("faqs about", "")
                    .trim();
            String faqs = PhysicsFAQ.getDefinitionAndFAQs(topic);
            if (faqs != null) return faqs;
        }


        // Calculate Sections
        boolean isMath = (
                text.startsWith("solve") ||
                        text.startsWith("find") ||
                        text.startsWith("equate") ||
                        (text.contains("find") && text.contains("value")) ||
                        text.matches("^([a-z]*[:\\-])?\\s*\\d+.*[+\\-*/^=x].*\\d+.*")  // equation-like shorthand
        );

        boolean isPhysics = text.contains("physics") || text.contains("solve physics") ||
                        text.startsWith("phy-") ||
                        text.contains("projectile") || text.contains("range") || text.contains("maximum height") ||
                        text.contains("newton") || text.contains("force") || text.contains("inertia") || text.contains("laws of motion") ||
                        text.contains("voltage") || text.contains("current") || text.contains("resistance") ||
                        text.contains("magnet") || text.contains("flux") || text.contains("magnetic field") ||
                        text.contains("gravity") || text.contains("gravitational") || text.contains("weight") ||
                        text.contains("torque") || text.contains("moment of inertia") || text.contains("angular") ||
                        text.contains("velocity") || text.contains("speed") || text.contains("displacement") || text.contains("acceleration") ||
                        text.contains("shm") || text.contains("spring constant") || text.contains("oscillation") ||
                        text.contains("thermo") || text.contains("heat") || text.contains("temperature change") ||
                        text.contains("elastic") || text.contains("strain") || text.contains("stress") ||
                        text.contains("buoyancy") || text.contains("viscosity") || text.contains("fluid") ||
                        text.contains("vector") || text.contains("dot product") || text.contains("angle between");

        if (!(isMath || isPhysics)) {
            return "This input doesn't appear to be a math or physics question. If you're trying another prompt type (like 'explain:' or 'quiz:'), let me know!";
        }
        else if(isMath||isPhysics){
            return QuestionInterpreter.dectQuestion(text);
        }


        // Friendly Q&A
        if (text.contains("how are you")) return "I'm doing great! Thanks for asking!";
        if (text.contains("what is your name")) return "You can call me Mr. Cluck—your friendly AI companion.";
        if (text.contains("who made you")) return "I was made by two brilliant humans named Saptansu and Kajol, of course!";

        return "I'm not sure how to respond to that yet, but I'm learning! You can ask my uncle, Copilot :)";
    }

    public static String getMathSampleInputs() {
        return """
        -Mathematics Sample Inputs:

        > Bayes' Theorem
        "bayes(0.4, 0.7, 0.6)"

        > Probability
        "P = 4 / 10"
        "probability(3,12)"

        > Trigonometry
        "sin(30)"
        "cos(60)"
        "tan(45)"

        > Fractions
        "3/4 + 1/2"
        "5/6 * 2/3"

        > Arithmetic
        "12.5 + 7.2"
        "30 / 5"

        > HCF and LCM
        "hcf(12, 18)"
        "lcm(8, 20)"

        > Inequality
        "2x+4>=0"
        "-3x-9<0"

        > Quadratic Equation
        "3x^2+5x+2=0"
        "x^2-4x+4=0"

        > Linear Equation
        "2x-6=0"
        "-x+3=0"
        """;
    }
    public static String getPhysicsSampleInputs() {
        return """
        -Physics Sample Inputs:

        > Projectile Motion
        "Find range if velocity is 20 and angle is 45"

        > Laws of Motion
        "Find force if mass is 5 and acceleration is 2"

        > Electricity
        "Voltage is 12, resistance is 6"

        > Magnetism
        "Current is 3, length is 2, magnetic field is 0.5, angle is 30"

        > Gravitation
        "Find weight if mass is 10"

        > Rotational Motion
        "Moment of inertia is 2, angular acceleration is 3"

        > Kinematics
        "Initial velocity is 5, acceleration is 2, time is 3"

        > SHM
        "Mass is 1, spring constant is 4"

        > Thermodynamics
        "Mass is 2, specific heat is 4200, temperature change is 5"

        > Elasticity
        "Force is 100, area is 0.01, extension is 0.002, original length is 2"

        > Fluid Mechanics
        "Force is 100, area is 0.2"

        > Vectors
        "Vector A is 5, Vector B is 4, angle is 60"
        """;
    }

    private static String getString(String text) {
        String expr = null;

        if (text.contains(":")) {
            expr = text.substring(text.indexOf(':') + 1).trim();
        } else if (text.contains("-")) {
            expr = text.substring(text.indexOf('-') + 1).trim();
        } else {
            int physicsIndex = text.indexOf("physics");
            if (physicsIndex != -1 && text.length() > physicsIndex + 7) {
                expr = text.substring(physicsIndex + 7).trim(); // after the word "physics"
            }
        }
        return expr;
    }
    public static String getPhysicsConcepts() {
        return """
        ##  Projectile Motion
        Definition: Motion in 2D under gravity with a parabolic path.
        Formulas:
        - Time of Flight: T = (2v₀·sinθ)/g
        - Range: R = (v₀²·sin2θ)/g
        - Max Height: H = (v₀²·sin²θ)/(2g)
        FAQs:
        - What path does it follow? A parabola.
        - Does horizontal velocity change? No—it remains constant.
        
        ## Laws of Motion
        Definition: Newton's 3 laws describe how forces affect motion.
        Formulas:
        - 1st Law: Law of inertia.
        - 2nd Law: F = m·a
        - 3rd Law: F₁ = -F₂
        FAQs:
        - What is inertia? Resistance to motion change.
        - Are forces always equal in action-reaction pairs? Yes.

        ##  Electricity
        Definition: Flow of electric charge in a circuit.
        Formulas:
        - Ohm’s Law: V = I·R
        - Power: P = VI = I²R = V²/R
        - Energy: E = P·t
        FAQs:
        - What flows in a wire? Electrons.
        - What’s the SI unit of current? Ampere (A)

        ##  Magnetism
        Definition: Force caused by moving charges or magnetic materials.
        Formulas:
        - Magnetic Force: F = q·v·B·sinθ
        - Ampere’s Law: B = (μ₀·I)/(2πr)
        FAQs:
        - What causes magnetism? Moving charges.
        - Do electricity and magnetism relate? Yes—they're unified in electromagnetism.

        ##  Gravitation
        Definition: Attractive force between any two masses.
        Formulas:
        - Universal Law: F = G·(m₁m₂)/r²
        - Weight: W = m·g
        - g = G·M/R²
        FAQs:
        - Why do objects fall? Earth's gravity.
        - Does gravity vary? Slightly with altitude and location.

        ##  Rotational Motion
        Definition: Motion about a fixed axis.
        Formulas:
        - Angular velocity: ω = θ/t
        - Angular acceleration: α = Δω/Δt
        - Torque: τ = I·α
        - K.E.: (1/2)·I·ω²
        FAQs:
        - Moment of inertia? Rotational equivalent of mass.
        - F=ma analog? τ = I·α

        ##  Motion Concepts
        Definitions:
        - Speed: scalar distance/time
        - Velocity: vector displacement/time
        - Displacement: vector from start to end
        - Acceleration: rate of velocity change
        Formulas:
        - v = u + at
        - s = ut + ½at²
        - v² = u² + 2as
        FAQs:
        - Can displacement be zero? Yes.
        - What is deceleration? Negative acceleration.

        ##  Vectors
        Definition: Quantity with both magnitude and direction.
        Formulas:
        - |A| = √(Ax² + Ay² + Az²)
        - Dot: A·B = |A||B|cosθ
        - Cross: A×B = |A||B|sinθ·n̂
        FAQs:
        - Can vectors cancel? Yes, if equal and opposite.
        - Are vectors visualized with arrows? Yes.

        ##  SHM
        Definition: Repetitive motion with restoring force ∝ displacement.
        Formulas:
        - x(t) = A·cos(ωt + φ)
        - T = 2π√(m/k)
        - v_max = A·ω
        FAQs:
        - Restoring force? F = -k·x
        - Is SHM sinusoidal? Always.

        ##  Thermodynamics
        Definition: Study of heat, energy, and systems.
        Formulas:
        - First Law: ΔU = Q - W
        - Ideal Gas: PV = nRT
        FAQs:
        - Entropy? Degree of disorder.
        - Heat vs. temperature? Energy vs. kinetic measurement.

        ##  Elasticity
        Definition: Ability to return to shape after deformation.
        Formulas:
        - Stress = F/A
        - Strain = ΔL/L
        - Y = stress / strain
        FAQs:
        - Elastic limit? Max reversible stretch.
        - Hooke’s law valid for all? Only within elastic limit.

        ## Fluid Mechanics
        Definition: Study of fluids at rest and in motion.
        Formulas:
        - Pressure = F/A
        - Bernoulli’s: P + ½ρv² + ρgh = constant
        FAQs:
        - What is buoyancy? Upthrust.
        - Viscosity? Resistance to flow.
        """;
    }

    // 🔗 Smart site launcher
    private static String launchWebsite(String key) {
        String url = switch (key) {
            case "youtube" -> "https://www.youtube.com/";
            case "google" -> "https://www.google.com/";
            case "facebook" -> "https://www.facebook.com/";
            case "instagram" -> "https://www.instagram.com/";
            case "twitter", "x" -> "https://www.x.com/";
            case "spotify" -> "https://www.spotify.com/";
            default -> null;
        };

        if (url != null) {
            try {
                Desktop.getDesktop().browse(URI.create(url));
                return "Opening " + key + "...";
            } catch (IOException e) {
                return "Failed to open " + key + ".";
            }
        }
        return "Sorry, I don't recognize that link.";
    }
}