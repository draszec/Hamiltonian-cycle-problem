import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HamiltonCycleGreed {
    private final int[][] graph;
    private final List<Integer> path = new ArrayList<>();
    private final boolean[] visited;
    private final int vertices;
    private final FileWriter writer; // Dodanie obiektu FileWriter

    /**Generuje graf i inicjalizacja FileWriter **/
    public HamiltonCycleGreed(int vertices, double edgeProbability, FileWriter writer) {
        this.vertices = vertices;
        this.visited = new boolean[vertices];
        Arrays.fill(this.visited, false);
        this.graph = generateGraph(vertices, edgeProbability);
        this.writer = writer; // Inicjalizacja FileWriter
        printGraphMatrix();
    }


    public int[][] generateGraph(int V, double edgeProbability) {
        Random random = new Random();
        int[][] graph = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) { // Ustawiamy, że graf jest nieskierowany
                if (random.nextDouble() < edgeProbability) {
                    graph[i][j] = 1;
                    graph[j][i] = 1;
                }
            }
        }
        return graph;
    }

    // Funkcja do znalezienia wierzchołka o najwyższym stopniu
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

    // Główna funkcja do znalezienia ścieżki Hamiltona zaczynając od wierzchołka o najwyższym stopniu
    public boolean findPath() {
        int startVertex = findHighestDegreeVertex();
        this.visited[startVertex] = true;
        this.path.add(startVertex);

        if (!findNextLowestDegreeVertex(startVertex)) {
            System.out.println("Nie udało się znaleźć ścieżki Hamiltona.");
            return false;
        }
        this.path.add(startVertex); // Tworzenie cyklu poprzez powrót do wierzchołka startowego
        printPath();
        return true;
    }

    /** Rekurencyjna funkcja do wyboru następnego wierzchołka o najniższym stopniu**/
    private boolean findNextLowestDegreeVertex(int current) {
        if (path.size() == vertices) {
            return graph[current][path.get(0)] > 0; // Sprawdź, czy ostatni wierzchołek łączy się z pierwszym
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
            }
        }

        if (nextVertex == -1) return false; // Brak dostępnych nieodwiedzonych wierzchołków

        visited[nextVertex] = true;
        path.add(nextVertex);
        return findNextLowestDegreeVertex(nextVertex);
    }

    /** Metoda do wydrukowania macierzy grafu**/
    private void printGraphMatrix() {
        System.out.println("Wygenerowana macierz grafu:");
        StringBuilder sb = new StringBuilder("Wygenerowana macierz grafu:\n");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                System.out.print(graph[i][j] + " ");
                sb.append(graph[i][j]).append(" ");
            }
            System.out.println(); // Nowa linia po każdym wierszu
            sb.append("\n");
        }
        try {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.err.println("Nie można zapisać macierzy grafu do pliku: " + e.getMessage());
        }
    }


    // Funkcja pomocnicza do drukowania ścieżki

    private void printPath() {
        System.out.println("Ścieżka Hamiltona:");
        StringBuilder sb = new StringBuilder("Ścieżka Hamiltona:\n");
        for (Integer vertex : path) {
            System.out.print(vertex + " ");
            sb.append(vertex).append(" ");
        }
        System.out.println();
        sb.append("\n");
        try {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.err.println("Nie można zapisać ścieżki Hamiltona do pliku: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj liczbę wierzchołków w grafie:");
        int vertices = scanner.nextInt();
        System.out.println("Podaj prawdopodobieństwo krawędzi (0-1):");
        double edgeProbability = scanner.nextDouble();
        System.out.println("Podaj liczbę wykonanych algorytmów:");
        int executions = scanner.nextInt();

        try (FileWriter writer = new FileWriter("wyniki_algorytmu.txt", false)) { // Otwarcie pliku do dopisywania
            for (int i = 0; i < executions; i++) {
                System.out.println("Wykonanie nr " + (i + 1));
                HamiltonCycleGreed cycleFinder = new HamiltonCycleGreed(vertices, edgeProbability, writer);

                long startTime = System.nanoTime();
                cycleFinder.findPath();
                long endTime = System.nanoTime();
                double elapsedTimeInMs = (endTime - startTime) / 1_000_000.0;
                System.out.println("Czas trwania heurystycznego algorytmu wyszukiwania cyklu Hamiltona: " + elapsedTimeInMs + " ms\n");

                writer.write("Czas trwania algorytmu: " + elapsedTimeInMs + " ms\n\n");
            }
        } catch (IOException e) {
            System.err.println("Wystąpił błąd przy zapisie do pliku: " + e.getMessage());
        }
    }
}
