package listy;

import wyrazenia.Wyrazenie;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaWyrazen {
    private ListaWyrazen() {
    }

    public static ArrayList<Wyrazenie> jakich(Wyrazenie... parametry) {
        return new ArrayList<>(Arrays.asList(parametry));
    }
}
