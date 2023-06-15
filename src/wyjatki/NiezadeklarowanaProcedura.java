package wyjatki;

public class NiezadeklarowanaProcedura extends Throwable {
    private char nazwa;
    public NiezadeklarowanaProcedura(char nazwa) {
    }

    public char getNazwa() {
        return nazwa;
    }
}
