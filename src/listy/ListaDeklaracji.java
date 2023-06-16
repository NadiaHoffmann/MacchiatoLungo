package listy;

import instrukcje.Deklaracja;
import wyrazenia.Wyrazenie;

import java.util.ArrayList;

public class ListaDeklaracji {
    private ArrayList<Deklaracja> lista;

    private ListaDeklaracji() {

    }

    public ArrayList jakich(Deklaracja... parametry) {
        for (Deklaracja element : parametry) {
            lista.add(element);
        }

        return this.lista;
    }
}
