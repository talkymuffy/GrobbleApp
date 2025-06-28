package com.smcc.backend_process;

public class Physics {
  //Projectile Motion, Laws of Motion, Electricity, Magnetism,Gravitation,Rotational Motion,Velocity,Speed,Displacement, Acceleration,SHM,Themrodynamics,Elasticity,Fluid Mechanics
    //Vectors
  public static String autoSolvePhysics(String question) {
      question = question.toLowerCase();

      if (question.contains("projectile") || question.contains("range") || question.contains("maximum height")) {
          return solveProjectileMotion(question);
      } else if (question.contains("newton") || question.contains("force") || question.contains("inertia")) {
          return solveLawsOfMotion(question);
      } else if (question.contains("voltage") || question.contains("current") || question.contains("resistance")) {
          return solveElectricity(question);
      } else if (question.contains("magnetic") || question.contains("flux") || question.contains("field")) {
          return solveMagnetism(question);
      } else if (question.contains("gravity") || question.contains("gravitational")) {
          return solveGravitation(question);
      } else if (question.contains("torque") || question.contains("moment of inertia") || question.contains("angular")) {
          return solveRotationalMotion(question);
      } else if (question.contains("velocity") || question.contains("speed") || question.contains("acceleration") || question.contains("displacement")) {
          return solveKinematics(question);
      } else if (question.contains("shm") || question.contains("oscillation")) {
          return solveSHM(question);
      } else if (question.contains("thermo") || question.contains("heat") || question.contains("temperature")) {
          return solveThermodynamics(question);
      } else if (question.contains("elastic") || question.contains("strain") || question.contains("stress")) {
          return solveElasticity(question);
      } else if (question.contains("buoyancy") || question.contains("viscosity") || question.contains("fluid")) {
          return solveFluidMechanics(question);
      } else if (question.contains("vector") || question.contains("dot product") || question.contains("cross product")) {
          return solveVectors(question);
      }

      return "I couldn't detect the topic yet. Try asking about motion, electricity, or vectors!";
  }
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
        // Example: "What is force if mass is 5 kg and acceleration is 2 m/s^2?"
        double m = 5.0, a = 2.0;
        double force = m * a;
        return "Force = mass × acceleration = " + force + " N";
    }
}
