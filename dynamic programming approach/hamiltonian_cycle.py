import os
import sys
import random
import time
import pandas as pd


# adjacency_list: tuple[tuple[int]]
count_of_algorithm = 0
execution_times = []
step_counts = []
outputs = []

def generate_graph(size: int, density: float) -> tuple[tuple[int]]:
    assert size > 0 and 0 <= density <= 1

    new_adjacency_list = []
    for i in range(size):
        new_adjacency_list.append([])

    for i in range(size - 1):
        for j in range(i+1, size):
            r = random.random()
            if r < density:
                new_adjacency_list[i].append(j)
                new_adjacency_list[j].append(i)

    for i in range(len(new_adjacency_list)):
        new_adjacency_list[i] = tuple(new_adjacency_list[i])

    return tuple(new_adjacency_list)


def hamiltonian_cycle(adjacency_list) -> bool:
    global count_of_algorithm
    hamiltonian_list: list[set[tuple[frozenset, int]]] = []
    for i in range(len(adjacency_list)):
        # t = set()
        # t = t.add(tuple((set, int)))
        hamiltonian_list.append(set())

    starting_node = 0
    for i in range(len(adjacency_list)):
        if len(adjacency_list[i]) > len(adjacency_list[starting_node]):
            starting_node = i

    hamiltonian_list[0].add((frozenset({starting_node}), starting_node))

    # Number of iterations = number of vertices - 1
    for i in range(len(adjacency_list) - 1):
        for path, last_node in hamiltonian_list[i]:
            for adjacent_node in adjacency_list[last_node]:
                if adjacent_node not in path:
                    new_path = {adjacent_node}
                    new_path = frozenset(new_path.union(path))
                    hamiltonian_list[i + 1].add((new_path, adjacent_node))
                    count_of_algorithm += 1

    for _, last_node in hamiltonian_list[-1]:
        if starting_node in adjacency_list[last_node]:
            return True

    return False


args = sys.argv
if len(args) < 4:
    print("No size, density or number of executions given")
    exit(1)
size = int(args[1])
density = float(args[2])
number_of_executions = int(args[3])

# size = 15
# density = 0.3

for _ in range(number_of_executions):
    adjacency_list = generate_graph(size, density)
    entry = time.time()
    cycle = hamiltonian_cycle(adjacency_list)
    end = time.time()
    time_of_execution = end - entry

    execution_times.append(time_of_execution)
    step_counts.append(count_of_algorithm)
    count_of_algorithm = 0
    outputs.append(cycle)

df = pd.DataFrame(
    data={
            'n-size': [size] * number_of_executions,
            'edge_probability': [density] * number_of_executions,
            'operations_count': step_counts,
            'time': execution_times,
            'output': outputs
        }
)

output_path = '../dynamic.csv'
df.to_csv(output_path, mode='a', header=not os.path.exists(output_path), index=False)
