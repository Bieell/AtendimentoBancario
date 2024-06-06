package atendimentobancario;

import TADS.Fila;
import atendimentobancario.model.Cliente;
import atendimentobancario.model.Guiche;
import java.util.Random;

public class Main {
    private static final Guiche[] ARRAY_GUICHES = {new Guiche(1), new Guiche(2), new Guiche(3)};
    private static final int TEMPO_FINAL_EXPEDIENTE = 21600;
    private static final Fila fila = new Fila();
    private static int tempoExpedienteAtual = 0;
    private static int numeroClientes = 0;
    private static int tempoEsperaTotal = 0;
    private static int totalDePagamentos = 0;
    private static int totalDeSaques = 0;
    private static int totalDeDepositos = 0;
    private static int tempoExtraExpediente = 0;
    
    public static void main(String[] args) throws InterruptedException {
        
        boolean temGuicheOcupado = false;
        boolean filaVazia = true;
        while(tempoExpedienteAtual <= TEMPO_FINAL_EXPEDIENTE || !filaVazia || temGuicheOcupado) {
            int qtdGuichesOcupados = 0;
            filaVazia = fila.isEmpty();
            System.out.println("\nTempo de Expediente: " + horarioExpediente(tempoExpedienteAtual++));
            System.out.println("Quantidade de clientes na fila: " + fila.length);
            Guiche guicheDisponivel = null;        
            
            for(Guiche guiche : ARRAY_GUICHES) {                                
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
            boolean chegouCliente = checarClientes();
            
            if(tempoExpedienteAtual <= TEMPO_FINAL_EXPEDIENTE) {
                if(chegouCliente) {
                    Cliente cliente = new Cliente(tempoExpedienteAtual);
                    System.out.println("Cliente entrou na fila as " + horarioExpediente(tempoExpedienteAtual));
                    fila.enqueue(cliente);
                    numeroClientes++;
                }
            } else {
                if(chegouCliente)
                    System.out.println("Chegou cliente após tempo de expediente encerrar. Cliente não atendido.");
            }
            
            if(guicheDisponivel != null && !filaVazia) {
                adicionarClienteGuiche(guicheDisponivel);
            }
            
            if(tempoExpedienteAtual > TEMPO_FINAL_EXPEDIENTE) tempoExtraExpediente++;
            Thread.sleep(2);
        }
        
        for(Guiche guiche : ARRAY_GUICHES) {
            totalDeDepositos += guiche.getQuantidadeDepositos();
            totalDePagamentos += guiche.getQuantidadePagamentos();
            totalDeSaques += guiche.getQuantidadeSaques();
        }
        
        double tempoDeEsperaMedio = tempoEsperaTotal / numeroClientes;
        
        System.out.println("\n================ Expediente encerrado ================\n");
        System.out.println("Número de Clientes atendidos: " + numeroClientes);
        System.out.println("Tempo de espera médio: " + tempoDeEsperaMedio);
        System.out.println("Total de Saques: " + totalDeSaques);
        System.out.println("Total de Depósitos: " + totalDeDepositos);
        System.out.println("Total de Pagamentos: " + totalDePagamentos);
        System.out.println("Tempo extra do expediente: " + tempoExtraExpediente);
        System.out.println("\n================ FIM ================\n");
    }
    
    public static void adicionarClienteGuiche(Guiche guiche) {
        int codigoTransacao = obterCodTransacao();
        Cliente cliente = fila.dequeue();
        int tempoEsperaCliente = tempoExpedienteAtual - cliente.getHorarioEntrada();
        tempoEsperaTotal += tempoEsperaCliente;
        System.out.println("Cliente que entrou na fila as " + horarioExpediente(cliente.getHorarioEntrada()) + " iniciará o atendimento no Guiche " + guiche.getNumero() + ". Tempo de Espera: " + tempoEsperaCliente);
        guiche.adicionarTempoOcupacao(codigoTransacao);
    }
    
    public static boolean checarClientes() {
        Random random = new Random();
        return random.nextInt(30) == 0;
    }
    
    public static int obterCodTransacao() {
        Random random = new Random();
        return random.nextInt(3);
    }
    
    public static String horarioExpediente(int segundos) {
        String tempoRestante = converterSegundos(segundos);
        return segundos > TEMPO_FINAL_EXPEDIENTE ? "+" + tempoRestante : tempoRestante;
    }
    
    public static String converterSegundos(int segundos) {
        int horas = segundos / 3600;
        int minutos = (segundos % 3600) / 60;
        int segundosRestantes = segundos % 60;
        
        String tempoRestante = String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);

        return tempoRestante;
    }
}
