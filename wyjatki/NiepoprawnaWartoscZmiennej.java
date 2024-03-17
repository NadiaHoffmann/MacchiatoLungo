package wyjatki;

import wyrazenia.Wyrazenie;

public class NiepoprawnaWartoscZmiennej extends Throwable {
    private final Wyrazenie wyrazenie;

    public NiepoprawnaWartoscZmiennej(Wyrazenie wyrazenie) {
        this.wyrazenie = wyrazenie;
    }

    public Wyrazenie getWyrazenie() {
        return wyrazenie;
    }
}
