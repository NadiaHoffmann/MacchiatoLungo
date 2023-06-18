package listy;

import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.ArrayList;

public class ListaZmiennych {
    private ListaZmiennych() {

    }

    public static ArrayList<Zmienna> jakich(Zmienna... parametry) {
        ArrayList<Zmienna> lista = new ArrayList<>();
        for (Zmienna element : parametry) {
            lista.add(element);
        }

        return lista;
    }
}
