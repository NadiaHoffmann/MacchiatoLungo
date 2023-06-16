package porownania;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiezadeklarowanaZmienna;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.Stack;

public class MniejszeRowne extends Porownanie {
    private MniejszeRowne(Wyrazenie wyr1, Wyrazenie wyr2) {
        super(wyr1, wyr2);
    }

    public MniejszeRowne coOdCzego(Wyrazenie wyr1, Wyrazenie wyr2) {
        return new MniejszeRowne(wyr1, wyr2);
    }

    @Override
    protected int priorytet() {
        return 2000;
    }

    @Override
    public boolean ewaluuj(Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej, NiepoprawnaWartoscZmiennej {
        return wyr1.wartosc(stosZmiennych, stosPoziomow) <= wyr2.wartosc(stosZmiennych, stosPoziomow);
    }

    @Override
    public String nazwa() {
        return " <= ";
    }
}
