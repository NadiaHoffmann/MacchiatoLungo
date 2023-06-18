package wyjatki;

public class ProceduraJuzZadeklarowana extends Throwable {
    private String nazwa;
    public ProceduraJuzZadeklarowana(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }
}
