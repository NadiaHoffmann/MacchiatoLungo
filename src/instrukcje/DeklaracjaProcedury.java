package instrukcje;

import wyjatki.*;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.Stack;

public class DeklaracjaProcedury extends Deklaracja {
    private char nazwa;
    private ArrayList<Wyrazenie> parametry;
    private ArrayList<Deklaracja> deklaracje;
    private ArrayList<Instrukcja> instrukcje;

    protected DeklaracjaProcedury(char nazwa, ArrayList<Wyrazenie> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        this.nazwa = nazwa;
        this.parametry = parametry;
        this.deklaracje = deklaracje;
        this.instrukcje = instrukcje;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        String tab = "";
        for (int i = 0; i < liczbaTabow; i++) {
            tab += '\t';
        }
        String tekstProcedury = tab + "begin procedura " + nazwa + "(" + parametry + ")\n";
        tab += '\t';
        for (Deklaracja dz : this.deklaracje) {
            tekstProcedury += tab;
            tekstProcedury += dz.wypisz(liczbaTabow + 1);
            tekstProcedury += '\n';
        }

        for (Instrukcja i : this.instrukcje) {
            tekstProcedury += tab;
            tekstProcedury += i.wypisz(liczbaTabow + 1);
            tekstProcedury += '\n';
        }

        tekstProcedury += tab + "end procedura " + nazwa + "\n";
        return tekstProcedury;
    }

    @Override
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana {
        int nazwaJakoIndeks = this.nazwa - 'a';
        if (nazwaJakoIndeks < ('a' - 'a') || nazwaJakoIndeks >= ('z' - 'a' + 1)) {
            throw new NiepoprawnaNazwaProcedury(this.nazwa);
        }

        Procedura[] tablicaProcedurZBloku = stosProcedur.pop();
        Integer[] poziomyProcedur = stosPoziomowProcedur.pop();
        if(poziomyProcedur[nazwaJakoIndeks] == 0) {
            throw new ProceduraJuzZadeklarowana(this.nazwa);
        }
        else {
            // setWartosc korzysta ze stosow, z tego powodu poziomyProcedur i tablicaProcedurZBloku sa wkladane na stos
            stosPoziomowProcedur.push(poziomyProcedur);
            stosProcedur.push(tablicaProcedurZBloku);
            tablicaProcedurZBloku[nazwaJakoIndeks] = new Procedura(nazwa, parametry, deklaracje, instrukcje);
            stosPoziomowProcedur.pop();
            stosProcedur.pop();
            poziomyProcedur[nazwaJakoIndeks] = 0;
            stosProcedur.push(tablicaProcedurZBloku);
            stosPoziomowProcedur.push(poziomyProcedur);
        }
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana {
        this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana {
        this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
    }
}
