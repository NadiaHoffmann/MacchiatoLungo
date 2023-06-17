package instrukcje;

import porownania.Porownanie;
import wyjatki.*;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Blok extends Instrukcja {
    private Zmienna[] zmienneZBloku;
    private Integer[] poziomZmiennych;
    private Procedura[] proceduryZBloku;
    private Integer[] poziomProcedur;

    private ArrayList<Instrukcja> instrukcje;
    private ArrayList<Deklaracja> deklaracje;
    private ArrayList<DeklaracjaZmiennej> ukryteDeklaracjeZmiennych;
    protected int licznikInstrukcji;

    // konstruktor dla bloku w petli for
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

    protected Blok(Blok blok) {
        this.deklaracje = blok.getDeklaracje() == null ? new ArrayList<>() : deklaracje;
        this.ukryteDeklaracjeZmiennych = new ArrayList<>();
        this.instrukcje = blok.getInstrukcje();
        this.licznikInstrukcji = instrukcje.size();
    }

    protected void dodajInstrukcje(Instrukcja instrukcja) {
        this.instrukcje.add(instrukcja);
    }

    protected void dodajDeklaracje(Deklaracja deklaracja) {
        this.deklaracje.add(deklaracja);
    }

    protected ArrayList<Deklaracja> getDeklaracje() {
        return deklaracje;
    }

    protected ArrayList<Instrukcja> getInstrukcje() {
        return instrukcje;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        String tab = "";
        for (int i = 0; i < liczbaTabow; i++) {
            tab += '\t';
        }
        String tekstBloku = tab + "begin blok\n";
        for (Deklaracja dz : this.deklaracje) {
            tekstBloku += tab;
            tekstBloku += dz.wypisz(liczbaTabow + 1);
            tekstBloku += '\n';
        }

        for (Instrukcja i : this.instrukcje) {
            tekstBloku += tab;
            tekstBloku += i.wypisz(liczbaTabow + 1);
            tekstBloku += '\n';
        }

        tekstBloku += tab + "end blok";
        return tekstBloku;
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);

        for (Deklaracja dz : deklaracje) {
            odpluskwiacz.zwiekszLicznikInstrukcjiWProgramie();
            dz.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
        }

        for (Instrukcja i : instrukcje) {
            odpluskwiacz.zwiekszLicznikInstrukcjiWProgramie();
            i.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
        }

        stosZmiennych.pop();
        stosPoziomowZmiennych.pop();
        stosProcedur.pop();
        stosPoziomowProcedur.pop();
    }

    @Override
    protected void wykonaj(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur)
            throws NiepoprawnaWartoscZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);

        for (Deklaracja dz : deklaracje) {
            dz.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
        }

        for (Instrukcja i : instrukcje) {
            i.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
        }
        stosZmiennych.pop();
        stosPoziomowZmiennych.pop();
        stosProcedur.pop();
        stosPoziomowProcedur.pop();
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);

        for (Deklaracja dz : deklaracje) {
            odpluskwiacz.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, dz);
            odpluskwiacz.zmniejszLicznikInstrukcjiWProgramie();
            dz.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
        }


        for (Instrukcja instrukcja : instrukcje) {
            odpluskwiacz.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, instrukcja);
            odpluskwiacz.zmniejszLicznikInstrukcjiWProgramie();
            instrukcja.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
        }

        stosZmiennych.pop();
        stosPoziomowZmiennych.pop();
        stosProcedur.pop();
        stosPoziomowProcedur.pop();
    }

    // czesc wspolna dla trzech powyzszych komend
    private void czescWspolna(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmienych, Stack<Integer[]> stosPoziomowProcedur) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna {
        this.zmienneZBloku = new Zmienna['z' - 'a' + 1];
        this.proceduryZBloku = new Procedura['z' - 'a' + 1];
        this.poziomZmiennych = new Integer['z' - 'a' + 1];
        this.poziomProcedur = new Integer['z' - 'a' + 1];

        if (stosPoziomowZmienych.empty()) {
            Arrays.fill(this.poziomZmiennych, -1);
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
                    this.poziomZmiennych[i] = -1;
                }
                else {
                    this.poziomZmiennych[i] = poprzedniPoziom[i] + 1;
                    int wartosc = poprzednieZmienne[i].getWartosc();
                    zmienneZBloku[i].setWartosc(wartosc);
                }
            }
        }

        if (stosPoziomowProcedur.empty()) {
            Arrays.fill(this.poziomProcedur, -1);
            for (int i = 0; i < ('z' -'a' + 1); i++) {
                char nazwa = (char) ('a' + i);
                proceduryZBloku[i] = new Procedura(nazwa);
            }
        }
        else {
            Integer[] poprzedniPoziom = stosPoziomowProcedur.peek();
            Procedura[] poprzednieProcedury = stosProcedur.peek();

            for (int i = 0; i < ('z' - 'a' + 1); i++) {
                char nazwa = (char) ('a' + i);
                proceduryZBloku[i] = new Procedura(nazwa);
                if (poprzedniPoziom[i] == -1) {
                    this.poziomProcedur[i] = -1;
                }
                else {
                    this.poziomProcedur[i] = poprzedniPoziom[i] + 1;
                    proceduryZBloku[i] = poprzednieProcedury[i];
                }
            }
        }

        stosZmiennych.push(zmienneZBloku);
        stosPoziomowZmienych.push(poziomZmiennych);
        stosProcedur.push(proceduryZBloku);
        stosPoziomowProcedur.push(poziomProcedur);

        for (DeklaracjaZmiennej dz : ukryteDeklaracjeZmiennych) {
            dz.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmienych, stosPoziomowProcedur);
        }
    }
}
