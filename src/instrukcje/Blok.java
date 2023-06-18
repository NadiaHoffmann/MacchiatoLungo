package instrukcje;

import wyjatki.*;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Blok extends Instrukcja {
    private final ArrayList<Instrukcja> instrukcje;
    private final ArrayList<Deklaracja> deklaracje;
    private final ArrayList<DeklaracjaZmiennej> ukryteDeklaracjeZmiennych;
    protected int licznikInstrukcji;

    // konstruktor dla bloku w petli for i procedury
    protected Blok(ArrayList<Deklaracja> deklaracje, ArrayList<DeklaracjaZmiennej> ukryteDeklaracjeZmiennych, ArrayList<Instrukcja> instrukcje) {
        this.deklaracje = deklaracje == null ? new ArrayList<>() : deklaracje;
        this.ukryteDeklaracjeZmiennych = ukryteDeklaracjeZmiennych == null ? new ArrayList<>() : ukryteDeklaracjeZmiennych;
        this.instrukcje = instrukcje;
        this.licznikInstrukcji = instrukcje.size();
    }

    protected Blok(ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        this.deklaracje = deklaracje == null ? new ArrayList<>() : deklaracje;
        this.ukryteDeklaracjeZmiennych = new ArrayList<>();
        this.instrukcje = instrukcje;
        this.licznikInstrukcji = instrukcje.size();
    }

    protected Blok(){
        this.deklaracje = new ArrayList<>();
        this.ukryteDeklaracjeZmiennych = new ArrayList<>();
        this.instrukcje = new ArrayList<>();
        this.licznikInstrukcji = 0;
    }

    protected void dodajInstrukcje(Instrukcja instrukcja) {
        this.instrukcje.add(instrukcja);
    }

    protected void dodajDeklaracje(Deklaracja deklaracja) {
        this.deklaracje.add(deklaracja);
    }

    protected ArrayList<Deklaracja> getDeklaracje() {
        return this.deklaracje;
    }

    protected ArrayList<Instrukcja> getInstrukcje() {
        return this.instrukcje;
    }

    protected void zwiekszLiczbeInstrukcji() {
        this.licznikInstrukcji++;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        StringBuilder tab = new StringBuilder();
        tab.append("\t".repeat(Math.max(0, liczbaTabow)));
        StringBuilder tekstBloku = new StringBuilder(tab + "begin blok\n");
        for (Deklaracja dz : this.deklaracje) {
            tekstBloku.append(tab);
            tekstBloku.append(dz.wypisz(liczbaTabow + 1));
            tekstBloku.append('\n');
        }

        for (Instrukcja i : this.instrukcje) {
            tekstBloku.append(tab);
            tekstBloku.append(i.wypisz(liczbaTabow + 1));
            tekstBloku.append('\n');
        }

        tekstBloku.append(tab).append("end blok");
        return tekstBloku.toString();
    }

    // czesc wspolna dla trzech ponizszych komend
    private void czescWspolna(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmienych) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        Zmienna[] zmienneZBloku = new Zmienna['z' - 'a' + 1];
        HashMap<String, Procedura> proceduryZBloku = new HashMap<>(10);
        Integer[] poziomZmiennych = new Integer['z' - 'a' + 1];

        if (stosPoziomowZmienych.empty()) {
            Arrays.fill(poziomZmiennych, -1);
            for (int i = 0; i < ('z' -'a' + 1); i++) {
                char nazwa = (char) ('a' + i);
                zmienneZBloku[i] = new Zmienna(nazwa);
            }
        }
        else {
            Integer[] poprzedniPoziom = stosPoziomowZmienych.peek();
            Zmienna[] poprzednieZmienne = stosZmiennych.peek();

            for (int i = 0; i < ('z' - 'a' + 1); i++) {
                char nazwa = (char) ('a' + i);
                zmienneZBloku[i] = new Zmienna(nazwa);
                if (poprzedniPoziom[i] == -1) {
                    poziomZmiennych[i] = -1;
                }
                else {
                    poziomZmiennych[i] = poprzedniPoziom[i] + 1;
                    int wartosc = poprzednieZmienne[i].getWartosc();
                    zmienneZBloku[i].setWartosc(wartosc);
                }
            }
        }

        if (stosProcedur.empty()) {
            stosProcedur.push(new HashMap<>(10));
        }
        else {
            HashMap<String, Procedura> poprzednieProcedury = stosProcedur.peek();
            if (poprzednieProcedury != null) {
                proceduryZBloku.putAll(poprzednieProcedury);
            }
            else {
                proceduryZBloku = new HashMap<>(10);
            }
        }

        stosZmiennych.push(zmienneZBloku);
        stosPoziomowZmienych.push(poziomZmiennych);
        stosProcedur.push(proceduryZBloku);

        for (DeklaracjaZmiennej dz : ukryteDeklaracjeZmiennych) {
            dz.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmienych);
        }
    }

    @Override
    protected void wykonaj(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych)
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
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz)
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
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz)
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
}
