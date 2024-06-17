import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;


public class HamiltonianCycleBacktracking {
    private int V, pathCount;
    private int[] path;
    private int[][] graph;


    public void findHamiltonianCycle(int[][] g) {
        V = g.length;
        path = new int[V];

        Arrays.fill(path, -1);
        graph = g;
        try {
            path[0] = 0;
            pathCount = 1;
            solve(0);
            System.out.println("No solution");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            display();
        }
    }


    public void solve(int vertex) throws Exception {
        if (graph[vertex][0] == 1 && pathCount == V)
            throw new Exception("Solution found");
        if (pathCount == V)
            return;

        for (int v = 0; v < V; v++) {
            if (graph[vertex][v] == 1) {
                path[pathCount++] = v;
                graph[vertex][v] = 0;
                graph[v][vertex] = 0;

                if (!isPresent(v))
                    solve(v);

                graph[vertex][v] = 1;
                graph[v][vertex] = 1;
                path[--pathCount] = -1;
            }
        }
    }


    public boolean isPresent(int v) {
        for (int i = 0; i < pathCount - 1; i++)
            if (path[i] == v)
                return true;
        return false;
    }


    public void display() {
        System.out.print("\nPath : ");
        for (int i = 0; i <= V; i++)
            System.out.print(path[i % V] +" ");
        System.out.println();
    }


    public int[][] generateGraph(int V, double edgeProbability) {
        Random random = new Random();
        int[][] graph = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) { // Upewnij się, że graf jest nie skierowany
                if (random.nextDouble() < edgeProbability) {
                    graph[i][j] = 1;
                    graph[j][i] = 1;
                }
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("HamiltonianCycle Algorithm Test\n");

        HamiltonianCycle hc = new HamiltonianCycle();

        System.out.println("Enter number of vertices");
        int V = scan.nextInt();

        System.out.println("Enter edge probability (0.0 to 1.0)");
        double edgeProbability = scan.nextDouble();

        // Generate random graph
        int[][] graph = hc.generateGraph(V, edgeProbability);

        // Print vertices before looking for a solution
        System.out.println("Vertices:");
        for (int i = 0; i < V; i++) {
            System.out.print(i + " ");
        }
        System.out.println("\nGenerated graph:");

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }

        long startTime = System.nanoTime();

        // Find Hamiltonian cycle
        hc.findHamiltonianCycle(graph);

        // Record end time
        long endTime = System.nanoTime();

        // Calculate elapsed time in nanoseconds
        long elapsedTimeNano = endTime - startTime;

        // Convert nanoseconds to milliseconds for readability
        double elapsedTimeMilli = elapsedTimeNano / 1_000_000.0;

        System.out.println("\nTime taken: " + elapsedTimeMilli + " milliseconds");
    }
}
