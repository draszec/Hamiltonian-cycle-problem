import random

import numpy as np
import time

adjacency_list = \
    (
        (1, 2, 5),
        (0, 3, 5),
        (0, 4),
        (1, 4, 6),
        (2, 3, 6),
        (1, 0, 6),
        (3, 4, 5),
    )


def generate_graph(size: int, number_of_edges: int):
    assert size > 0 and number_of_edges >= 0
    assert number_of_edges <= size * (size - 1) / 2

    adjacency_list = []
    for i in range(size):
        adjacency_list.append([])

    for i in range(number_of_edges):
        first_node = random.randint(0, size - 1)
        second_node = random.randint(0, size - 1)
        while first_node == second_node:
            first_node = random.randint(0, size - 1)
            second_node = random.randint(0, size - 1)

        adjacency_list[first_node].append(second_node)
        adjacency_list[second_node].append(first_node)

    for i in range(len(adjacency_list)):
        adjacency_list[i] = tuple(set(adjacency_list[i]))

    return tuple(adjacency_list)

# ZMIENIĆ IDENTYFIKACJĘ WĘZŁÓW NA NUMERKI ZAMIAST KROTKI, BO JEST BŁĄD
def hamiltonian_cycle() -> bool:
    starting_node = adjacency_list[0]

    hamiltonian_list = []
    for i in range(len(adjacency_list)):
        hamiltonian_list.append([])

    hamiltonian_list[0].append(({starting_node}, starting_node))

    # Number of iterations = number of vertices - 1
    for i in range(len(adjacency_list) - 1):
        for path_and_last_node_pair in hamiltonian_list[i]:
            path = path_and_last_node_pair[0]
            last_node = path_and_last_node_pair[1]
            for adjacent_node_number in last_node:
                #   BŁĄD - UZNAJE SIĘ ZA TEN SAM WĘZEŁ, JEŚLI OBA TE WĘZŁY ŁĄCZĄ SIĘ Z TYMI SAMYMI WĘZŁAMI
                if adjacency_list[adjacent_node_number] not in path:
                    temp_set = set()
                    temp_set.add(adjacency_list[adjacent_node_number])
                    new_path = path.union(temp_set)
                    hamiltonian_list[i + 1].append((new_path, adjacency_list[adjacent_node_number]))

    for S in hamiltonian_list[len(adjacency_list) - 1]:
        if 0 in S[1]:
            return True

    return False


if __name__ == "__main__":
    adjacency_list = generate_graph(4, 6)

    # entry = time.time()
    cycle = hamiltonian_cycle()
    # end = time.time()
    # print(end - entry)

    print(adjacency_list)
    print(cycle)