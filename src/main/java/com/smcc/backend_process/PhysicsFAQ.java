package com.smcc.backend_process;

import java.util.HashMap;
import java.util.Map;

public class PhysicsFAQ {
    private static final Map<String, String[]> topicFAQs = new HashMap<>();

    static {
        topicFAQs.put("velocity", new String[]{
            "Q: What is velocity?\nA: Velocity is the rate of change of displacement; it is a vector quantity.",
            "Q: How is velocity different from speed?\nA: Speed is scalar (no direction), velocity is vector (includes direction)."
        });
        topicFAQs.put("spring constant", new String[]{
            "Q: What is the spring constant?\nA: It measures the stiffness of a spring, denoted as 'k' in Hooke’s law (F = -k·x).",
            "Q: How do you determine the spring constant?\nA: By dividing applied force by extension (k = F/x)."
        });
        topicFAQs.put("amplitude", new String[]{
            "Q: What is amplitude in SHM?\nA: The maximum displacement from equilibrium.",
            "Q: How does amplitude affect energy in SHM?\nA: The energy is proportional to the square of amplitude (E ∝ A²)."
        });
        topicFAQs.put("archimedes principle", new String[]{
            "Q: What is Archimedes’ Principle?\nA: Any object wholly or partially submerged in a fluid experiences an upward buoyant force equal to the weight of the fluid displaced by the object.",
            "Q: Why do objects float?\nA: If buoyant force equals or exceeds the object's weight, it floats."
        });
        topicFAQs.put("kinematics", new String[]{
            "Q: What is the equation for final velocity?\nA: v = u + at, where u is initial velocity, a is acceleration, t is time.",
            "Q: What is displacement?\nA: The change in position of an object.",
            "Q: What is acceleration?\nA: The rate of change of velocity.",
            "Q: Can displacement be zero?\nA: Yes, if the initial and final positions are the same.",
            "Q: What is deceleration?\nA: Negative acceleration."
        });
        topicFAQs.put("thermodynamics", new String[]{
            "Q: What is specific heat?\nA: The amount of heat required to raise the temperature of 1 kg of a substance by 1°C.",
            "Q: What is the first law of thermodynamics?\nA: Energy cannot be created or destroyed, only transformed.",
            "Q: What is entropy?\nA: A measure of disorder in a system.",
            "Q: What is the difference between heat and temperature?\nA: Heat is energy transfer; temperature measures kinetic energy."
        });
        topicFAQs.put("elasticity", new String[]{
            "Q: What is Young's modulus?\nA: A measure of the stiffness of a solid material.",
            "Q: What is stress and strain?\nA: Stress is force per unit area, strain is the ratio of extension to original length.",
            "Q: What is Hooke’s Law?\nA: Within elastic limit, stress is proportional to strain (F = kx).",
            "Q: Elastic limit?\nA: The maximum stretch after which a material will not return to its original shape.",
            "Q: Is Hooke’s law valid for all materials?\nA: Only within the elastic limit."
        });
        topicFAQs.put("fluid mechanics", new String[]{
            "Q: What is pressure in fluids?\nA: Pressure = Force / Area.",
            "Q: What is buoyancy?\nA: The upward force exerted by a fluid on a submerged object.",
            "Q: What is viscosity?\nA: A fluid’s resistance to flow.",
            "Q: What determines whether an object sinks or floats?\nA: Relative densities and Archimedes’ principle."
        });
        topicFAQs.put("gravitation", new String[]{
            "Q: What is the formula for weight?\nA: Weight = mass × gravity (W = mg).",
            "Q: What is the value of gravitational acceleration on Earth?\nA: Approximately 9.8 m/s².",
            "Q: Why do objects fall?\nA: Because of Earth's gravity.",
            "Q: Does gravity vary?\nA: Slightly, with altitude and location."
        });
        topicFAQs.put("rotational motion", new String[]{
            "Q: What is torque?\nA: Torque is the force that causes rotation, τ = I × α.",
            "Q: What is moment of inertia?\nA: A measure of an object's resistance to changes in its rotation.",
            "Q: What is angular velocity?\nA: The rate of change of angular displacement.",
            "Q: F=ma analog?\nA: τ = I·α"
        });
        topicFAQs.put("magnetism", new String[]{
            "Q: What is the formula for magnetic force on a conductor?\nA: F = I × L × B × sin(θ).",
            "Q: What is magnetic flux?\nA: The product of magnetic field and area perpendicular to it.",
            "Q: What causes magnetism?\nA: Moving charges.",
            "Q: Do electricity and magnetism relate?\nA: Yes—they're unified in electromagnetism."
        });
        topicFAQs.put("vectors", new String[]{
            "Q: What is a dot product?\nA: The product of the magnitudes of two vectors and the cosine of the angle between them.",
            "Q: How do you find the angle between two vectors?\nA: Using the dot product formula.",
            "Q: What is the cross product?\nA: A vector perpendicular to two given vectors with magnitude |A||B|sinθ.",
            "Q: Can vectors cancel?\nA: Yes, if equal and opposite.",
            "Q: Are vectors visualized with arrows?\nA: Yes."
        });
        topicFAQs.put("shm", new String[]{
            "Q: What is the formula for the time period of SHM?\nA: T = 2π√(m/k), where m is mass and k is spring constant.",
            "Q: What causes SHM?\nA: A restoring force proportional to displacement.",
            "Q: What is amplitude in SHM?\nA: The maximum displacement from equilibrium.",
            "Q: What is the restoring force formula?\nA: F = -k·x",
            "Q: Is SHM sinusoidal?\nA: Always.",
            "Q: What is v_max in SHM?\nA: The maximum speed, v_max = A·ω."
        });
    }

    public static String getFAQs(String topic) {
        if(topic == null) return null;
        if (topicFAQs.containsKey(topic)) {
            StringBuilder sb = new StringBuilder();
            sb.append("FAQs for ").append(topic).append(":\n");
            for (String faq : topicFAQs.get(topic)) {
                sb.append(faq).append("\n");
            }
            return sb.toString().trim();
        }
        return null;
    }
}
