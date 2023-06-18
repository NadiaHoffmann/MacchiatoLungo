package instrukcje;

import wyjatki.*;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DeklaracjaProcedury extends Deklaracja {
    private String nazwa;
    private ArrayList<Zmienna> parametry;
    private ArrayList<Deklaracja> deklaracje;
    private ArrayList<Instrukcja> instrukcje;

    protected DeklaracjaProcedury(String nazwa, ArrayList<Zmienna> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        this.nazwa = nazwa;
        this.parametry = parametry;
        this.deklaracje = deklaracje;
        this.instrukcje = instrukcje;
    }

    protected DeklaracjaProcedury(String nazwa, ArrayList<Zmienna> parametry, Blok blok) {
        this.nazwa = nazwa;
        this.parametry = parametry;
        this.deklaracje = blok.getDeklaracje();
        this.instrukcje = blok.getInstrukcje();
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
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        HashMap<String, Procedura> mapaProcedurZBloku = stosProcedur.pop();
        if(mapaProcedurZBloku.containsKey(this.nazwa)) {
            throw new ProceduraJuzZadeklarowana(this.nazwa);
        }
        else {
            System.out.println(nazwa);
            for (Wyrazenie w : parametry) {
                System.out.println(w.toString());
            }
            for (Deklaracja d : deklaracje) {
                d.wypisz(0);
            }
            for(Instrukcja i :  instrukcje) {
                i.wypisz(0);
            }
            mapaProcedurZBloku.put(nazwa, new Procedura(nazwa, parametry, deklaracje, instrukcje));
            stosProcedur.push(mapaProcedurZBloku);
        }
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        try {
            this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
        } catch (NiezadeklarowanaProcedura niezadeklarowanaProcedura) {
            throw new RuntimeException(niezadeklarowanaProcedura);
        }
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        try {
            this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
        } catch (NiezadeklarowanaProcedura niezadeklarowanaProcedura) {
            throw new RuntimeException(niezadeklarowanaProcedura);
        }
    }
}
