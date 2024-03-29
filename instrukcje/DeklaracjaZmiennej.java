package instrukcje;

import wyjatki.*;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.HashMap;
import java.util.Stack;

public class DeklaracjaZmiennej extends Deklaracja implements ModyfikacjaZmiennych {
    private final char nazwa;
    private final Wyrazenie wyrazenie;

    protected DeklaracjaZmiennej(char nazwa, Wyrazenie wyrazenie) {
        this.nazwa = nazwa;
        this.wyrazenie = wyrazenie;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        return "\t" + this.nazwa + " = " + this.wyrazenie;
    }

    @Override
    protected void wykonaj(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<HashMap<String, Integer>> stosPoziomowProcedur)
            throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        int nazwaJakoIndeks = this.nazwa - 'a';
        if (nazwaJakoIndeks < 0 || nazwaJakoIndeks >= ('z' - 'a' + 1)) {
            throw new NiepoprawnaNazwaZmiennej(this.nazwa);
        }

        Zmienna[] tablicaZmiennychZBloku = stosZmiennych.pop();
        Integer[] poziomyZmiennych = stosPoziomowZmiennych.pop();
        if(poziomyZmiennych[nazwaJakoIndeks] == 0) {
            throw new ZmiennaJuzZadeklarowana(this.nazwa);
        }
        else {
            // setWartosc korzysta ze stosow, z tego powodu poziomyZmiennych i tablicaZmiennychZBloku sa wkladane na stos
            stosPoziomowZmiennych.push(poziomyZmiennych);
            stosZmiennych.push(tablicaZmiennychZBloku);
            tablicaZmiennychZBloku[nazwaJakoIndeks].setWartosc(wyrazenie.wartosc(stosZmiennych, stosPoziomowZmiennych));
            stosPoziomowZmiennych.pop();
            stosZmiennych.pop();
            poziomyZmiennych[nazwaJakoIndeks] = 0;
            stosZmiennych.push(tablicaZmiennychZBloku);
            stosPoziomowZmiennych.push(poziomyZmiennych);
        }
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<HashMap<String, Integer>> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<HashMap<String, Integer>> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
            this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
    }
}
