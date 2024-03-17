package wyjatki;

public class NiepoprawnaNazwaZmiennej extends Throwable {
    private final char nazwa;

    public NiepoprawnaNazwaZmiennej(char nazwa) {
        this.nazwa = nazwa;
    }

    public char getNazwa() {
        return nazwa;
    }
}
