Funkcja ZnajdzSciezke

    startowyWierzcholek ← znajdzWierzcholekZNajwiekszymStopniem()
    odwiedzone[startowyWierzcholek] ← prawda
    sciezka.dodaj(startowyWierzcholek)

    jeżeli nie znajdzNastepnyWierzcholekZNajmniejszymStopniem(startowyWierzcholek) wtedy
        cyklZnaleziony ← fałsz
        wypisz("Nie znaleziono rozwiązania")
        zwroc
    koniec jeżeli

    sciezka.dodaj(startowyWierzcholek) // Tworzy cykl poprzez powrót do wierzchołka początkowego
    cyklZnaleziony ← prawda
    wypiszSciezke()

Funkcja znajdzWierzcholekZNajwiekszymStopniem

    maksymalnyStopien ← -1
    wierzcholek ← -1
    dla i od 0 do wierzcholki - 1 zrob
        stopien ← 0
        dla j od 0 do wierzcholki - 1 zrob
            jeżeli graf[i][j] > 0 wtedy
                stopien ← stopien + 1
            koniec jeżeli
        koniec dla
        jeżeli stopien > maksymalnyStopien wtedy
            maksymalnyStopien ← stopien
            wierzcholek ← i
        koniec jeżeli
    koniec dla
    zwroc wierzcholek

Funkcja ZnajdźNastępnyWierzchołekONajniższymStopniu(current)

     jeżeli sciezka.rozmiar() = wierzcholki wtedy
        liczbaWykonan ← liczbaWykonan + 1 // Dla końcowego sprawdzenia
        zwroc graf[obecny][sciezka.pobierz(0)] > 0 // Sprawdza, czy ostatni wierzchołek łączy się z pierwszym
    koniec jeżeli

    minimalnyStopien ← MAKS_WARTOSC
    nastepnyWierzcholek ← -1
    dla i od 0 do wierzcholki - 1 zrob
        liczbaWykonan ← liczbaWykonan + 1 // Dla porównania w warunku pętli
        jeżeli graf[obecny][i] > 0 oraz nieodwiedzone[i] wtedy
            liczbaWykonan ← liczbaWykonan + 1 // Dla warunku if
            stopien ← 0
            dla j od 0 do wierzcholki - 1 zrob
                liczbaWykonan ← liczbaWykonan + 1 // Dla porównania w warunku pętli
                jeżeli graf[i][j] > 0 wtedy
                    stopien ← stopien + 1
                koniec jeżeli
                liczbaWykonan ← liczbaWykonan + 1 // Dla warunku if
            koniec dla
            jeżeli stopien < minimalnyStopien wtedy
                minimalnyStopien ← stopien
                nastepnyWierzcholek ← i
                liczbaWykonan ← liczbaWykonan + 1 // Dla warunku if i przypisania
            koniec jeżeli
        koniec jeżeli
    koniec dla

    jeżeli nastepnyWierzcholek = -1 wtedy
        zwroc fałsz // Brak dostępnych nieodwiedzonych wierzchołków
    koniec jeżeli

    odwiedzone[nastepnyWierzcholek] ← prawda
    sciezka.dodaj(nastepnyWierzcholek)
    zwroc znajdzNastepnyWierzcholekZNajmniejszymStopniem(nastepnyWierzcholek)
