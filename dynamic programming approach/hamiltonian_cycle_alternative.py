import random
import time
import numpy


def generate_graph(size: int, number_of_edges: int) -> tuple[tuple[int]]:
    assert size > 0 and number_of_edges >= 0

    new_adjacency_list = []
    for i in range(size):
        new_adjacency_list.append([])

    for i in range(number_of_edges):
        first_node = random.randint(0, size - 1)
        second_node = random.randint(0, size - 1)
        while first_node == second_node:
            first_node = random.randint(0, size - 1)
            second_node = random.randint(0, size - 1)

        new_adjacency_list[first_node].append(second_node)
        new_adjacency_list[second_node].append(first_node)

    for i in range(len(new_adjacency_list)):
        new_adjacency_list[i] = tuple(set(new_adjacency_list[i]))

    return tuple(new_adjacency_list)


def hamiltonian_cycle(adjacency_list: tuple[tuple[int]]) -> bool:

    def node_in_subset(node: int, subset: int) -> bool:
        if (subset >> node) & 1 == 1:
            return True
        else:
            return False

    def subset_with_added_node(old_subset: int, node: int) -> int:
        return (1 << node) | old_subset

    size = len(adjacency_list)
    number_of_nodes_subsets = 1 << size  # "1 << var" is the same as "2 ** var" but more efficient
    hamiltonian_table = numpy.full((number_of_nodes_subsets, size), False)

    starting_node = 0

    first_subset = subset_with_added_node(0, starting_node)
    hamiltonian_table[first_subset, starting_node] = True

    for current_subset in range(1, number_of_nodes_subsets):
        for node in range(size):
            if node_in_subset(node, current_subset) and hamiltonian_table[current_subset, node]:
                for neighbor in adjacency_list[node]:
                    if neighbor is not node_in_subset(node, current_subset):
                        new_subset = subset_with_added_node(current_subset, neighbor)
                        hamiltonian_table[new_subset, neighbor] = True

    for node in range(size):
        if hamiltonian_table[number_of_nodes_subsets - 1, node] and node in adjacency_list[starting_node]:
            return True

    return False


if __name__ == "__main__":
    adjacency_list = generate_graph(20, 50)

    entry = time.time()
    cycle = hamiltonian_cycle(adjacency_list)
    end = time.time()
    print(end - entry)

    # print(adjacency_list)
    print(cycle)
