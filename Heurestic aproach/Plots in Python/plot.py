import pandas as pd
import matplotlib.pyplot as plt

# Załaduj plik csv
file_path = 'hamilton_cycle_results.csv'
df = pd.read_csv(file_path)

# Oblicz średni czas wykonania i średnią liczbę operacji grupowane według liczby wierzchołków i prawdopodobieństwa krawędzi
mean_times = df.groupby(['Liczba Wierzchołków', 'Prawdopodobieństwo Krawędzi'])['Czas wykonania (ms)'].mean().unstack()
mean_operations = (df.groupby(['Liczba Wierzchołków', 'Prawdopodobieństwo Krawędzi'])['Liczenie Operacji']
                   .mean().unstack())

# Wykres średniego czasu wykonania
plt.figure(figsize=(10, 6))
mean_times.plot(marker='o')
plt.title('Średni czas wykonania w zależności od liczby wierzchołków i prawdopodobieństwa krawędzi')
plt.xlabel('Liczba Wierzchołków')
plt.ylabel('Średni czas wykonania (ms)')
plt.legend(title='Prawdopodobieństwo Krawędzi')
plt.grid(True)
plt.show()

# Wykres średniej liczby operacji
plt.figure(figsize=(10, 6))
mean_operations.plot(marker='o')
plt.title('Średnia liczba operacji w zależności od liczby wierzchołków i prawdopodobieństwa krawędzi')
plt.xlabel('Liczba Wierzchołków')
plt.ylabel('Średnia liczba operacji')
plt.legend(title='Prawdopodobieństwo Krawędzi')
plt.grid(True)
plt.show()
