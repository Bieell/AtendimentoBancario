package atendimentobancario.model;

import atendimentobancario.Main;

public class Guiche {
    private int numero;
    private int tempoRestanteOcupado;
    private int quantidadeSaques;
    private int quantidadePagamentos;
    private int quantidadeDepositos;

    public Guiche(int numero) {
        this.numero = numero;
        tempoRestanteOcupado = 0;
        quantidadeSaques = 0;
        quantidadeDepositos = 0;
    }
    
    public boolean estaOcupado() {
        return tempoRestanteOcupado > 0;
    }
    
    public int tempoRestante() {
        return tempoRestanteOcupado;
    }
    
    public void adicionarTempoOcupacao(int transacao) {
        switch(transacao) {
            case 0:
                System.out.println("Cliente vai sacar dinheiro. Tempo de Atendimento: 60 segundos" );
                tempoRestanteOcupado += 60;
                quantidadeSaques++;
                break;
            case 1:
                System.out.println("Cliente vai depositar dinheiro. Tempo de Atendimento: 90 segundos" );
                tempoRestanteOcupado += 90;
                quantidadeDepositos++;
                break;
            case 2:
                System.out.println("Cliente vai realizar pagamento. Tempo de Atendimento: 120 segundos" );
                tempoRestanteOcupado += 120;
                quantidadePagamentos++;
                break;
        }
    }
    
    public void removerTempoOcupacao(int tempo) {
        tempoRestanteOcupado--;
    }

    public int getNumero() {
        return numero;
    }
    
}
