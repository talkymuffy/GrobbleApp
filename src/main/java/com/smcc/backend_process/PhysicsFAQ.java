package com.smcc.backend_process;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PhysicsFAQ {

    private static final Map<String, String> topicDefinitions = new LinkedHashMap<>();
    private static final Map<String, List<String>> topicFAQs = new LinkedHashMap<>();

    static {
        // Kinematics & Motion
        topicDefinitions.put("velocity", "Velocity is the rate of change of displacement; it is a vector quantity that has both magnitude and direction.");
        topicDefinitions.put("acceleration", "Acceleration is the rate of change of velocity with respect to time.");
        topicDefinitions.put("kinematics", "Kinematics is the branch of mechanics dealing with the motion of objects without considering the forces causing the motion.");
        topicDefinitions.put("projectile motion", "Projectile motion refers to the motion of an object thrown or projected into the air, subject only to gravity.");
        topicDefinitions.put("displacement", "Displacement is a vector representing the change in position of an object.");
        // Dynamics & Newton's Laws
        topicDefinitions.put("force", "A force is a push or pull upon an object resulting from its interaction with another object.");
        topicDefinitions.put("newton's laws", "Newton's Laws are three physical laws that form the foundation for classical mechanics describing the relationship between a body and the forces acting upon it.");
        // Energy & Work
        topicDefinitions.put("work", "Work is the product of the force applied to an object and the distance the object moves in the direction of the force.");
        topicDefinitions.put("energy", "Energy is the capacity to do work.");
        topicDefinitions.put("power", "Power is the rate at which work is done or energy is transferred.");
        // Circular & Rotational Motion
        topicDefinitions.put("rotational motion", "Rotational motion refers to the movement of an object around an axis, characterized by quantities like torque and angular velocity.");
        topicDefinitions.put("torque", "Torque is a measure of the turning force on an object, equal to the force times the lever arm.");
        topicDefinitions.put("moment of inertia", "Moment of inertia is a quantity expressing a body's tendency to resist angular acceleration.");
        // Oscillations & Waves
        topicDefinitions.put("shm", "Simple Harmonic Motion (SHM) is a type of periodic oscillatory motion under a restoring force proportional to displacement from equilibrium.");
        topicDefinitions.put("amplitude", "Amplitude is the maximum displacement from the equilibrium position in oscillatory motion such as SHM.");
        topicDefinitions.put("frequency", "Frequency is the number of oscillations or cycles per unit time.");
        topicDefinitions.put("wave", "A wave is a disturbance that transfers energy from one place to another without transferring matter.");
        // Gravitation
        topicDefinitions.put("gravitation", "Gravitation is the force of attraction between objects due to their masses, most notably observed as the force keeping planets in orbit.");
        topicDefinitions.put("weight", "Weight is the force exerted on a body due to gravity.");
        // Fluid Mechanics
        topicDefinitions.put("fluid mechanics", "Fluid mechanics studies the behavior of fluids (liquids and gases) and the forces acting on them.");
        topicDefinitions.put("archimedes principle", "Archimedes’ Principle states that a body submerged in a fluid experiences an upward buoyant force equal to the weight of the fluid it displaces.");
        topicDefinitions.put("density", "Density is mass per unit volume of a substance.");
        topicDefinitions.put("buoyancy", "Buoyancy is the upward force exerted by a fluid that opposes the weight of an object immersed in it.");
        topicDefinitions.put("viscosity", "Viscosity is a measure of a fluid's resistance to deformation or flow; it describes the internal friction of a moving fluid.");
        topicDefinitions.put("surface tension", "Surface tension is the tendency of a fluid’s surface to shrink into the minimum surface area possible, due to cohesive forces between molecules.");
        topicDefinitions.put("pressure", "Pressure is the force exerted per unit area.");
        topicDefinitions.put("bernoulli's principle", "Bernoulli's Principle states that in a streamline flow, the sum of pressure energy, kinetic energy and potential energy per unit volume is constant.");
        // Thermodynamics
        topicDefinitions.put("thermodynamics", "Thermodynamics is the study of heat, work, temperature, and the statistical behaviors of systems.");
        topicDefinitions.put("specific heat", "Specific heat is the amount of heat required to raise the temperature of 1 kg of a substance by 1°C.");
        topicDefinitions.put("entropy", "Entropy is a measure of disorder or randomness in a system.");
        // Electricity & Magnetism
        topicDefinitions.put("electricity", "Electricity is the presence and flow of electric charge, used as a source of power.");
        topicDefinitions.put("voltage", "Voltage is the electric potential difference between two points.");
        topicDefinitions.put("current", "Electric current is the flow of electric charge.");
        topicDefinitions.put("resistance", "Resistance is a measure of the opposition to the flow of electric current.");
        topicDefinitions.put("magnetism", "Magnetism is a force caused by moving electric charges resulting in attractive and repulsive forces between objects.");
        // Material Properties
        topicDefinitions.put("elasticity", "Elasticity describes a material's ability to return to its original shape after being deformed.");
        topicDefinitions.put("spring constant", "The spring constant (k) measures the stiffness of a spring, as described by Hooke’s law (F = -k·x).");
        topicDefinitions.put("young's modulus", "Young's modulus is a measure of the stiffness of a solid material.");
        // Vectors & Scalars
        topicDefinitions.put("vectors", "Vectors are quantities that have both magnitude and direction, commonly represented by arrows.");
        topicDefinitions.put("scalar", "A scalar is a quantity that has only magnitude, not direction.");

        // FAQs (Only some representative examples below; you can expand these as needed)
        topicFAQs.put("velocity", Arrays.asList(
                "Q: What is velocity?\nA: Velocity is the rate of change of displacement; it is a vector quantity.",
                "Q: How is velocity different from speed?\nA: Speed is scalar (no direction), velocity is vector (includes direction)."
        ));
        topicFAQs.put("acceleration", Arrays.asList(
                "Q: What is acceleration?\nA: Acceleration is the rate of change of velocity."
        ));
        topicFAQs.put("kinematics", Arrays.asList(
                "Q: What is the equation for final velocity?\nA: v = u + at, where u is initial velocity, a is acceleration, t is time.",
                "Q: What is displacement?\nA: The change in position of an object."
        ));
        topicFAQs.put("force", Arrays.asList(
                "Q: What is force?\nA: A push or pull exerted on an object.",
                "Q: What is Newton's second law?\nA: F = m*a, where F is force, m is mass, a is acceleration."
        ));
        topicFAQs.put("work", Arrays.asList(
                "Q: What is work?\nA: Work is done when a force moves an object over a distance. W = F * d."
        ));
        topicFAQs.put("energy", Arrays.asList(
                "Q: What are types of energy?\nA: Kinetic, potential, thermal, chemical, etc."
        ));
        topicFAQs.put("rotational motion", Arrays.asList(
                "Q: What is angular velocity?\nA: The rate of change of angular displacement."
        ));
        topicFAQs.put("shm", Arrays.asList(
                "Q: What is the formula for the time period of SHM?\nA: T = 2π√(m/k), where m is mass and k is spring constant.",
                "Q: What causes SHM?\nA: A restoring force proportional to displacement.",
                "Q: Is SHM sinusoidal?\nA: Yes."
        ));
        topicFAQs.put("gravitation", Arrays.asList(
                "Q: What is gravitational force?\nA: The force of attraction between any two masses."
        ));
        topicFAQs.put("fluid mechanics", Arrays.asList(
                "Q: What does fluid mechanics study?\nA: The behavior of fluids and the forces acting on them.",
                "Q: What is viscosity?\nA: The resistance of a fluid to flow.",
                "Q: What is Bernoulli's equation?\nA: It relates pressure, velocity, and height in a moving fluid."
        ));
        topicFAQs.put("viscosity", Arrays.asList(
                "Q: What is viscosity?\nA: Viscosity is a measure of a fluid's resistance to flow.",
                "Q: What are examples of high and low viscosity fluids?\nA: Honey has high viscosity; water has low viscosity.",
                "Q: What causes viscosity?\nA: Internal friction between fluid molecules."
        ));
        topicFAQs.put("archimedes principle", Arrays.asList(
                "Q: What is Archimedes’ Principle?\nA: Any object wholly or partially submerged in a fluid experiences an upward buoyant force equal to the weight of the fluid displaced by the object.",
                "Q: Why do objects float?\nA: If buoyant force equals or exceeds the object's weight, it floats."
        ));
        topicFAQs.put("pressure", Arrays.asList(
                "Q: What is pressure?\nA: Pressure is force per unit area (P = F/A).",
                "Q: What is atmospheric pressure?\nA: The pressure exerted by the weight of the atmosphere."
        ));
        topicFAQs.put("surface tension", Arrays.asList(
                "Q: What is surface tension?\nA: The tendency of fluid surfaces to shrink into the minimum surface area possible.",
                "Q: Why do small insects walk on water?\nA: Due to surface tension."
        ));
        topicFAQs.put("thermodynamics", Arrays.asList(
                "Q: What is specific heat?\nA: The amount of heat required to raise the temperature of 1 kg of a substance by 1°C.",
                "Q: What is the first law of thermodynamics?\nA: Energy cannot be created or destroyed, only transformed.",
                "Q: What is entropy?\nA: A measure of disorder in a system.",
                "Q: What is the difference between heat and temperature?\nA: Heat is energy transfer; temperature measures kinetic energy."
        ));
        topicFAQs.put("elasticity", Arrays.asList(
                "Q: What is Young's modulus?\nA: A measure of the stiffness of a solid material.",
                "Q: What is Hooke's Law?\nA: F = -k*x, where k is the spring constant and x is displacement."
        ));
        topicFAQs.put("magnetism", Arrays.asList(
                "Q: What causes magnetism?\nA: Moving electric charges."
        ));
        topicFAQs.put("vectors", Arrays.asList(
                "Q: Can vectors cancel?\nA: Yes, if equal and opposite.",
                "Q: Are vectors visualized with arrows?\nA: Yes."
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
