package wyjatki;

public class NiezadeklarowanaZmienna extends Throwable {
    private final char nazwa;

    public NiezadeklarowanaZmienna(char nazwa) {
        this.nazwa = nazwa;
    }

    public char getNazwa() {
        return nazwa;
    }
}
