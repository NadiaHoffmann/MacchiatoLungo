package instrukcje;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyjatki.NiezadeklarowanaZmienna;
import wyjatki.ZmiennaJuzZadeklarowana;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.Stack;

public class Procedura extends Instrukcja implements ModyfikacjaZmiennych {
    private char nazwa;
    private ArrayList<Wyrazenie> parametry;
    private ArrayList<DeklaracjaZmiennej> deklaracjeZmiennych;
    private ArrayList<Instrukcja> instrukcje;
    @Override
    protected String wypisz(int liczbaTabow) {
        return null;
    }

    @Override
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej {

    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomow, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana {

    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomow, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna {

    }

    @Override
    public Zmienna getWartoscZmiennej(char nazwa, Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        return ModyfikacjaZmiennych.super.getWartoscZmiennej(nazwa, stosZmiennych, stosPoziomow);
    }
}
