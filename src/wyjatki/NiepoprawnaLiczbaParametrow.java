package wyjatki;

public class NiepoprawnaLiczbaParametrow extends Throwable {
    private String nazwa;
    public NiepoprawnaLiczbaParametrow(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }
}
