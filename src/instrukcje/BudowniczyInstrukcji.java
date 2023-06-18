package instrukcje;

import porownania.Porownanie;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;

public interface BudowniczyInstrukcji {
    BudowniczyInstrukcji zadeklarujZmienna(char nazwa, Wyrazenie wyrazenie);

    BudowniczyInstrukcji zadeklarujProcedure(String nazwa, ArrayList<Zmienna> parametry, Blok blok);

    BudowniczyInstrukcji wywolajProcedure(String nazwa, ArrayList<Wyrazenie> parametry);

    BudowniczyInstrukcji wywolajInstrukcjeIf(Porownanie porownanie, Blok instrukcjeIf);

    BudowniczyInstrukcji wywolajInstrukcjeIfElse(Porownanie porownanie, Blok instrukcjeIf, Blok instrukcjeElse);

    BudowniczyInstrukcji wypisz(Wyrazenie wyrazenie);

    BudowniczyInstrukcji wywolajPetleFor(char nazwa, Wyrazenie wyrazenie, Blok instrukcjeFor);

    BudowniczyInstrukcji przypiszWartoscZmiennej(char nazwa, Wyrazenie wyrazenie);

    BudowniczyInstrukcji dodajBlok(Blok blok);
}
