package instrukcje;

import porownania.Porownanie;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;

public class BudowniczyProgramu implements BudowniczyInstrukcji {
    private Blok glowny = new Blok();

    public BudowniczyProgramu() {}

    public BudowniczyProgramu zadeklarujZmienna(char nazwa, Wyrazenie wyrazenie) {
        glowny.dodajDeklaracje(new DeklaracjaZmiennej(nazwa, wyrazenie));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu zadeklarujProcedure(String nazwa, ArrayList<Zmienna> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        for (Deklaracja d : /*blok.getDeklaracje()*/ deklaracje) {
            d.wypisz(0);
        }

        for (Instrukcja i : /*blok.getInstrukcje()*/ instrukcje) {
            i.wypisz(0);
        }
        glowny.dodajDeklaracje(new DeklaracjaProcedury(nazwa, parametry, /*blok.getDeklaracje(), blok.getInstrukcje()*/ deklaracje, instrukcje));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu zadeklarujProcedure(String nazwa, ArrayList<Zmienna> parametry, Blok blok) {
        for (Deklaracja d : blok.getDeklaracje()) {
            d.wypisz(0);
        }

        for (Instrukcja i : blok.getInstrukcje()) {
            i.wypisz(0);
        }
        glowny.dodajDeklaracje(new DeklaracjaProcedury(nazwa, parametry, blok.getDeklaracje(), blok.getInstrukcje()));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu wywolajProcedure(String nazwa, ArrayList<Wyrazenie> parametry) {
        glowny.dodajInstrukcje(new Procedura(nazwa, parametry));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu wywolajInstrukcjeIf(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf) {
        glowny.dodajInstrukcje(new InstrukcjaIf(porownanie, instrukcjeIf));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu wywolajInstrukcjeIfElse(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf, ArrayList<Instrukcja> instrukcjeElse) {
        glowny.dodajInstrukcje(new InstrukcjaIf(porownanie, instrukcjeIf, instrukcjeElse));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu wypisz(Wyrazenie wyrazenie) {
        glowny.dodajInstrukcje(new InstrukcjaPrint(wyrazenie));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu wywolajPetleFor(char nazwa, Wyrazenie wyrazenie, ArrayList<Instrukcja> instrukcje) {
        glowny.dodajInstrukcje(new PetlaFor(nazwa, wyrazenie, instrukcje));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu przypiszWartoscZmiennej(char nazwa, Wyrazenie wyrazenie) {
        glowny.dodajInstrukcje(new PrzypisanieWartosci(nazwa, wyrazenie));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu dodajBlok(Blok blok) {
        glowny.dodajInstrukcje(new Blok(blok.getDeklaracje(), blok.getInstrukcje()));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public Program zbuduj() {
        return new Program(this.glowny);
    }
}