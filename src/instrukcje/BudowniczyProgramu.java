package instrukcje;

import porownania.Porownanie;
import wyrazenia.Wyrazenie;

import java.util.ArrayList;

public class BudowniczyProgramu implements BudowniczyInstrukcji {
    private Blok glowny;

    public BudowniczyProgramu() {}

    public BudowniczyProgramu zadeklarujZmienna(char nazwa, Wyrazenie wyrazenie) {
        glowny.dodajDeklaracje(new DeklaracjaZmiennej(nazwa, wyrazenie));
        return this;
    }

    public BudowniczyProgramu zadeklarujProcedure(char nazwa, ArrayList<Wyrazenie> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        glowny.dodajDeklaracje(new DeklaracjaProcedury(nazwa, parametry, deklaracje, instrukcje));
        return this;
    }

    public BudowniczyProgramu wywolajProcedure(char nazwa, ArrayList<Wyrazenie> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        glowny.dodajInstrukcje(new Procedura(nazwa, parametry, deklaracje, instrukcje));
        return this;
    }

    public BudowniczyProgramu wywolajInstrukcjeIf(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf) {
        glowny.dodajInstrukcje(new InstrukcjaIf(porownanie, instrukcjeIf));
        return this;
    }

    public BudowniczyProgramu wywolajInstrukcjeIfElse(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf, ArrayList<Instrukcja> instrukcjeElse) {
        glowny.dodajInstrukcje(new InstrukcjaIf(porownanie, instrukcjeIf, instrukcjeElse));
        return this;
    }

    public BudowniczyProgramu wypisz(Wyrazenie wyrazenie) {
        glowny.dodajInstrukcje(new InstrukcjaPrint(wyrazenie));
        return this;
    }

    public BudowniczyProgramu wywolajPetleFor(char nazwa, Wyrazenie wyrazenie, ArrayList<Instrukcja> instrukcje) {
        glowny.dodajInstrukcje(new PetlaFor(nazwa, wyrazenie, instrukcje));
        return this;
    }

    public BudowniczyProgramu przypiszWartoscZmiennej(char nazwa, Wyrazenie wyrazenie) {
        glowny.dodajInstrukcje(new PrzypisanieWartosci(nazwa, wyrazenie));
        return this;
    }

    public BudowniczyProgramu dodajBlok(ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        glowny.dodajInstrukcje(new Blok(deklaracje, instrukcje));
        return this;
    }

    public Program zbuduj() {
        return new Program(this.glowny);
    }
}