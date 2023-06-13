package wyjatki;

public class ZmiennaJuzZadeklarowana extends Throwable {
    private final char nazwa;
    public ZmiennaJuzZadeklarowana(char nazwa) {
        this.nazwa = nazwa;
    }

    public char getNazwa() {
        return nazwa;
    }
}
