import java.util.Scanner;

public class NewModelSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Euler's Method Simulation for Rent Stabilization System");
        System.out.println("Note: Each time step (delta t) represents a decade.");

        while (true) {
            // User Inputs
            System.out.print("\nEnter initial population P0: ");
            double P = scanner.nextDouble();

            System.out.print("Enter initial rent R0: ");
            double R = scanner.nextDouble();

            System.out.print("Enter time step size: ");
            double deltaT = scanner.nextDouble();

            System.out.print("Enter total number of iterations: ");
            int totalSteps = scanner.nextInt();

            // Variables to track stabilization
            double tolerance = 1e-1 * deltaT;
            boolean stabilized = false;
            int stabilizeStep = 0;
            int stabilizeCount = 0;
            int requiredStableSteps = 10000; // Number of consecutive stable steps required

            for (int step = 1; step <= totalSteps; step++) {
                // Compute derivatives
                double dPdt = P * (10 - P) - R;
                double dRdt = 0.5 * R + (P / 3) * (R / 10) - 0.3 * Math.pow(R - 3, 2) * heaviside(R - 3);

                // Update P and R using Euler's Method
                double P_new = P + dPdt * deltaT;
                double R_new = R + dRdt * deltaT;

                // Compute changes
                double deltaP = Math.abs(P_new - P);
                double deltaR = Math.abs(R_new - R);

                // Update P and R for next iteration
                P = P_new;
                R = R_new;

                // Check for stabilization
                if (deltaP < tolerance && deltaR < tolerance) {
                    stabilizeCount++;
                } else {
                    stabilizeCount = 0;
                }

                // Check if stabilization criteria met
                if (stabilizeCount >= requiredStableSteps) {
                    stabilized = true;
                    stabilizeStep = step;
                    break;
                }

                // Optional: Prevent population or rent from becoming negative
                if (P < 0 || R < 0) {
                    break;
                }
            }

            // Final Assessment
            if (P < 0 || R < 0) {
                System.out.println("\nThe system has become unstable (negative population or rent).");
                System.out.printf("Final Population P = %.5f\n", P);
                System.out.printf("Final Rent R = %.5f\n", R);
            } else if (stabilized) {
                double totalTimeYears = (stabilizeStep - stabilizeCount) * deltaT * 10; // Convert decades to years
                System.out.println("\nThe system stabilized after " + totalTimeYears + " years.");
                System.out.printf("Final Population P = %.5f\n", P);
                System.out.printf("Final Rent R = %.5f\n", R);
            } else {
                System.out.println("\nThe system did not stabilize within the given number of iterations.");
                System.out.printf("Final Population P = %.5f\n", P);
                System.out.printf("Final Rent R = %.5f\n", R);
            }

            // Prompt to run another simulation or exit
            System.out.print("\nDo you want to run another simulation? (yes to continue, exit to quit): ");
            scanner.nextLine(); // Consume the newline left-over
            String userChoice = scanner.nextLine();
            if (userChoice.equalsIgnoreCase("exit")) {
                break;
            }
        }

        scanner.close();
        System.out.println("Simulation terminated.");
    }

    // Heaviside Step Function
    public static double heaviside(double x) {
        return x > 0 ? 1.0 : 0.0;
    }
}
