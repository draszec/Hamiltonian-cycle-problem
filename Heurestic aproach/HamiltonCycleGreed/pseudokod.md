Funkcja ZnajdźŚcieżkęHamiltona

    startVertex <- ZnajdźWierzchołekONajwyższymStopniu
    Oznacz startVertex jako odwiedzony
    Dodaj startVertex do ścieżki

    Jeśli !ZnajdźNastępnyWierzchołekONajniższymStopniu(startVertex):
        Wydrukuj "Nie udało się znaleźć ścieżki Hamiltona."
        Zwróć

    Dodaj startVertex do ścieżki (tworzenie cyklu)
    WydrukujŚcieżkę

Funkcja ZnajdźWierzchołekONajwyższymStopniu

    maxDegree <- -1 
    vertex <- -1
    Dla każdego i od 0 do vertices-1:
    degree <- 0
    Dla każdego j od 0 do vertices-1:
    Jeśli graph[i][j] > 0:
    degree <- degree + 1
    Jeśli degree > maxDegree:
    maxDegree <- degree
    vertex <- i
    Zwróć vertex

Funkcja ZnajdźNastępnyWierzchołekONajniższymStopniu(current)

    Jeśli rozmiar ścieżki == vertices:
    Zwróć czy graph[current][pierwszy element ścieżki] > 0

    minDegree <- nieskończoność
    nextVertex <- -1
    Dla każdego i od 0 do vertices-1:
        Jeśli graph[current][i] > 0 i i nie jest odwiedzone:
            degree <- 0
            Dla każdego j od 0 do vertices-1:
                Jeśli graph[i][j] > 0:
                    degree <- degree + 1
            Jeśli degree < minDegree:
                minDegree <- degree
                nextVertex <- i

    Jeśli nextVertex == -1:
        Zwróć false (brak dostępnych nieodwiedzonych wierzchołków)

    Oznacz nextVertex jako odwiedzony
    Dodaj nextVertex do ścieżki
    Zwróć ZnajdźNastępnyWierzchołekONajniższymStopniu(nextVertex)

