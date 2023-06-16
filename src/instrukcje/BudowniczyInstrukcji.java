package instrukcje;

import porownania.Porownanie;
import wyrazenia.Wyrazenie;

public interface BudowniczyInstrukcji {
    public BudowniczyInstrukcji zadeklarujZmienna(char nazwa, Wyrazenie wyrazenie);

    public BudowniczyInstrukcji zadeklarujProcedure(char nazwa/*, ...*/);

    public BudowniczyInstrukcji wywolajProcedure(char nazwa/*, ...*/);

    public BudowniczyInstrukcji wywolajInstrukcjeIf(Porownanie porownanie/*, ...*/);

    public BudowniczyInstrukcji wywolajInstrukcjeIfElse(Porownanie porownanie/*, ..., ...*/);

    public BudowniczyInstrukcji wypisz(Wyrazenie wyrazenie);

    public BudowniczyInstrukcji wywolajPetleFor(char nazwa, Wyrazenie wyrazenie/*, ...*/);

    public BudowniczyInstrukcji przypiszWartoscZmiennej(char nazwa, Wyrazenie wyrazenie);
}
