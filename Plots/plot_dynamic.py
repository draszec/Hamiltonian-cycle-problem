import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

file_path = '../results/dynamic.csv'
df = pd.read_csv(file_path)

df['log_time'] = np.log2(df['time'])
df['log_operations_count'] = np.log2(df['operations_count'])

df_grouped = df.groupby(['n-size', 'edge_probability'])
df_grouped_size = df.groupby(['n-size'])

mean_times = df_grouped['time'].mean().unstack()
min_times, max_times = df_grouped_size['time'].min(), df_grouped_size['time'].max()
mean_operations = df_grouped['operations_count'].mean().unstack()
min_operations, max_operations = df_grouped_size['operations_count'].min(), df_grouped_size['operations_count'].max()

mean_log_times = df_grouped['log_time'].mean().unstack()
min_log_times, max_log_times = df_grouped_size['log_time'].min(), df_grouped_size['log_time'].max()
mean_log_operations = df_grouped['log_operations_count'].mean().unstack()
min_log_operations, max_log_operations = df_grouped_size['log_operations_count'].min(), df_grouped_size['log_operations_count'].max()


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

# Wykres średniego logarytmu czasu wykonania
plt.figure(figsize=(10, 6))
m1 = mean_log_times.plot(marker='o')
m2 = min_log_times.plot(ax=m1, color='violet', marker='o', label="minimalny czas")
max_log_times.plot(ax=m2, marker='o', label="maksymalny czas")
plt.title('Średni logarytm czasu wykonania w zależności \n od liczby wierzchołków i prawdopodobieństwa krawędzi')
plt.xlabel('Liczba Wierzchołków')
plt.ylabel('Średni logarytm czasu wykonania (lg(s))')
plt.legend(title='Prawdopodobieństwo Krawędzi')
plt.grid(True)
plt.show()

# Wykres średniego logarytmu liczby operacji
plt.figure(figsize=(10, 6))
m1 = mean_log_operations.plot(marker='o')
m2 = min_log_operations.plot(ax=m1, color='violet', marker='o', label="minimalna liczba")
max_log_operations.plot(ax=m2, marker='o', label="maksymalna liczba")
plt.title('Średni logarytm liczby operacji w zależności \n od liczby wierzchołków i prawdopodobieństwa krawędzi')
plt.xlabel('Liczba Wierzchołków')
plt.ylabel('Średnia liczba operacji')
plt.legend(title='Prawdopodobieństwo Krawędzi')
plt.grid(True)
plt.show()
