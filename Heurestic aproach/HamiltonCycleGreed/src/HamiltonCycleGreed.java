import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HamiltonCycleGreed {
    private final int[][] graph;
    private final List<Integer> path = new ArrayList<>();
    private final boolean[] visited;
    private final int vertices;
    private int countOfExecutions = 0;

    /**
     * Generuje graf i inicjalizuje FileWriter
     **/
    public HamiltonCycleGreed(int vertices, int numberOfEdges) {
        this.vertices = vertices;
        this.visited = new boolean[vertices];
        Arrays.fill(this.visited, false);
        this.graph = generateGraph(vertices, numberOfEdges);
        printGraphMatrix();
    }

    public int[][] generateGraph(int V, int numberOfEdges) {
        Random random = new Random();
        int[][] graph = new int[V][V];
        int edgesAdded = 0;

        while (edgesAdded < numberOfEdges) {
            int i = random.nextInt(V);
            int j = random.nextInt(V);

            if (i != j && graph[i][j] == 0) { // Zakłada, że graf jest nieskierowany
                graph[i][j] = 1;
                graph[j][i] = 1;
                edgesAdded++;
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
            System.out.println("Nie znaleziono rozwiązania");
            return;
        }
        this.path.add(startVertex); // Tworzy cykl poprzez powrót do wierzchołka początkowego
        printPath();
    }

    private boolean findNextLowestDegreeVertex(int current) {
        if (path.size() == vertices) {
            return graph[current][path.get(0)] > 0; // Sprawdza, czy ostatni wierzchołek łączy się z pierwszym
        }

        int minDegree = Integer.MAX_VALUE;
        int nextVertex = -1;
        for (int i = 0; i < vertices; i++) {
            if (graph[current][i] > 0 && !visited[i]) {
                int degree = 0;
                for (int j = 0; j < vertices; j++) {
                    if (graph[i][j] > 0) degree++;
                }
                if (degree < minDegree) {
                    minDegree = degree;
                    nextVertex = i;
                }
                countOfExecutions++;
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj liczbę wierzchołków w grafie:");
        int vertices = scanner.nextInt();
        System.out.println("Podaj liczbę krawędzi w grafie:");
        int numberOfEdges = scanner.nextInt();

        int maxEdges = vertices * (vertices - 1) / 2;
        if (numberOfEdges > maxEdges) {
            System.out.println("Liczba krawędzi jest większa niż maksymalna możliwa liczba krawędzi dla danego grafu.");
            return;
        }

        System.out.println("Podaj liczbę powtórzeń:");
        int executions = scanner.nextInt();

        String outputPath = "hamilton_cycle_results.csv";
        try (FileWriter writer = new FileWriter(outputPath, false)) {
            if (new java.io.File(outputPath).length() == 0) {
                writer.write("Czas wykonania (ms),Ścieżka Hamiltona,Liczenie Operacji,Liczba Wierzchołków\n");
            }

            System.out.println("Rozgrzewka JVM...");
            for (int i = 0; i < 3; i++) {
                HamiltonCycleGreed warmupCycleFinder = new HamiltonCycleGreed(vertices, numberOfEdges);
                warmupCycleFinder.findPath();
            }
            System.out.println("...koniec rozgrzewki.");

            for (int i = 0; i < executions; i++) {
                System.out.println("Wykonanie #" + (i + 1));
                HamiltonCycleGreed cycleFinder = new HamiltonCycleGreed(vertices, numberOfEdges);
                long startTime = System.nanoTime();
                cycleFinder.findPath();
                long endTime = System.nanoTime();
                double elapsedTimeInMs = (endTime - startTime) / 1_000_000.0;
                System.out.println("Czas trwania heurystycznego algorytmu wyszukiwania cyklu Hamiltona: " + elapsedTimeInMs + " ms\n");

                String pathString = "[" + cycleFinder.path.toString().replaceAll("[\\[\\]]", "") + "]";
                writer.write(elapsedTimeInMs + "," + pathString + "," + cycleFinder.countOfExecutions + "," + vertices + "\n");
            }
        } catch (IOException e) {
            System.err.println("Wystąpił błąd przy zapisie do pliku: " + e.getMessage());
        }
    }
}
