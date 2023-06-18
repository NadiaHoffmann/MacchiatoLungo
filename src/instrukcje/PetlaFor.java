package instrukcje;

import wyjatki.*;
import wyrazenia.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class PetlaFor extends Instrukcja {
    private final char nazwa;
    private int granica;
    private final Wyrazenie wyrazenie;
    private final ArrayList<Instrukcja> instrukcje;

    protected PetlaFor(char nazwa, Wyrazenie wyrazenie, ArrayList<Instrukcja> instrukcje) {
        this.nazwa = nazwa;
        this.wyrazenie = wyrazenie;
        this.instrukcje = instrukcje;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        StringBuilder tab = new StringBuilder();
        tab.append("\t".repeat(Math.max(0, liczbaTabow)));

        StringBuilder tekst = new StringBuilder(tab + "for (" + this.nazwa + " -> " + this.wyrazenie + ")\n");
        for (Instrukcja i : instrukcje) {
            tekst.append(tab);
            tekst.append(i.wypisz(liczbaTabow + 1));
            tekst.append('\n');
        }
        tekst = new StringBuilder(tekst.substring(0, tekst.length() - 1));
        return tekst.toString();
    }

    @Override
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych)
            throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura, NiepoprawnaLiczbaParametrow {
        int granica = wyrazenie.wartosc(stosZmiennych, stosPoziomowZmiennych);
        for (int i = 0; i < granica; i++) {
            ArrayList<Deklaracja> deklaracja = new ArrayList<>();
            ArrayList<DeklaracjaZmiennej> ukrytaDeklaracjaZmiennej = new ArrayList<>();
            DeklaracjaZmiennej zmiennaWPetli = new DeklaracjaZmiennej(nazwa, Literal.oWartosci(i));

            ukrytaDeklaracjaZmiennej.add(zmiennaWPetli);
            Blok blok = new Blok(deklaracja, ukrytaDeklaracjaZmiennej, instrukcje);
            blok.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
        }
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura, NiepoprawnaLiczbaParametrow {
        this.granica = wyrazenie.wartosc(stosZmiennych, stosPoziomowZmiennych);
        for (int i = 0; i < this.granica; i++) {
            ArrayList<Deklaracja> deklaracja = new ArrayList<>();
            ArrayList<DeklaracjaZmiennej> ukrytaDeklaracjaZmiennej = new ArrayList<>();
            DeklaracjaZmiennej zmiennaWPetli = new DeklaracjaZmiennej(nazwa, Literal.oWartosci(i));

            ukrytaDeklaracjaZmiennej.add(zmiennaWPetli);
            Blok blok = new Blok(deklaracja, ukrytaDeklaracjaZmiennej, instrukcje);
            blok.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, odpluskwiacz);
        }
    }


    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura, NiepoprawnaLiczbaParametrow {
        this.granica = wyrazenie.wartosc(stosZmiennych, stosPoziomowZmiennych);
        for (int i = 0; i < this.granica; i++) {
            ArrayList<Deklaracja> deklaracja = new ArrayList<>();
            ArrayList<DeklaracjaZmiennej> ukrytaDeklaracjaZmiennej = new ArrayList<>();
            DeklaracjaZmiennej zmiennaWPetli = new DeklaracjaZmiennej(nazwa, Literal.oWartosci(i));

            ukrytaDeklaracjaZmiennej.add(zmiennaWPetli);
            Blok blok = new Blok(deklaracja, ukrytaDeklaracjaZmiennej, instrukcje);
            blok.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, odpluskwiacz);
        }
    }
}
