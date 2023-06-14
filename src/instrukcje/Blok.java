package instrukcje;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiezadeklarowanaZmienna;
import wyjatki.ZmiennaJuzZadeklarowana;
import wyjatki.NiepoprawnaWartoscZmiennej;
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
    private ArrayList<DeklaracjaZmiennej> deklaracjeZmiennych;
    private ArrayList<DeklaracjaZmiennej> ukryteDeklaracjeZmiennych;
    protected int licznikInstrukcji;

    // konstruktor dla bloku w petli for
    protected Blok(ArrayList<DeklaracjaZmiennej> deklaracjeZmiennych, ArrayList<DeklaracjaZmiennej> ukryteDeklaracjeZmiennych, ArrayList<Instrukcja> instrukcje) {
        this.deklaracjeZmiennych = deklaracjeZmiennych == null ? new ArrayList<>() : deklaracjeZmiennych;
        this.ukryteDeklaracjeZmiennych = ukryteDeklaracjeZmiennych == null ? new ArrayList<>() : ukryteDeklaracjeZmiennych;
        this.instrukcje = instrukcje;
        this.licznikInstrukcji = instrukcje.size();
    }

    public Blok(ArrayList<DeklaracjaZmiennej> deklaracjeZmiennych, ArrayList<Instrukcja> instrukcje) {
        this.deklaracjeZmiennych = deklaracjeZmiennych == null ? new ArrayList<>() : deklaracjeZmiennych;
        this.ukryteDeklaracjeZmiennych = new ArrayList<>();
        this.instrukcje = instrukcje;
        this.licznikInstrukcji = instrukcje.size();
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        String tab = "";
        for (int i = 0; i < liczbaTabow; i++) {
            tab += '\t';
        }
        String tekstBloku = tab + "begin blok\n";
        for (DeklaracjaZmiennej dz : this.deklaracjeZmiennych) {
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
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack <Integer[]> stosPoziomow, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomow);

        for (DeklaracjaZmiennej dz : deklaracjeZmiennych) {
            odpluskwiacz.zwiekszLicznikInstrukcjiWProgramie();
            dz.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomow, odpluskwiacz);
        }

        for (Instrukcja i : instrukcje) {
            odpluskwiacz.zwiekszLicznikInstrukcjiWProgramie();
            i.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomow, odpluskwiacz);
        }

        stosZmiennych.pop();
        stosPoziomow.pop();
    }

    @Override
    protected void wykonaj(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomow);

        for (DeklaracjaZmiennej dz : deklaracjeZmiennych) {
            dz.wykonaj(stosZmiennych, stosProcedur, stosPoziomow);
        }

        for (Instrukcja i : instrukcje) {
            i.wykonaj(stosZmiennych, stosProcedur, stosPoziomow);
        }
        stosZmiennych.pop();
        stosPoziomow.pop();
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomow, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomow);

        for (DeklaracjaZmiennej dz : deklaracjeZmiennych) {
            odpluskwiacz.puscOdpluskwiacz(stosZmiennych, stosPoziomow, dz);
            odpluskwiacz.zmniejszLicznikInstrukcjiWProgramie();
            dz.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomow, odpluskwiacz);
        }


        for (Instrukcja instrukcja : instrukcje) {
            odpluskwiacz.puscOdpluskwiacz(stosZmiennych, stosPoziomow, instrukcja);
            odpluskwiacz.zmniejszLicznikInstrukcjiWProgramie();
            instrukcja.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomow, odpluskwiacz);
        }

        stosZmiennych.pop();
        stosPoziomow.pop();
    }

    // czesc wspolna dla trzech powyzszych komend
    private void czescWspolna(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna {
        this.zmienneZBloku = new Zmienna['z' - 'a' + 1];
        this.proceduryZBloku = new Procedura['z' - 'a' + 1];
        this.poziomZmiennych = new Integer['z' - 'a' + 1];

        if (stosPoziomow.empty()) {
            Arrays.fill(this.poziomZmiennych, -1);
            for (int i = 0; i < ('z' -'a' + 1); i++) {
                char nazwa = (char) ('a' + i);
                zmienneZBloku[i] = new Zmienna(nazwa);
            }
            for (int i = 0; i < ('z' -'a' + 1); i++) {
                char nazwa = (char) ('a' + i);
                proceduryZBloku[i] = new Procedura(nazwa);
            }
        }
        else {
            Integer[] poprzedniPoziom = stosPoziomow.peek();
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

        stosZmiennych.push(zmienneZBloku);
        stosPoziomow.push(poziomZmiennych);

        for (DeklaracjaZmiennej dz : ukryteDeklaracjeZmiennych) {
            dz.wykonaj(stosZmiennych, stosProcedur, stosPoziomow);
        }
    }
}
