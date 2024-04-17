Funkcja ZnajdźCyklHamiltona(g)

    V <- długość g
    Utwórz tablicę path o rozmiarze V
    Wypełnij tablicę path wartościami -1
    graph <- g
    Ustaw path[0] <- 0
    pathCount <- 1
    Wywołaj Funkcja Rozwiąż(0)
    Jeśli nie zostanie wyrzucony wyjątek:
    Wydrukuj "Rozwiązanie nieznalezione"
    W przeciwnym przypadku:
    Wydrukuj wiadomość wyjątku
    Wywołaj Funkcja WyświetlŚcieżkę

Funkcja Rozwiąż(vertex)

    Jeśli graph[vertex][0] == 1 i pathCount == V:
    Wyrzuć wyjątek "Rozwiązanie znalezione"
    Jeśli pathCount == V:
    Zwróć
    Dla każdego v od 0 do V-1:
    Jeśli graph[vertex][v] == 1:
    Dodaj v do ścieżki i zwiększ pathCount
    Usuń krawędź między vertex a v (ustaw na 0)
    Jeśli v nie jest w obecnej ścieżce:
    Wywołaj rekurencyjnie Funkcja Rozwiąż(v)
    Przywróć krawędź między vertex a v (ustaw na 1)
    Usuń v z ścieżki i zmniejsz pathCount

Funkcja JestObecny(v)

    Dla każdego i od 0 do pathCount-1:
    Jeśli path[i] == v:
    Zwróć true
    Zwróć false
