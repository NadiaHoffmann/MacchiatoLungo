package instrukcje;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiezadeklarowanaZmienna;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyjatki.ZmiennaJuzZadeklarowana;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.Stack;

public class InstrukcjaPrint extends Instrukcja {
    private final Wyrazenie wyrazenie;

    public InstrukcjaPrint(Wyrazenie wyrazenie) {
        this.wyrazenie = wyrazenie;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        return "\t" + "print " + this.wyrazenie;
    }

    @Override
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        String wartosc;
        try {
            wartosc = String.valueOf(this.wyrazenie.wartosc(stosZmiennych, stosPoziomow));
        }
        catch (Exception e) {
            wartosc = "";
        }
        System.out.println(wartosc);
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna {
        this.wykonaj(stosZmiennych, stosPoziomow);
    }

    // nie wypisuje, bo tylko liczy kroki
    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow, Odpluskwiacz odpluskwiacz) throws NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna {
        String wartosc = String.valueOf(this.wyrazenie.wartosc(stosZmiennych, stosPoziomow));
    }
}