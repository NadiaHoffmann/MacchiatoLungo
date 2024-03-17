package wyjatki;

public class NiezadeklarowanaProcedura extends Throwable {
    private String nazwa;
    public NiezadeklarowanaProcedura(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }
}
