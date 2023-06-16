package instrukcje;

import wyjatki.*;
import wyrazenia.*;

import java.util.ArrayList;
import java.util.Stack;

public class PetlaFor extends Instrukcja {
    private final char nazwa;
    private int granica;
    private Wyrazenie wyrazenie;
    private ArrayList<Instrukcja> instrukcje;

    public PetlaFor(char nazwa, Wyrazenie wyrazenie, ArrayList<Instrukcja> instrukcje) {
        this.nazwa = nazwa;
        this.wyrazenie = wyrazenie;
        this.instrukcje = instrukcje;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        String tab = "";
        for (int i = 0; i < liczbaTabow; i++) {
            tab += '\t';
        }

        String tekst = tab + "for (" + this.nazwa + " -> " + this.wyrazenie + ")\n"/*[" + this.wyrazenie + " = " + + "]\n"*/;
        for (Instrukcja i : instrukcje) {
            tekst += tab;
            tekst += i.wypisz(liczbaTabow + 1);
            tekst += '\n';
        }
        tekst = tekst.substring(0, tekst.length() - 1);
        return tekst;
    }

    @Override
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur)
            throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        int granica = wyrazenie.wartosc(stosZmiennych, stosPoziomowZmiennych);
        for (int i = 0; i < granica; i++) {
            ArrayList<Deklaracja> deklaracja = new ArrayList<>();
            ArrayList<DeklaracjaZmiennej> ukrytaDeklaracjaZmiennej = new ArrayList<>();
            DeklaracjaZmiennej zmiennaWPetli = new DeklaracjaZmiennej(nazwa, Literal.oWartosci(i));

            ukrytaDeklaracjaZmiennej.add(zmiennaWPetli);
            Blok blok = new Blok(deklaracja, ukrytaDeklaracjaZmiennej, instrukcje);
            blok.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
        }
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        this.granica = wyrazenie.wartosc(stosZmiennych, stosPoziomowZmiennych);
        for (int i = 0; i < this.granica; i++) {
            ArrayList<Deklaracja> deklaracja = new ArrayList<>();
            ArrayList<DeklaracjaZmiennej> ukrytaDeklaracjaZmiennej = new ArrayList<>();
            DeklaracjaZmiennej zmiennaWPetli = new DeklaracjaZmiennej(nazwa, Literal.oWartosci(i));

            ukrytaDeklaracjaZmiennej.add(zmiennaWPetli);
            Blok blok = new Blok(deklaracja, ukrytaDeklaracjaZmiennej, instrukcje);
            blok.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
        }
    }


    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        this.granica = wyrazenie.wartosc(stosZmiennych, stosPoziomowZmiennych);
        for (int i = 0; i < this.granica; i++) {
            ArrayList<Deklaracja> deklaracja = new ArrayList<>();
            ArrayList<DeklaracjaZmiennej> ukrytaDeklaracjaZmiennej = new ArrayList<>();
            DeklaracjaZmiennej zmiennaWPetli = new DeklaracjaZmiennej(nazwa, Literal.oWartosci(i));

            ukrytaDeklaracjaZmiennej.add(zmiennaWPetli);
            Blok blok = new Blok(deklaracja, ukrytaDeklaracjaZmiennej, instrukcje);
            blok.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
        }
    }
}
