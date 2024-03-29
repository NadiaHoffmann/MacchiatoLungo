package porownania;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiezadeklarowanaZmienna;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.Stack;

public class WiekszeRowne extends Porownanie {
    private WiekszeRowne(Wyrazenie wyr1, Wyrazenie wyr2) {
        super(wyr1, wyr2);
    }

    public static WiekszeRowne coOdCzego(Wyrazenie wyr1, Wyrazenie wyr2) {
        return new WiekszeRowne(wyr1, wyr2);
    }

    @Override
    protected int priorytet() {
        return 2000;
    }

    @Override
    public boolean ewaluuj(Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        return wyr1.wartosc(stosZmiennych, stosPoziomow) >= wyr2.wartosc(stosZmiennych, stosPoziomow);
    }

    @Override
    public String nazwa() {
        return " >= ";
    }
}
