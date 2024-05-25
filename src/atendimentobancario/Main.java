package atendimentobancario;

import TADS.Fila;
import atendimentobancario.model.Cliente;
import atendimentobancario.model.Guiche;
import java.util.Random;

public class Main {
    private static Guiche[] guiches = {new Guiche(1), new Guiche(2), new Guiche(3)};
    private static Fila fila = fila = new Fila();
    private static int tempoExpediente = 21600;
    private static int numeroClientes = 0;
    
    public static int contadorSaque = 0;
    public static int contadorDeposito = 0;
    public static int contadorPagamento = 0;
    
    public static void main(String[] args) {
        
        while(tempoExpediente >= 0 && !fila.isEmpty()) {
            
            if(chegouCliente()) {
                Cliente cliente = new Cliente(++numeroClientes);
                fila.enqueue(cliente);
                Guiche guiche = guicheDesocupados();
                if(guiche != null) adicionarClienteGuiche(guiche);
            }
        }
    }
    
    public static Guiche guicheDesocupados() {
        for(Guiche guiche : guiches) {
            if(!guiche.estaOcupado()) return guiche;
        }
        return null;
    }
    
    public static void adicionarClienteGuiche(Guiche guiche) {
        int codigoTransacao = obterCodTransacao();
        guiche.adicionarTempoOcupacao(codigoTransacao);
    }
    
    public static boolean chegouCliente() {
        Random random = new Random();
        return random.nextInt(30) == 0;
    }
    
    public static int obterCodTransacao() {
        Random random = new Random();
        return random.nextInt(3);
    }
    
}
