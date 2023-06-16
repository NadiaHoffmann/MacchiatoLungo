package instrukcje;

import java.util.ArrayList;

public class Lista<T> {
    private ArrayList<T> lista;

    private Lista() {

    }

    public ArrayList czego(T... parametry) {
        for (T element : parametry) {
            lista.add(element);
        }

        return lista;
    }
}
