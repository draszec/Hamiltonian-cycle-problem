import random
import time

adjacency_list: tuple[tuple[int]]


def generate_graph(size: int, number_of_edges: int) -> tuple[tuple[int]]:
    assert size > 0 and number_of_edges >= 0
    # assert number_of_edges <= size * (size - 1) / 2

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


def hamiltonian_cycle() -> bool:
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

    for _, last_node in hamiltonian_list[-1]:
        if starting_node in adjacency_list[last_node]:
            return True

    return False


if __name__ == "__main__":
    adjacency_list = generate_graph(20, 50)

    entry = time.time()
    cycle = hamiltonian_cycle()
    end = time.time()
    print(end - entry)

    # print(adjacency_list)
    print(cycle)

