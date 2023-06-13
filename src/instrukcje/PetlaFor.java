package instrukcje;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyjatki.NiezadeklarowanaZmienna;
import wyjatki.ZmiennaJuzZadeklarowana;
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
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej {
        int granica = wyrazenie.wartosc(stosZmiennych, stosPoziomow);
        for (int i = 0; i < granica; i++) {
            ArrayList<DeklaracjaZmiennej> deklaracjaZmiennej = new ArrayList<>();
            ArrayList<DeklaracjaZmiennej> ukrytaDeklaracjaZmiennej = new ArrayList<>();
            DeklaracjaZmiennej zmiennaWPetli = new DeklaracjaZmiennej(nazwa, new Literal(i));

            ukrytaDeklaracjaZmiennej.add(zmiennaWPetli);
            Blok blok = new Blok(deklaracjaZmiennej, ukrytaDeklaracjaZmiennej, instrukcje);
            blok.wykonaj(stosZmiennych, stosPoziomow);
        }
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna {
        this.granica = wyrazenie.wartosc(stosZmiennych, stosPoziomow);
        for (int i = 0; i < this.granica; i++) {
            ArrayList<DeklaracjaZmiennej> deklaracjaZmiennej = new ArrayList<>();
            ArrayList<DeklaracjaZmiennej> ukrytaDeklaracjaZmiennej = new ArrayList<>();
            DeklaracjaZmiennej zmiennaWPetli = new DeklaracjaZmiennej(nazwa, new Literal(i));

            ukrytaDeklaracjaZmiennej.add(zmiennaWPetli);
            Blok blok = new Blok(deklaracjaZmiennej, ukrytaDeklaracjaZmiennej, instrukcje);
            blok.wykonajZOdpluskwiaczem(stosZmiennych, stosPoziomow, odpluskwiacz);
        }
    }


    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna {
        this.granica = wyrazenie.wartosc(stosZmiennych, stosPoziomow);
        for (int i = 0; i < this.granica; i++) {
            ArrayList<DeklaracjaZmiennej> deklaracjaZmiennej = new ArrayList<>();
            ArrayList<DeklaracjaZmiennej> ukrytaDeklaracjaZmiennej = new ArrayList<>();
            DeklaracjaZmiennej zmiennaWPetli = new DeklaracjaZmiennej(nazwa, new Literal(i));

            ukrytaDeklaracjaZmiennej.add(zmiennaWPetli);
            Blok blok = new Blok(deklaracjaZmiennej, ukrytaDeklaracjaZmiennej, instrukcje);
            blok.policzInstrukcjeWProgramie(stosZmiennych, stosPoziomow, odpluskwiacz);
        }
    }
}
