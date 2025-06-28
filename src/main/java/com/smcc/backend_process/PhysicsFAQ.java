package com.smcc.backend_process;

import java.util.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PhysicsFAQ {

    private static final Map<String, String> topicDefinitions = new LinkedHashMap<>();
    private static final Map<String, List<String>> topicFAQs = new LinkedHashMap<>();

    static {
        topicDefinitions.put("velocity", "Velocity is the rate of change of displacement; it is a vector quantity that has both magnitude and direction.");
        topicDefinitions.put("spring constant", "The spring constant (k) measures the stiffness of a spring, as described by Hooke’s law (F = -k·x).");
        topicDefinitions.put("amplitude", "Amplitude is the maximum displacement from the equilibrium position in oscillatory motion such as SHM.");
        topicDefinitions.put("archimedes principle", "Archimedes’ Principle states that a body submerged in a fluid experiences an upward buoyant force equal to the weight of the fluid it displaces.");
        topicDefinitions.put("kinematics", "Kinematics is the branch of mechanics dealing with the motion of objects without considering the forces causing the motion.");
        topicDefinitions.put("thermodynamics", "Thermodynamics is the study of heat, work, temperature, and the statistical behaviors of systems.");
        topicDefinitions.put("elasticity", "Elasticity describes a material's ability to return to its original shape after being deformed.");
        topicDefinitions.put("fluid mechanics", "Fluid mechanics studies the behavior of fluids (liquids and gases) and the forces acting on them.");
        topicDefinitions.put("gravitation", "Gravitation is the force of attraction between objects due to their masses, most notably observed as the force keeping planets in orbit.");
        topicDefinitions.put("rotational motion", "Rotational motion refers to the movement of an object around an axis, characterized by quantities like torque and angular velocity.");
        topicDefinitions.put("magnetism", "Magnetism is a force caused by moving electric charges resulting in attractive and repulsive forces between objects.");
        topicDefinitions.put("vectors", "Vectors are quantities that have both magnitude and direction, commonly represented by arrows.");
        topicDefinitions.put("shm", "Simple Harmonic Motion (SHM) is a type of periodic oscillatory motion under a restoring force proportional to displacement from equilibrium.");

        topicFAQs.put("velocity", Arrays.asList(
                "Q: What is velocity?\nA: Velocity is the rate of change of displacement; it is a vector quantity.",
                "Q: How is velocity different from speed?\nA: Speed is scalar (no direction), velocity is vector (includes direction)."
        ));
        topicFAQs.put("spring constant", Arrays.asList(
                "Q: What is the spring constant?\nA: It measures the stiffness of a spring, denoted as 'k' in Hooke’s law (F = -k·x).",
                "Q: How do you determine the spring constant?\nA: By dividing applied force by extension (k = F/x)."
        ));
        topicFAQs.put("amplitude", Arrays.asList(
                "Q: What is amplitude in SHM?\nA: The maximum displacement from equilibrium.",
                "Q: How does amplitude affect energy in SHM?\nA: The energy is proportional to the square of amplitude (E ∝ A²)."
        ));
        topicFAQs.put("archimedes principle", Arrays.asList(
                "Q: What is Archimedes’ Principle?\nA: Any object wholly or partially submerged in a fluid experiences an upward buoyant force equal to the weight of the fluid displaced by the object.",
                "Q: Why do objects float?\nA: If buoyant force equals or exceeds the object's weight, it floats."
        ));
        topicFAQs.put("kinematics", Arrays.asList(
                "Q: What is the equation for final velocity?\nA: v = u + at, where u is initial velocity, a is acceleration, t is time.",
                "Q: What is displacement?\nA: The change in position of an object."
        ));
        topicFAQs.put("thermodynamics", Arrays.asList(
                "Q: What is specific heat?\nA: The amount of heat required to raise the temperature of 1 kg of a substance by 1°C.",
                "Q: What is the first law of thermodynamics?\nA: Energy cannot be created or destroyed, only transformed.",
                "Q: What is entropy?\nA: A measure of disorder in a system.",
                "Q: What is the difference between heat and temperature?\nA: Heat is energy transfer; temperature measures kinetic energy."
        ));
        topicFAQs.put("elasticity", Arrays.asList(
                "Q: What is Young's modulus?\nA: A measure of the stiffness of a solid material."
        ));
        topicFAQs.put("fluid mechanics", Arrays.asList(
                "Q: What does fluid mechanics study?\nA: The behavior of fluids and the forces acting on them."
        ));
        topicFAQs.put("gravitation", Arrays.asList(
                "Q: What is gravitational force?\nA: The force of attraction between any two masses."
        ));
        topicFAQs.put("rotational motion", Arrays.asList(
                "Q: What is angular velocity?\nA: The rate of change of angular displacement."
        ));
        topicFAQs.put("magnetism", Arrays.asList(
                "Q: What causes magnetism?\nA: Moving electric charges."
        ));
        topicFAQs.put("vectors", Arrays.asList(
                "Q: Can vectors cancel?\nA: Yes, if equal and opposite.",
                "Q: Are vectors visualized with arrows?\nA: Yes."
        ));
        topicFAQs.put("shm", Arrays.asList(
                "Q: What is the formula for the time period of SHM?\nA: T = 2π√(m/k), where m is mass and k is spring constant.",
                "Q: What causes SHM?\nA: A restoring force proportional to displacement.",
                "Q: What is amplitude in SHM?\nA: The maximum displacement from equilibrium.",
                "Q: What is the restoring force formula?\nA: F = -k·x",
                "Q: Is SHM sinusoidal?\nA: Always.",
                "Q: What is v_max in SHM?\nA: The maximum speed, v_max = A·ω."
        ));
    }
    public static String getDefinition(String topic) {
        if (topic == null) return null;
        return topicDefinitions.getOrDefault(topic.toLowerCase(), null);
    }


    public static String getFAQs(String topic) {
        if (topic == null) return null;
        List<String> faqs = topicFAQs.get(topic.toLowerCase());
        if (faqs != null && !faqs.isEmpty()) {
            return String.join("\n\n", faqs);
        }
        return null;
    }

    public static String getDefinitionAndFAQs(String topic) {
        String def = getDefinition(topic);
        String faqs = getFAQs(topic);
        if (def != null && faqs != null) {
            return def + "\n\n" + faqs;
        } else if (def != null) {
            return def;
        } else if (faqs != null) {
            return faqs;
        }
        return null;
    }

    public static String extractTopic(String question) {
        if (question == null) return null;
        String sanitized = question.toLowerCase().replaceAll("[^a-z0-9\\s]", " ");
        sanitized = sanitized.replaceAll("\\s+", " ").trim();
        for (String topic : topicDefinitions.keySet()) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(topic.toLowerCase()) + "\\b");
            Matcher matcher = pattern.matcher(sanitized);
            if (matcher.find()) {
                return topic;
            }
        }
        return null;
    }
}
