package atendimentobancario.model;

public class Guiche {
    private int numero;
    private boolean ocupado;

    public Guiche(int numero) {
        this.numero = numero;
        this.ocupado = false;
    }
    
    public void ocupar() {
        ocupado = true;
    }
    
    public void desocupar() {
        ocupado = false;
    }
}
