package atendimentobancario;

import TADS.Fila;
import atendimentobancario.model.Cliente;
import atendimentobancario.model.Guiche;
import java.util.Random;
import javax.xml.transform.OutputKeys;

public class Main {
    private static Guiche[] arrayGuiches = {new Guiche(1), new Guiche(2), new Guiche(3)};
    private static Fila fila = fila = new Fila();
    private static int tempoExpediente = 21600;
    private static int numeroClientes = 0;
    
    public static void main(String[] args) throws InterruptedException {
        
        boolean temGuicheOcupado = false;
        boolean filaVazia = true;
        while(tempoExpediente >= 0 || !filaVazia || temGuicheOcupado) {
            int qtdGuichesOcupados = 0;
            filaVazia = fila.isEmpty();
            System.out.println("Tempo Restante Expediente: " + converterSegundos(tempoExpediente--));
            System.out.println("Quantidade de clientes na fila: " + fila.length);
            Guiche guicheDisponivel = null;        
            for(Guiche guiche : arrayGuiches) {                                
                if(guiche.estaOcupado()) {
                    System.out.println("Guiche " + guiche.getNumero() + " encerrará seu atendimento em " + guiche.tempoRestante() + " segundos.");
                    guiche.removerTempoOcupacao(1);
                    qtdGuichesOcupados++;
                    continue;
                }
                if(guicheDisponivel == null) {
                    guicheDisponivel = guiche;
                }
            }
            
            temGuicheOcupado = qtdGuichesOcupados > 0;
            
            if(tempoExpediente >= 0) {
                if(chegouCliente()) {
                    Cliente cliente = new Cliente(tempoExpediente);
                    System.out.println("Cliente entrou na fila as " + converterSegundos(tempoExpediente));
                    fila.enqueue(cliente);
                }
            } else {
                System.out.println("Chegou cliente após tempo de expediente encerrar. Cliente expulso");
            }
            
            if(guicheDisponivel != null && !filaVazia) {
                adicionarClienteGuiche(guicheDisponivel);
            }                
        }
        
        System.out.println("Expediente encerrado.");
    }
    
    public static void adicionarClienteGuiche(Guiche guiche) {
        int codigoTransacao = obterCodTransacao();
        guiche.adicionarTempoOcupacao(codigoTransacao);
        Cliente cliente = fila.dequeue();
        System.out.println("Cliente que entrou na fila as " + converterSegundos(cliente.getId()) + " começou o atendimento no Guiche " + guiche.getNumero());
    }
    
    public static boolean chegouCliente() {
        Random random = new Random();
        return random.nextInt(30) == 0;
    }
    
    public static int obterCodTransacao() {
        Random random = new Random();
        return random.nextInt(3);
    }
    
    public static String converterSegundos(int segundos) {
        int horas = segundos / 3600;
        int minutos = (segundos % 3600) / 60;
        int segundosRestantes = segundos % 60;
        
        if(segundos < 0) {
            horas = Math.abs(horas);
            minutos = Math.abs(minutos);
            segundosRestantes = Math.abs(segundosRestantes);
        }
        
        String tempoRestante = String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);

        return segundos < 0 ? "+" + tempoRestante : tempoRestante;
    }
    
    
}
