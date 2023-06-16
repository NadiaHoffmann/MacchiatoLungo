package wyrazenia;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyjatki.NiezadeklarowanaZmienna;

import java.util.Stack;

public class Modulo extends Operacja {
    protected Modulo(Wyrazenie arg1, Wyrazenie arg2) {
        super(arg1, arg2);
    }

    public static Modulo z(Wyrazenie arg1, Wyrazenie arg2) {
        return new Modulo(arg1, arg2);
    }

    @Override
    public int priorytet(){
        return 500;
    }

    @Override
    public int wartosc(Stack <Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        if (arg2.wartosc(stosZmiennych, stosPoziomow) == 0) {
            throw new NiepoprawnaWartoscZmiennej(new Modulo(this.arg1, this.arg2));
        }
        return arg1.wartosc(stosZmiennych, stosPoziomow) % arg2.wartosc(stosZmiennych, stosPoziomow);
    }

    @Override
    public String nazwa() {
        return "%";
    }
}
