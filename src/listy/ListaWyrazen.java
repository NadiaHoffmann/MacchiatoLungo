package listy;

import wyrazenia.Wyrazenie;

import java.util.ArrayList;

public class ListaWyrazen {
    private ListaWyrazen() {

    }

    public static ArrayList<Wyrazenie> jakich(Wyrazenie... parametry) {
        ArrayList<Wyrazenie> lista = new ArrayList<>();
        for (Wyrazenie element : parametry) {
            lista.add(element);
        }

        return lista;
    }
}
