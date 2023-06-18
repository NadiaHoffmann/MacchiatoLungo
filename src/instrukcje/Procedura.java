package instrukcje;

import wyjatki.*;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Procedura extends Instrukcja implements ModyfikacjaZmiennych {
    private String nazwa;
    private ArrayList<Zmienna> parametryZdefiniowane;
    private ArrayList<Wyrazenie> parametryPrzekazane;
    private ArrayList<Integer> wartosciParametrow;
    private ArrayList<Deklaracja> deklaracje;
    private ArrayList<Instrukcja> instrukcje;

    private Zmienna[] zmienneZProcedury;
    private Integer[] poziomZmiennych;
    private HashMap<String, Procedura> proceduryZProcedury;

    protected Procedura(String nazwa, ArrayList<Zmienna> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        this.nazwa = nazwa;
        this.parametryZdefiniowane = parametry;
        this.deklaracje = deklaracje == null ? new ArrayList<Deklaracja>() : deklaracje;
        this.instrukcje = instrukcje;
    }

    protected Procedura(String nazwa, ArrayList<Wyrazenie> parametry, Blok blok) {
        this.nazwa = nazwa;
        this.parametryPrzekazane = parametry;
        this.deklaracje = blok.getDeklaracje() == null ? new ArrayList<Deklaracja>() : deklaracje;
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
        String tab = "";
        for (int i = 0; i < liczbaTabow; i++) {
            tab += '\t';
        }
        String tekstProcedury = tab + "begin procedura " + nazwa + "(" + parametryPrzekazane + ")\n";
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
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych)
            throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura, NiepoprawnaLiczbaParametrow {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomowZmiennych);

        for (Deklaracja dz : deklaracje) {
            dz.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
        }

        for (Instrukcja i : instrukcje) {
            i.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
        }
        stosZmiennych.pop();
        stosPoziomowZmiennych.pop();
        stosProcedur.pop();
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura, NiepoprawnaLiczbaParametrow {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomowZmiennych);

        for (Deklaracja dz : deklaracje) {
            odpluskwiacz.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, dz);
            odpluskwiacz.zmniejszLicznikInstrukcjiWProgramie();
            dz.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, odpluskwiacz);
        }


        for (Instrukcja instrukcja : instrukcje) {
            odpluskwiacz.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, instrukcja);
            odpluskwiacz.zmniejszLicznikInstrukcjiWProgramie();
            instrukcja.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, odpluskwiacz);
        }

        stosZmiennych.pop();
        stosPoziomowZmiennych.pop();
        stosProcedur.pop();
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura, NiepoprawnaLiczbaParametrow {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomowZmiennych);

        for (Deklaracja dz : deklaracje) {
            odpluskwiacz.zwiekszLicznikInstrukcjiWProgramie();
            dz.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, odpluskwiacz);
        }

        for (Instrukcja i : instrukcje) {
            odpluskwiacz.zwiekszLicznikInstrukcjiWProgramie();
            i.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, odpluskwiacz);
        }

        stosZmiennych.pop();
        stosPoziomowZmiennych.pop();
        stosProcedur.pop();
    }

    @Override
    public Zmienna getWartoscZmiennej(char nazwa, Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomowZmiennych) throws NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        return ModyfikacjaZmiennych.super.getWartoscZmiennej(nazwa, stosZmiennych, stosPoziomowZmiennych);
    }

    // czesc wspolna dla trzech powyzszych komend
    private void czescWspolna(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmienych)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, NiezadeklarowanaProcedura, NiepoprawnaLiczbaParametrow {
        this.zmienneZProcedury = new Zmienna['z' - 'a' + 1];
        this.proceduryZProcedury = new HashMap<String, Procedura>(10);
        this.poziomZmiennych = new Integer['z' - 'a' + 1];

        if (stosProcedur.empty()) {
            throw new NiezadeklarowanaProcedura(nazwa);
        }
        else {
            HashMap<String,Procedura> poprzednieProcedury = stosProcedur.peek();

            if (poprzednieProcedury == null || !poprzednieProcedury.containsKey(nazwa)) {
                throw new NiezadeklarowanaProcedura(nazwa);
            }
            else {
                this.deklaracje = poprzednieProcedury.get(nazwa).deklaracje;
                this.instrukcje = poprzednieProcedury.get(nazwa).instrukcje;
                this.parametryZdefiniowane = poprzednieProcedury.get(nazwa).parametryZdefiniowane;
                
                if (this.parametryPrzekazane.size() != this.parametryPrzekazane.size()) {
                    throw new NiepoprawnaLiczbaParametrow(nazwa);
                }
            }

            for (Wyrazenie w : parametryPrzekazane) {
                wartosciParametrow.add(w.wartosc(stosZmiennych, stosPoziomowZmienych));
            }

            for (int i = 0; i < ; i++) {
                char nazwa = (char) ('a' + i);
                if (poprzedniPoziom[i] == -1) {
                    this.poziomProcedur[i] = -1;
                    proceduryZProcedury[i] = new Procedura(nazwa);
                }
                else {
                    proceduryZProcedury[i] = poprzednieProcedury[i];
                }
            }
        }
        stosProcedur.push(proceduryZProcedury);

    }

    private int znajdz(ArrayList<Zmienna> parametryZdefiniowane, char nazwa) {
        for (int i = 0; i < parametryZdefiniowane.size(); i++) {
            if (parametryZdefiniowane.get(i).getNazwa() == nazwa) {
                return i;
            }
        }

        return -1;
    }
}

