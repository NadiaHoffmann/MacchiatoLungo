package instrukcje;

import wyjatki.*;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Procedura extends Instrukcja implements ModyfikacjaZmiennych {
    private final String nazwa;
    private ArrayList<Zmienna> parametryZdefiniowane;
    private ArrayList<Wyrazenie> parametryPrzekazane;
    private ArrayList<Deklaracja> deklaracje;
    private ArrayList<Instrukcja> instrukcje;
    private Blok blokProcedury;

    protected Procedura(String nazwa, ArrayList<Zmienna> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        this.nazwa = nazwa;
        this.parametryZdefiniowane = parametry;
        this.deklaracje = deklaracje == null ? new ArrayList<>() : deklaracje;
        this.instrukcje = instrukcje;
    }

    protected Procedura(String nazwa, ArrayList<Wyrazenie> parametry, Blok blok) {
        this.nazwa = nazwa;
        this.parametryPrzekazane = parametry;
        this.deklaracje = blok.getDeklaracje() == null ? new ArrayList<>() : deklaracje;
        this.instrukcje = blok.getInstrukcje();
    }

    protected Procedura(String nazwa, ArrayList<Wyrazenie> parametry) {
        this.nazwa = nazwa;
        this.parametryPrzekazane = parametry;
    }

    public String getNazwa() {
        return nazwa;
    }

    public ArrayList<Zmienna> getParametry() {
        return parametryZdefiniowane;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        StringBuilder tab = new StringBuilder();
        tab.append("\t".repeat(Math.max(0, liczbaTabow)));
        StringBuilder tekstProcedury = new StringBuilder(tab + "begin procedura " + nazwa + "(" + parametryPrzekazane + ")\n");
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

    // czesc wspolna dla trzech powyzszych komend
    protected void czescWspolna(Stack<HashMap<String, Procedura>> stosProcedur)
            throws NiezadeklarowanaProcedura, NiepoprawnaLiczbaParametrow {
        if (stosProcedur.empty()) {
            throw new NiezadeklarowanaProcedura(nazwa);
        }
        HashMap<String,Procedura> poprzednieProcedury = stosProcedur.peek();

        if (poprzednieProcedury == null || !poprzednieProcedury.containsKey(nazwa)) {
            throw new NiezadeklarowanaProcedura(nazwa);
        }
        else {
            this.deklaracje = poprzednieProcedury.get(nazwa).deklaracje;
            this.instrukcje = poprzednieProcedury.get(nazwa).instrukcje;
            this.parametryZdefiniowane = poprzednieProcedury.get(nazwa).parametryZdefiniowane;

            if (this.parametryPrzekazane.size() != this.parametryZdefiniowane.size()) {
                throw new NiepoprawnaLiczbaParametrow(nazwa);
            }
        }

        ArrayList<DeklaracjaZmiennej> zmienneParametryzowane = new ArrayList<>();

        for (int i = 0; i < this.parametryZdefiniowane.size(); i++) {
            char nazwa = this.parametryZdefiniowane.get(i).getNazwa();
            Wyrazenie wyrazenie = this.parametryPrzekazane.get(i);

            zmienneParametryzowane.add(new DeklaracjaZmiennej(nazwa, wyrazenie));
        }

        this.blokProcedury = new Blok(deklaracje, zmienneParametryzowane, instrukcje);
    }

    @Override
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<HashMap<String, Integer>> stosPoziomowProcedur) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura, NiepoprawnaLiczbaParametrow {
        this.czescWspolna(stosProcedur);
        blokProcedury.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
    }


    @Override
    protected void wykonajZOdpluskwiaczem(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<HashMap<String, Integer>> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura, NiepoprawnaLiczbaParametrow {
        this.czescWspolna(stosProcedur);
        blokProcedury.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur , odpluskwiacz);
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<HashMap<String, Integer>> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura, NiepoprawnaLiczbaParametrow {
        this.czescWspolna(stosProcedur);
        blokProcedury.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
    }

    @Override
    public Zmienna getWartoscZmiennej(char nazwa, Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomowZmiennych) throws NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        return ModyfikacjaZmiennych.super.getWartoscZmiennej(nazwa, stosZmiennych, stosPoziomowZmiennych);
    }
}

