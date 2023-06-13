package wyrazenia;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyjatki.NiezadeklarowanaZmienna;

import java.util.Stack;

public class Mnozenie extends Operacja {
    @Override
    public int priorytet() {
        return 500;
    }

    public Mnozenie(Wyrazenie arg1, Wyrazenie arg2) {
        super(arg1, arg2);
    }

    @Override
    public int wartosc(Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        return arg1.wartosc(stosZmiennych, stosPoziomow) * arg2.wartosc(stosZmiennych, stosPoziomow);
    }

    @Override
    public String nazwa() {
        return "*";
    }
}
