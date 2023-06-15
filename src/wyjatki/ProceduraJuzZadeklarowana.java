package wyjatki;

public class ProceduraJuzZadeklarowana extends Throwable {
    private char nazwa;
    public ProceduraJuzZadeklarowana(char nazwa) {
        this.nazwa = nazwa;
    }

    public char getNazwa() {
        return nazwa;
    }
}
