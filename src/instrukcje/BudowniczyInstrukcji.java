package instrukcje;

import porownania.Porownanie;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;

public interface BudowniczyInstrukcji {
    BudowniczyInstrukcji zadeklarujZmienna(char nazwa, Wyrazenie wyrazenie);

    BudowniczyInstrukcji zadeklarujProcedure(String nazwa, ArrayList<Zmienna> parametry, Blok blok);

    BudowniczyInstrukcji wywolajProcedure(String nazwa, ArrayList<Wyrazenie> parametry);

    BudowniczyInstrukcji wywolajInstrukcjeIf(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf);

    BudowniczyInstrukcji wywolajInstrukcjeIfElse(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf, ArrayList<Instrukcja> instrukcjeElse);

    BudowniczyInstrukcji wypisz(Wyrazenie wyrazenie);

    BudowniczyInstrukcji wywolajPetleFor(char nazwa, Wyrazenie wyrazenie, ArrayList<Instrukcja> instrukcje);

    BudowniczyInstrukcji przypiszWartoscZmiennej(char nazwa, Wyrazenie wyrazenie);

    BudowniczyInstrukcji dodajBlok(Blok blok);
}
