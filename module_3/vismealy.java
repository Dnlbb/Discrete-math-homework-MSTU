import java.util.Scanner;

public class MealyMachineGraphviz {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Reading the input
        int n = scanner.nextInt(); // Number of states
        int m = scanner.nextInt(); // Size of the input alphabet
        int q0 = scanner.nextInt(); // Initial state (not used for DOT output)

        int[][] delta = new int[n][m]; // Transition matrix
        String[][] phi = new String[n][m]; // Output matrix

        // Read transition matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                delta[i][j] = scanner.nextInt();
            }
        }

        // Read output matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                phi[i][j] = scanner.next();
            }
        }

        scanner.close();

        // Generate DOT output
        System.out.println("digraph {");
        System.out.println("    rankdir = LR");

        char inputSignal = 'a';
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int targetState = delta[i][j];
                String outputSignal = phi[i][j];
                System.out.printf("    %d -> %d [label = \"%c(%s)\"]\n", i, targetState, (char)(inputSignal + j), outputSignal);
            }
        }

        System.out.println("}");
    }
}
