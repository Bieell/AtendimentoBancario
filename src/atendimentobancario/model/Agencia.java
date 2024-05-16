package atendimentobancario.model;

public class Agencia {
    private final Guiche guiche1;
    private final Guiche guiche2;
    private final Guiche guiche3;
    
    public Agencia() {
        guiche1 = new Guiche(1);
        guiche2 = new Guiche(2);
        guiche3 = new Guiche(3);
    }
}
