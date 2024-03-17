package wyjatki;

public class NiepoprawnyPoziom extends Throwable{
    private final String liczba;
    public NiepoprawnyPoziom(String liczba) {
        this.liczba = liczba;
    }

    public String getLiczba() {
        return liczba;
    }
}
