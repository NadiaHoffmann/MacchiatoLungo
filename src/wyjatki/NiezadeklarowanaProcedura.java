package wyjatki;

public class NiezadeklarowanaProcedura extends Throwable {
    private char nazwa;
    public NiezadeklarowanaProcedura(String nazwa) {
    }

    public char getNazwa() {
        return nazwa;
    }
}
