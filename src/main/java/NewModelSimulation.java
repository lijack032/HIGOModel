import java.util.Scanner;

public class NewModelSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User Inputs
        System.out.println("Euler's Method Simulation for Rent Stabilization System");
        System.out.print("Enter initial population P0: ");
        double P = scanner.nextDouble();

        System.out.print("Enter initial rent R0: ");
        double R = scanner.nextDouble();

        System.out.print("Enter time step size (delta t): ");
        double deltaT = scanner.nextDouble();

        System.out.print("Enter total number of iterations: ");
        int totalSteps = scanner.nextInt();

        // Variables to track stabilization
        double tolerance = 1e-5;
        boolean stabilized = false;
        int stabilizeStep = 0;
        int stablizecount = 0;

        for (int step = 1; step <= totalSteps; step++) {
            // Compute derivatives
            double dPdt = P * (10 - P) - R;
            double dRdt = (R / 10) + (0.5 * R) - 0.25 * Math.pow(R - 4, 2) * heaviside(R - 4);

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
                stablizecount++;
            }

            if (stablizecount >= totalSteps / 10) {
                stabilized = true;
                stabilizeStep = step;
                break;
            }
        }

        // Final Assessment
        if (stabilized) {
            System.out.println("\nThe system stabilized at step " + stabilizeStep + ".");
            System.out.printf("Final Population P = %.5f\n", P);
            System.out.printf("Final Rent R = %.5f\n", R);
        } else {
            System.out.println("\nThe system did not stabilize within the given number of iterations.");
            System.out.printf("Final Population P = %.5f\n", P);
            System.out.printf("Final Rent R = %.5f\n", R);
        }

        scanner.close();
    }

    // Heaviside Step Function
    public static double heaviside(double x) {
        return x > 0 ? 1.0 : 0.0;
    }
}
