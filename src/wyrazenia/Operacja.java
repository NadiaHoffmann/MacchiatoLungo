package wyrazenia;

public abstract class Operacja extends Wyrazenie {
    protected Wyrazenie arg1, arg2;

    public abstract int priorytet();

    public Operacja(Wyrazenie arg1, Wyrazenie arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }


    public String toString() {
        String arg1Str, arg2Str;
        if(priorytet() <= arg1.priorytet())
            arg1Str = arg1.toString();
        else
            arg1Str = "(" + arg1 + ")";

        if(priorytet() < arg2.priorytet())
            arg2Str = arg2.toString();
        else
            arg2Str = "(" + arg2 + ")";

        return arg1Str + nazwa() + arg2Str;
    }

    public abstract String nazwa();
}
