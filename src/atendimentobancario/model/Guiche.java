package atendimentobancario.model;

import atendimentobancario.Main;

public class Guiche {
    private int numero;
    private boolean ocupado;
    private int tempoRestanteOcupado;

    public Guiche(int numero) {
        this.numero = numero;
        this.ocupado = false;
        this.tempoRestanteOcupado = 0;
    }
    
    public boolean estaOcupado() {
        return this.ocupado;
    }
    
    public void ocupar() {
        this.ocupado = true;
    }
    
    public void desocupar() {
        this.ocupado = false;
    }
    
    public int tempoRestante() {
        return this.tempoRestanteOcupado;
    }
    
    public void adicionarTempoOcupacao(int transacao) {
        ocupar();
        switch(transacao) {
            case 0:
                this.tempoRestanteOcupado += 60;
                Main.contadorSaque++;
                break;
            case 1:
                this.tempoRestanteOcupado += 90;
                Main.contadorDeposito++;
                break;
            case 2:
                this.tempoRestanteOcupado += 120;
                Main.contadorPagamento++;
                break;
        }
    }
    
}
