package instrukcje;

import porownania.Porownanie;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;

public class BudowniczyProgramu implements BudowniczyInstrukcji {
    private final Blok glowny = new Blok();

    public BudowniczyProgramu() {}

    public BudowniczyProgramu zadeklarujZmienna(char nazwa, Wyrazenie wyrazenie) {
        glowny.dodajDeklaracje(new DeklaracjaZmiennej(nazwa, wyrazenie));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu zadeklarujProcedure(String nazwa, ArrayList<Zmienna> parametry, Blok blok) {
        glowny.dodajDeklaracje(new DeklaracjaProcedury(nazwa, parametry, blok.getDeklaracje(), blok.getInstrukcje()));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu wywolajProcedure(String nazwa, ArrayList<Wyrazenie> parametry) {
        glowny.dodajInstrukcje(new Procedura(nazwa, parametry));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu wywolajInstrukcjeIf(Porownanie porownanie, Blok instrukcjeIf) {
        glowny.dodajInstrukcje(new InstrukcjaIf(porownanie, instrukcjeIf.getInstrukcje()));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu wywolajInstrukcjeIfElse(Porownanie porownanie, Blok instrukcjeIf, Blok instrukcjeElse) {
        glowny.dodajInstrukcje(new InstrukcjaIf(porownanie, instrukcjeIf.getInstrukcje(), instrukcjeElse.getInstrukcje()));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu wypisz(Wyrazenie wyrazenie) {
        glowny.dodajInstrukcje(new InstrukcjaPrint(wyrazenie));
        glowny.zwiekszLiczbeInstrukcji();
        return this;
    }

    public BudowniczyProgramu wywolajPetleFor(char nazwa, Wyrazenie wyrazenie, Blok instrukcjeFor) {
        glowny.dodajInstrukcje(new PetlaFor(nazwa, wyrazenie, instrukcjeFor.getInstrukcje()));
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