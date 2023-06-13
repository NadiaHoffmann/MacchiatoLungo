package instrukcje;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiezadeklarowanaZmienna;
import wyjatki.ZmiennaJuzZadeklarowana;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.Stack;

public class DeklaracjaZmiennej extends Deklaracja implements ModyfikacjaZmiennych {
    private final char nazwa;
    private Wyrazenie wyrazenie;

    public DeklaracjaZmiennej(char nazwa, Wyrazenie wyrazenie) {
        this.nazwa = nazwa;
        this.wyrazenie = wyrazenie;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        return "\t" + this.nazwa + " = " + this.wyrazenie;
    }

    @Override
    protected void wykonaj(Stack <Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej {
        int nazwaJakoIndeks = this.nazwa - 'a';
        if (nazwaJakoIndeks < ('a' - 'a') || nazwaJakoIndeks >= ('z' - 'a' + 1)) {
            throw new NiepoprawnaNazwaZmiennej(this.nazwa);
        }

        Zmienna[] tablicaZmiennychZBloku = stosZmiennych.pop();
        Integer[] poziomyZmiennych = stosPoziomow.pop();
        if(poziomyZmiennych[nazwaJakoIndeks] == 0) {
            throw new ZmiennaJuzZadeklarowana(this.nazwa);
        }
        else {
            // setWartosc korzysta ze stosow, z tego powodu poziomyZmiennych i tablicaZmiennychZBloku sa wkladane na stos
            stosPoziomow.push(poziomyZmiennych);
            stosZmiennych.push(tablicaZmiennychZBloku);
            tablicaZmiennychZBloku[nazwaJakoIndeks].setWartosc(wyrazenie.wartosc(stosZmiennych, stosPoziomow));
            stosPoziomow.pop();
            stosZmiennych.pop();
            poziomyZmiennych[nazwaJakoIndeks] = 0;
            stosZmiennych.push(tablicaZmiennychZBloku);
            stosPoziomow.push(poziomyZmiennych);
        }
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow, Odpluskwiacz odpluskwiacz) throws NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna {
        this.wykonaj(stosZmiennych, stosPoziomow);
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow, Odpluskwiacz odpluskwiacz) throws NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna {
        this.wykonaj(stosZmiennych, stosPoziomow);
    }
}