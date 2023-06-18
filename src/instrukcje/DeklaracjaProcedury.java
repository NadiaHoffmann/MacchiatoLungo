package instrukcje;

import wyjatki.*;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DeklaracjaProcedury extends Deklaracja {
    private final String nazwa;
    private final ArrayList<Zmienna> parametry;
    private final ArrayList<Deklaracja> deklaracje;
    private final ArrayList<Instrukcja> instrukcje;

    protected DeklaracjaProcedury(String nazwa, ArrayList<Zmienna> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        this.nazwa = nazwa;
        this.parametry = parametry;
        this.deklaracje = deklaracje;
        this.instrukcje = instrukcje;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        StringBuilder tab = new StringBuilder();
        tab.append("\t".repeat(Math.max(0, liczbaTabow)));
        StringBuilder tekstProcedury = new StringBuilder(tab + "begin procedura " + nazwa + "(" + parametry + ")\n");
        tab.append('\t');
        for (Deklaracja dz : this.deklaracje) {
            tekstProcedury.append(tab);
            tekstProcedury.append(dz.wypisz(liczbaTabow + 1));
            tekstProcedury.append('\n');
        }

        for (Instrukcja i : this.instrukcje) {
            tekstProcedury.append(tab);
            tekstProcedury.append(i.wypisz(liczbaTabow + 1));
            tekstProcedury.append('\n');
        }

        tekstProcedury.append(tab).append("end procedura ").append(nazwa).append("\n");
        return tekstProcedury.toString();
    }

    @Override
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<HashMap<String, Integer>> stosPoziomowProcedur)
            throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        HashMap<String, Procedura> mapaProcedurZBloku = stosProcedur.pop();
        HashMap<String, Integer> poziomyProcedur = stosPoziomowProcedur.pop();
        if(poziomyProcedur.get(this.nazwa) == 0) {
            throw new ProceduraJuzZadeklarowana(this.nazwa);
        }
        else {
            mapaProcedurZBloku.put(nazwa, new Procedura(nazwa, parametry, deklaracje, instrukcje));
            poziomyProcedur.put(nazwa, 0);
            stosProcedur.push(mapaProcedurZBloku);
        }
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<HashMap<String, Integer>> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        try {
            this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
        } catch (NiezadeklarowanaProcedura niezadeklarowanaProcedura) {
            throw new RuntimeException(niezadeklarowanaProcedura);
        }
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<HashMap<String, Integer>> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        try {
            this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
        } catch (NiezadeklarowanaProcedura niezadeklarowanaProcedura) {
            throw new RuntimeException(niezadeklarowanaProcedura);
        }
    }
}
