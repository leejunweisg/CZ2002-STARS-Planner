package app;

/**
 * The STARSPlannerApp class that holds the main method.
 */
public class STARSPlannerApp{

    /**
     * The main method, instantiates the STARSPlanner and runs the menu.
     * @param args Default arguments
     */
    public static void main(String[] args) {
        STARSPlanner sp = new STARSPlanner();
        sp.run();
        System.out.println("Good bye!");
    }
}