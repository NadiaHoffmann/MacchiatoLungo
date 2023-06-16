package listy;

import wyrazenia.Wyrazenie;

import java.util.ArrayList;

public class ListaWyrazen {
    private ArrayList<Wyrazenie> lista;

    private ListaWyrazen() {

    }

    public static ArrayList<Wyrazenie> jakich(Wyrazenie... parametry) {
        for (Wyrazenie element : parametry) {
            lista.add(element);
        }

        return this.lista;
    }
}
