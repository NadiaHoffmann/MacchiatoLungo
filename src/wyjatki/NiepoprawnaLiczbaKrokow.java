package wyjatki;

public class NiepoprawnaLiczbaKrokow extends Throwable{
    private final String liczba;
    public NiepoprawnaLiczbaKrokow(String liczba) {
        this.liczba = liczba;
    }

    public String getLiczba() {
        return liczba;
    }
}
