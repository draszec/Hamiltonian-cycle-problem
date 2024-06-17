import matplotlib.pyplot as plt
import pandas as pd

# Załaduj plik csv
file_path = '../results/heuristic.csv'
df = pd.read_csv(file_path)

# Oblicz średni czas wykonania i średnią liczbę operacji grupowane według liczby wierzchołków
# i prawdopodobieństwa krawędzi
mean_times = df.groupby(['n-size', 'edge_probability'])['time'].mean().unstack()
mean_operations = df.groupby(['n-size', 'edge_probability'])['operations_count'].mean().unstack()

# Przykład zmiany podpisów linii wykresów
# Załóżmy, że chcesz zmienić podpisy na "Low", "Medium", "High" zamiast wartości prawdopodobieństwa
new_labels = {

    0.3: 'Niskie',
    0.5: 'Średnie',
    0.7: 'Wysokie',

}

# Zmiana etykiet
mean_times = mean_times.rename(columns=new_labels)
mean_operations = mean_operations.rename(columns=new_labels)

# Wykres średniego czasu wykonania
plt.figure(figsize=(10, 6))
mean_times.plot(marker='o')
plt.title('Średni czas wykonania w zależności \n od liczby wierzchołków i prawdopodobieństwa krawędzi')
plt.xlabel('Liczba Wierzchołków')
plt.ylabel('Średni czas wykonania (ms)')
plt.legend(title='Prawdopodobieństwo Krawędzi')
plt.grid(True)
plt.show()

# Wykres średniej liczby operacji
plt.figure(figsize=(10, 6))
mean_operations.plot(marker='o')
plt.title('Średnia liczba operacji w zależności \n od liczby wierzchołków i prawdopodobieństwa krawędzi')
plt.xlabel('Liczba Wierzchołków')
plt.ylabel('Średnia liczba operacji')
plt.legend(title='Prawdopodobieństwo Krawędzi')
plt.grid(True)
plt.show()
