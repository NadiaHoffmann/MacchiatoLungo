package instrukcje;

import porownania.Porownanie;
import wyrazenia.Wyrazenie;

import java.util.ArrayList;

public class BudowniczyBloku implements BudowniczyInstrukcji {
    private ArrayList<Instrukcja> instrukcje;
    private ArrayList<Deklaracja> deklaracje;

    public BudowniczyBloku() {}

    public BudowniczyBloku zadeklarujZmienna(char nazwa, Wyrazenie wyrazenie) {
        deklaracje.add(new DeklaracjaZmiennej(nazwa, wyrazenie));
        return this;
    }

    public BudowniczyBloku zadeklarujProcedure(char nazwa, ArrayList<Wyrazenie> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        deklaracje.add(new DeklaracjaProcedury(nazwa, parametry, deklaracje, instrukcje));
        return this;
    }

    public BudowniczyBloku wywolajProcedure(char nazwa, ArrayList<Wyrazenie> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        instrukcje.add(new Procedura(nazwa, parametry, deklaracje, instrukcje));
        return this;
    }

    public BudowniczyBloku wywolajInstrukcjeIf(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf) {
        instrukcje.add(new InstrukcjaIf(porownanie, instrukcjeIf));
        return this;
    }

    public BudowniczyBloku wywolajInstrukcjeIfElse(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf, ArrayList<Instrukcja> instrukcjeElse) {
        instrukcje.add(new InstrukcjaIf(porownanie, instrukcjeIf, instrukcjeElse));
        return this;
    }

    public BudowniczyBloku wypisz(Wyrazenie wyrazenie) {
        instrukcje.add(new InstrukcjaPrint(wyrazenie));
        return this;
    }

    public BudowniczyBloku wywolajPetleFor(char nazwa, Wyrazenie wyrazenie, ArrayList<Instrukcja> instrukcje) {
        instrukcje.add(new PetlaFor(nazwa, wyrazenie, instrukcje));
        return this;
    }

    public BudowniczyBloku przypiszWartoscZmiennej(char nazwa, Wyrazenie wyrazenie) {
        instrukcje.add(new PrzypisanieWartosci(nazwa, wyrazenie));
        return this;
    }

    public BudowniczyBloku dodajBlok(ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        instrukcje.add(new Blok(deklaracje, instrukcje));
        return this;
    }

    public Blok zbuduj() {
        return new Blok(this.deklaracje, this.instrukcje);
    }
}
