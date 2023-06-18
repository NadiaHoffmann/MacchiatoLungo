package instrukcje;

import porownania.Porownanie;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;

public class BudowniczyBloku implements BudowniczyInstrukcji {
    private final ArrayList<Instrukcja> instrukcje = new ArrayList<>();
    private final ArrayList<Deklaracja> deklaracje = new ArrayList<>();

    public BudowniczyBloku() {}

    public BudowniczyBloku zadeklarujZmienna(char nazwa, Wyrazenie wyrazenie) {
        deklaracje.add(new DeklaracjaZmiennej(nazwa, wyrazenie));
        return this;
    }

    public BudowniczyBloku zadeklarujProcedure(String nazwa, ArrayList<Zmienna> parametry, Blok blok) {
        deklaracje.add(new DeklaracjaProcedury(nazwa, parametry, blok.getDeklaracje(), blok.getInstrukcje()));
        return this;
    }

    public BudowniczyBloku wywolajProcedure(String nazwa, ArrayList<Wyrazenie> parametry) {
        instrukcje.add(new Procedura(nazwa, parametry));
        return this;
    }

    public BudowniczyBloku wywolajInstrukcjeIf(Porownanie porownanie, Blok instrukcjeIf) {
        instrukcje.add(new InstrukcjaIf(porownanie, instrukcjeIf.getInstrukcje()));
        return this;
    }

    public BudowniczyBloku wywolajInstrukcjeIfElse(Porownanie porownanie, Blok instrukcjeIf, Blok instrukcjeElse) {
        instrukcje.add(new InstrukcjaIf(porownanie, instrukcjeIf.getInstrukcje(), instrukcjeElse.getInstrukcje()));
        return this;
    }

    public BudowniczyBloku wypisz(Wyrazenie wyrazenie) {
        instrukcje.add(new InstrukcjaPrint(wyrazenie));
        return this;
    }

    public BudowniczyBloku wywolajPetleFor(char nazwa, Wyrazenie wyrazenie, Blok instrukcjeFor) {
        instrukcje.add(new PetlaFor(nazwa, wyrazenie, instrukcjeFor.getInstrukcje()));
        return this;
    }

    public BudowniczyBloku przypiszWartoscZmiennej(char nazwa, Wyrazenie wyrazenie) {
        instrukcje.add(new PrzypisanieWartosci(nazwa, wyrazenie));
        return this;
    }

    public BudowniczyBloku dodajBlok(Blok blok) {
        instrukcje.add(new Blok(blok.getDeklaracje(), blok.getInstrukcje()));
        return this;
    }

    public Blok zbuduj() {
        return new Blok(this.deklaracje, this.instrukcje);
    }
}
