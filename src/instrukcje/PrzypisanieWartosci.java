package instrukcje;

import wyjatki.*;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.HashMap;
import java.util.Stack;

public class PrzypisanieWartosci extends Instrukcja implements ModyfikacjaZmiennych{
    private final char nazwa;
    private final Wyrazenie wyrazenie;

    protected PrzypisanieWartosci(char nazwa, Wyrazenie wyrazenie) {
        this.nazwa = nazwa;
        this.wyrazenie = wyrazenie;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        return "\t" + this.nazwa + " := " + this.wyrazenie;
    }

    @Override
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        int nazwaJakoIndeks = this.nazwa - 'a';
        if (nazwaJakoIndeks < 0 || nazwaJakoIndeks >= ('z' - 'a' + 1)) {
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
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
    }
}
