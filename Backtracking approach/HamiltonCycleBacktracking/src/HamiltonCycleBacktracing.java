import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HamiltonCycleBacktracing {
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
            System.out.println("Rozwiązanie nie znalezione");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            display();
        }
    }

    public void solve(int vertex) throws Exception {
        if (graph[vertex][0] == 1 && pathCount == V)
            throw new Exception("Rozwiązanie znalezione");
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
        System.out.print("\nŚcieżka: ");
        for (int i = 0; i <= V; i++)
            System.out.print(path[i % V] + " ");
        System.out.println();
    }

    public int[][] generateGraph(int V, double edgeProbability) {
        Random random = new Random();
        int[][] graph = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
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
        HamiltonCycleBacktracing hc = new HamiltonCycleBacktracing();

        System.out.println("Podaj liczbę wierzchołków:");
        int V = scan.nextInt();

        System.out.println("Podaj prawdopodobieństwo krawędzi (0.0 do 1.0):");
        double edgeProbability = scan.nextDouble();

        System.out.println("Podaj liczbę powtórzeń:");
        int repetitions = scan.nextInt();

        try (PrintWriter writer = new PrintWriter(new FileWriter("wyniki_Algorytmu.txt"))) {
            for (int i = 0; i < repetitions; i++) {
                int[][] graph = hc.generateGraph(V, edgeProbability);
                System.out.println("\nWygenerowany graf:");
                for (int[] row : graph) {
                    for (int val : row) {
                        System.out.print(val + " ");
                    }
                    System.out.println();
                }
                long startTime = System.nanoTime();
                hc.findHamiltonianCycle(graph);
                long endTime = System.nanoTime();
                double elapsedTimeMilli = (endTime - startTime) / 1_000_000.0;
                writer.printf("Próba %d: Czas wykonania: %.3f milisekund%n", i + 1, elapsedTimeMilli);
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisu do pliku.");
            e.printStackTrace();
        }
    }
}
