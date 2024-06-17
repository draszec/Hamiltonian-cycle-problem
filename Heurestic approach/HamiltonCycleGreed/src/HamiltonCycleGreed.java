import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HamiltonCycleGreed {
    private final int[][] graph;
    private final List<Integer> path = new ArrayList<>();
    private final boolean[] visited;
    private final int vertices;
    private int countOfExecutions = 0;
    private boolean cycleFound = false;

    /**
     * Generuje graf i initialize FileWriter
     **/
    public HamiltonCycleGreed(int vertices, double edgeProbability) {
        this.vertices = vertices;
        this.visited = new boolean[vertices];
        Arrays.fill(this.visited, false);
        this.graph = generateGraph(vertices, edgeProbability);
        printGraphMatrix();
    }

    public int[][] generateGraph(int V, double edgeProbability) {
        Random random = new Random();
        int[][] graph = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) { // Zakłada, że graf jest nieskierowany
                if (random.nextDouble() < edgeProbability) {
                    graph[i][j] = 1;
                    graph[j][i] = 1;
                }
            }
        }
        return graph;
    }

    private int findHighestDegreeVertex() {
        int maxDegree = -1;
        int vertex = -1;
        for (int i = 0; i < this.vertices; i++) {
            int degree = 0;
            for (int j = 0; j < this.vertices; j++) {
                if (graph[i][j] > 0) degree++;
            }
            if (degree > maxDegree) {
                maxDegree = degree;
                vertex = i;
            }
        }
        return vertex;
    }

    /**
     * Główna funkcja do znalezienia ścieżki Hamiltona zaczynając od wierzchołka o najwyższym stopniu
     **/
    public void findPath() {
        int startVertex = findHighestDegreeVertex();
        this.visited[startVertex] = true;
        this.path.add(startVertex);

        if (!findNextLowestDegreeVertex(startVertex)) {
            cycleFound = false;
            System.out.println("Nie znaleziono rozwiązania");
            return;
        }
        this.path.add(startVertex); // Tworzy cykl poprzez powrót do wierzchołka początkowego
        cycleFound = true;
        printPath();
    }

    private boolean findNextLowestDegreeVertex(int current) {
        if (path.size() == vertices) {
            countOfExecutions++; // Dla końcowego sprawdzenia
            return graph[current][path.get(0)] > 0; // Sprawdza, czy ostatni wierzchołek łączy się z pierwszym
        }

        int minDegree = Integer.MAX_VALUE;
        int nextVertex = -1;
        for (int i = 0; i < vertices; i++) {
            countOfExecutions++; // Dla porównania w warunku pętli
            if (graph[current][i] > 0 && !visited[i]) {
                countOfExecutions++; // Dla warunku if
                int degree = 0;
                for (int j = 0; j < vertices; j++) {
                    countOfExecutions++; // Dla porównania w warunku pętli
                    if (graph[i][j] > 0) degree++;
                    countOfExecutions++; // Dla warunku if
                }
                if (degree < minDegree) {
                    minDegree = degree;
                    nextVertex = i;
                    countOfExecutions++; // Dla warunku if i przypisania
                }
            }
        }

        if (nextVertex == -1) return false; // Brak dostępnych nieodwiedzonych wierzchołków

        visited[nextVertex] = true;
        path.add(nextVertex);
        return findNextLowestDegreeVertex(nextVertex);
    }

    private void printGraphMatrix() {
        System.out.println("Wygenerowana macierz grafu:");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println(); // Nowa linia po każdym wierszu
        }
    }

    private void printPath() {
        System.out.println("Ścieżka Hamiltona:");
        for (Integer vertex : path) {
            System.out.print(vertex + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //region Hardcoded parametry programu
        int[] vertexCounts = {10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60};
        double[] edgeProbabilities = {0.3, 0.5, 0.7};
        int executions = 300;
        //endregion
        String outputPath = "hamilton_cycle_results.csv";
        try (FileWriter writer = new FileWriter(outputPath, false)) {
            if (new java.io.File(outputPath).length() == 0) {
                writer.write("Czas wykonania (ms),Czy znaleziono cykl Hamiltona,Liczenie Operacji,Liczba Wierzchołków,Prawdopodobieństwo Krawędzi\n");
            }

            System.out.println("Rozgrzewka JVM...");
            for (int i = 0; i < 3; i++) {
                HamiltonCycleGreed warmupCycleFinder = new HamiltonCycleGreed(vertexCounts[0], edgeProbabilities[0]);
                warmupCycleFinder.findPath();
            }
            System.out.println("...koniec rozgrzewki.");

            for (int vertices : vertexCounts) {
                for (double edgeProbability : edgeProbabilities) {
                    for (int i = 0; i < executions; i++) {
                        System.out.println("Wykonanie #" + (i + 1) + " dla liczby wierzchołków: " + vertices + " i prawdopodobieństwa krawędzi: " + edgeProbability);
                        HamiltonCycleGreed cycleFinder = new HamiltonCycleGreed(vertices, edgeProbability);
                        long startTime = System.nanoTime();
                        cycleFinder.findPath();
                        long endTime = System.nanoTime();
                        double elapsedTimeInMs = (endTime - startTime) / 1_000_000.0;
                        System.out.println("Czas trwania heurystycznego algorytmu wyszukiwania cyklu Hamiltona: " + elapsedTimeInMs + " ms\n");

                        writer.write(elapsedTimeInMs + "," + cycleFinder.cycleFound + "," + cycleFinder.countOfExecutions + "," + vertices + "," + edgeProbability + "\n");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Wystąpił błąd przy zapisie do pliku: " + e.getMessage());
        }
    }
}
