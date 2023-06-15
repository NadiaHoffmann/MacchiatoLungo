package wyjatki;

public class NiepoprawnaNazwaProcedury extends Throwable {
    private char nazwa;
    public NiepoprawnaNazwaProcedury(char nazwa) {
        this.nazwa = nazwa;
    }
    public char getNazwa() {
        return nazwa;
    }
}
