import matplotlib.pyplot as plt
import pandas as pd

file_path = '../results/heuristic.csv'
df = pd.read_csv(file_path)

df_grouped = df.groupby(['n-size', 'edge_probability'])
df_grouped_size = df.groupby(['n-size'])

mean_times = df_grouped['time'].mean().unstack()
min_times, max_times = df_grouped_size['time'].min(), df_grouped_size['time'].max()
mean_operations = df_grouped['operations_count'].mean().unstack()
min_operations, max_operations = df_grouped_size['operations_count'].min(), df_grouped_size['operations_count'].max()

new_labels = {

    0.3: 'Niskie',
    0.5: 'Średnie',
    0.7: 'Wysokie',

}

# Wykres średniego czasu wykonania
plt.figure(figsize=(10, 6))
m1 = mean_times.plot(marker='o')
m2 = min_times.plot(ax=m1, color='violet', marker='o', label="minimalny czas")
max_times.plot(ax=m2, marker='o', label="maksymalny czas")
plt.title('Średni czas wykonania w zależności \n od liczby wierzchołków i prawdopodobieństwa krawędzi')
plt.xlabel('Liczba Wierzchołków')
plt.ylabel('Średni czas wykonania (s)')
plt.legend(title='Prawdopodobieństwo Krawędzi')
plt.grid(True)
plt.show()

# Wykres średniej liczby operacji
plt.figure(figsize=(10, 6))
m1 = mean_operations.plot(marker='o')
m2 = min_operations.plot(ax=m1, color='violet', marker='o', label="minimalna liczba")
max_operations.plot(ax=m2, marker='o', label="maksymalna liczba")
plt.title('Średnia liczba operacji w zależności \n od liczby wierzchołków i prawdopodobieństwa krawędzi')
plt.xlabel('Liczba Wierzchołków')
plt.ylabel('Średnia liczba operacji')
plt.ticklabel_format(style='plain')
plt.legend(title='Prawdopodobieństwo Krawędzi')
plt.grid(True)
plt.show()
