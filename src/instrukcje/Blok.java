package instrukcje;

import wyjatki.*;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Blok extends Instrukcja {
    private Zmienna[] zmienneZBloku;
    private Integer[] poziomZmiennych;
    private HashMap<String, Procedura> proceduryZBloku;

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

    protected Blok(){
        this.deklaracje = new ArrayList<Deklaracja>();
        this.ukryteDeklaracjeZmiennych = new ArrayList<>();
        this.instrukcje = new ArrayList<>();
        this.licznikInstrukcji = 0;
    };

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
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);

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
        stosPoziomowProcedur.pop();
    }

    @Override
    protected void wykonaj(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych)
            throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);

        for (Deklaracja dz : deklaracje) {
            dz.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
        }

        for (Instrukcja i : instrukcje) {
            i.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
        }
        stosZmiennych.pop();
        stosPoziomowZmiennych.pop();
        stosProcedur.pop();
        stosPoziomowProcedur.pop();
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        this.czescWspolna(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);

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
        stosPoziomowProcedur.pop();
    }

    // czesc wspolna dla trzech powyzszych komend
    private void czescWspolna(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmienych, Stack<Integer[]> stosPoziomowProcedur) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna {
        this.zmienneZBloku = new Zmienna['z' - 'a' + 1];
        this.proceduryZBloku = new HashMap<String, Procedura>(10);
        this.poziomZmiennych = new Integer['z' - 'a' + 1];

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

        if (stosProcedur.empty()) {
            stosProcedur.push(new HashMap<String,Procedura>(10));
        }
        else {
            HashMap<String, Procedura> poprzednieProcedury = stosProcedur.peek();
            if (poprzednieProcedury != null) {
                this.proceduryZBloku.putAll(poprzednieProcedury);
            }
            else {
                this.proceduryZBloku = new HashMap<String, Procedura>(10);
            }
        }

        stosZmiennych.push(zmienneZBloku);
        stosPoziomowZmienych.push(poziomZmiennych);
        stosProcedur.push(proceduryZBloku);

        for (DeklaracjaZmiennej dz : ukryteDeklaracjeZmiennych) {
            try {
                dz.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmienych);
            } catch (ProceduraJuzZadeklarowana proceduraJuzZadeklarowana) {
                throw new RuntimeException(proceduraJuzZadeklarowana);
            } catch (NiezadeklarowanaProcedura niezadeklarowanaProcedura) {
                throw new RuntimeException(niezadeklarowanaProcedura);
            }
        }
    }
}
