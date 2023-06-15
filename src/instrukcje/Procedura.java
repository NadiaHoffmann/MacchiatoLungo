package instrukcje;

import wyjatki.*;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Procedura extends Instrukcja implements ModyfikacjaZmiennych {
    private char nazwa;
    private ArrayList<Wyrazenie> parametry;
    private ArrayList<Deklaracja> deklaracje;
    private ArrayList<Instrukcja> instrukcje;

    private Zmienna[] zmienneZProcedury;
    private Integer[] poziomZmiennych;
    private Procedura[] proceduryZProcedury;
    private Integer[] poziomProcedur;

    public Procedura(char nazwa) {
        this.nazwa = nazwa;
    }

    protected Procedura(char nazwa, ArrayList<Wyrazenie> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        this.nazwa = nazwa;
        this.parametry = parametry;
        this.deklaracje = deklaracje;
        this.instrukcje = instrukcje;
    }

    public Procedura(char nazwa, ArrayList<Wyrazenie> parametry) {
        this.nazwa = nazwa;
        this.parametry = parametry;
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
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur)
            throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej {

    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana {

    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna {

    }

    @Override
    public Zmienna getWartoscZmiennej(char nazwa, Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomowZmiennych) throws NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        return ModyfikacjaZmiennych.super.getWartoscZmiennej(nazwa, stosZmiennych, stosPoziomowZmiennych);
    }

    // czesc wspolna dla trzech powyzszych komend
    private void czescWspolna(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmienych, Stack<Integer[]> stosPoziomowProcedur)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, NiezadeklarowanaProcedura {
        this.zmienneZProcedury = new Zmienna['z' - 'a' + 1];
        this.proceduryZProcedury = new Procedura['z' - 'a' + 1];
        this.poziomZmiennych = new Integer['z' - 'a' + 1];
        this.poziomProcedur = new Integer['z' - 'a' + 1];

        if (stosPoziomowProcedur.empty()) {
            throw new NiezadeklarowanaProcedura(nazwa);
        }
        else {
            Integer[] poprzedniPoziom = stosPoziomowProcedur.peek();
            Procedura[] poprzednieProcedury = stosProcedur.peek();

            if (poprzedniPoziom[nazwa] == -1) {
                throw new NiezadeklarowanaProcedura(nazwa);
            }
            else {
                stosProcedur.push(poprzednieProcedury);
                this.deklaracje = stosProcedur.elementAt(stosProcedur.size() - 1 - poprzedniPoziom[nazwa])[nazwa].deklaracje;
                this.instrukcje = stosProcedur.elementAt(stosProcedur.size() - 1 - poprzedniPoziom[nazwa])[nazwa].instrukcje;
                stosProcedur.pop();
            }

            for (int i = 0; i < ('z' - 'a' + 1); i++) {
                char nazwa = (char) ('a' + i);
                if (poprzedniPoziom[i] == -1) {
                    this.poziomProcedur[i] = -1;
                    proceduryZProcedury[i] = new Procedura(nazwa);
                }
                else {
                    this.poziomProcedur[i] = poprzedniPoziom[i] + 1;
                    proceduryZProcedury[i] = poprzednieProcedury[i];
                }
            }
        }

        if (stosPoziomowZmienych.empty()) {
            Arrays.fill(this.poziomZmiennych, -1);
            for (int i = 0; i < ('z' -'a' + 1); i++) {
                char nazwa = (char) ('a' + i);
                zmienneZProcedury[i] = new Zmienna(nazwa);
            }
        }
        else {
            Integer[] poprzedniPoziom = stosPoziomowZmienych.peek();
            Zmienna[] poprzednieZmienne = stosZmiennych.peek();

            for (int i = 0; i < ('z' - 'a' + 1); i++) {
                char nazwa = (char) ('a' + i);
                zmienneZProcedury[i] = new Zmienna(nazwa);
                if (poprzedniPoziom[i] == -1) {
                    this.poziomZmiennych[i] = -1;
                }
                else {
                    this.poziomZmiennych[i] = poprzedniPoziom[i] + 1;
                    int wartosc = poprzednieZmienne[i].getWartosc();
                    zmienneZProcedury[i].setWartosc(wartosc);
                }
            }
        }

        stosZmiennych.push(zmienneZProcedury);
        stosPoziomowZmienych.push(poziomZmiennych);
        stosProcedur.push(proceduryZProcedury);
        stosPoziomowProcedur.push(poziomProcedur);

    }
}

