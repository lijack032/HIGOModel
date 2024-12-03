public class ModelSimulation {
    private static double delta;
    private static double currentRent;
    private static double cuurrentPopulation;
    private static double inflationrate;
    private static double populationCapacity;
    private static int steps = 0;

    public ModelSimulation(double delta, double currentRent, double cuurrentPopulation,
                double inflationrate, double populationCapacity) {
        ModelSimulation.delta = delta;
        ModelSimulation.currentRent = currentRent;
        ModelSimulation.cuurrentPopulation = cuurrentPopulation;
        ModelSimulation.inflationrate = inflationrate;
        ModelSimulation.populationCapacity = populationCapacity;
    }
    private static void setCurrentRent(double rent) {
        currentRent = rent;
    }

    private static void setCuurrentPopulation(double Population) {
        cuurrentPopulation = Population;
    }

    public static void simulate() {
        double changeinRent = inflationrate * currentRent;
        double changeinPopulation = cuurrentPopulation * (populationCapacity - cuurrentPopulation)
                - currentRent;
        setCurrentRent(currentRent + delta * changeinRent);
        setCuurrentPopulation(cuurrentPopulation + delta * changeinPopulation);
        steps++;
    }

    public static void main(String[] args) {
        new ModelSimulation(0.0001, 2.5, 5, 0.5, 10);
        while (cuurrentPopulation > 0) {
            simulate();
        }
        System.out.println("The population dies out after "
                + steps * delta * 10 + " years. The rent at the time will be "
                + currentRent * 1000);
    }
}


