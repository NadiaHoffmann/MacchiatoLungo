package instrukcje;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiezadeklarowanaZmienna;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyjatki.ZmiennaJuzZadeklarowana;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.Stack;

public class PrzypisanieWartosci extends Instrukcja implements ModyfikacjaZmiennych{
    private char nazwa;
    private Wyrazenie wyrazenie;

    public PrzypisanieWartosci(char nazwa, Wyrazenie wyrazenie) {
        this.nazwa = nazwa;
        this.wyrazenie = wyrazenie;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        return "\t" + this.nazwa + " := " + this.wyrazenie;
    }

    @Override
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur) throws NiezadeklarowanaZmienna, NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej {
        int nazwaJakoIndeks = this.nazwa - 'a';
        if (nazwaJakoIndeks < ('a' - 'a') || nazwaJakoIndeks >= ('z' - 'a' + 1)) {
            throw new NiepoprawnaNazwaZmiennej(this.nazwa);
        }

        Zmienna zmienna = this.getWartoscZmiennej(this.nazwa, stosZmiennych, stosPoziomowZmiennych);

        if (zmienna == null) {
            throw new NiezadeklarowanaZmienna(this.nazwa);
        }
        else {
            zmienna.setWartosc(this.wyrazenie.wartosc(stosZmiennych, stosPoziomowZmiennych));
            stosPoziomowZmiennych.elementAt(0)[nazwaJakoIndeks] = 0;
        }
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz) throws NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, NiepoprawnaWartoscZmiennej {
        this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz) throws NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna {
        this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
    }
}
