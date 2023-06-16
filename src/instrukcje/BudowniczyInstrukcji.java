package instrukcje;

import porownania.Porownanie;
import wyrazenia.Wyrazenie;

import java.util.ArrayList;

public interface BudowniczyInstrukcji {
    public BudowniczyInstrukcji zadeklarujZmienna(char nazwa, Wyrazenie wyrazenie);

    public BudowniczyInstrukcji zadeklarujProcedure(char nazwa, ArrayList<Wyrazenie> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje);

    public BudowniczyInstrukcji wywolajProcedure(char nazwa, ArrayList<Wyrazenie> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje);

    public BudowniczyInstrukcji wywolajInstrukcjeIf(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf);

    public BudowniczyInstrukcji wywolajInstrukcjeIfElse(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf, ArrayList<Instrukcja> instrukcjeElse);

    public BudowniczyInstrukcji wypisz(Wyrazenie wyrazenie);

    public BudowniczyInstrukcji wywolajPetleFor(char nazwa, Wyrazenie wyrazenie, ArrayList<Instrukcja> instrukcje);

    public BudowniczyInstrukcji przypiszWartoscZmiennej(char nazwa, Wyrazenie wyrazenie);
}
