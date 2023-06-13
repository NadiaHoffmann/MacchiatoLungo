package porownania;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiezadeklarowanaZmienna;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.Stack;

public abstract class Porownanie {
    protected Wyrazenie wyr1, wyr2;
    protected abstract int priorytet();

    public Porownanie(Wyrazenie wyr1, Wyrazenie wyr2) {
        this.wyr1 = wyr1;
        this.wyr2 = wyr2;
    }

    public abstract boolean ewaluuj(Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej;

    public String toString() {
        String wyr1Str, wyr2Str;
        if(priorytet() <= wyr1.priorytet())
            wyr1Str = wyr1.toString();
        else
            wyr1Str = "(" + wyr1 + ")";

        if(priorytet() < wyr2.priorytet())
            wyr2Str = wyr2.toString();
        else
            wyr2Str = "(" + wyr2 + ")";

        return wyr1Str + nazwa() + wyr2Str;
    }

    public abstract String nazwa();
}
