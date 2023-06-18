package wyjatki;

public class ProceduraJuzZadeklarowana extends Throwable {
    private char nazwa;
    public ProceduraJuzZadeklarowana(String nazwa) {
        this.nazwa = nazwa;
    }

    public char getNazwa() {
        return nazwa;
    }
}
