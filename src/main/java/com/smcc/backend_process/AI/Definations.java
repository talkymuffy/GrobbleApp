package com.smcc.backend_process.AI;

public class Definations {
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
}
