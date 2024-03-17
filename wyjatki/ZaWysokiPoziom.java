package wyjatki;

public class ZaWysokiPoziom extends Throwable {
    private final int licznik;
    public ZaWysokiPoziom(int licznik) {
        this.licznik = licznik;
    }

    public int getLicznik() {
        return licznik;
    }
}
