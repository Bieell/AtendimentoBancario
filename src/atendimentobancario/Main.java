package atendimentobancario;

import TADS.Fila;
import atendimentobancario.model.Cliente;
import atendimentobancario.model.Guiche;
import java.util.Random;

public class Main {
    // Definindo os guichês (caixas de atendimento)
    private static final Guiche[] ARRAY_GUICHES = {new Guiche(1), new Guiche(2), new Guiche(3)};
    // Definindo o tempo final do expediente em segundos (6 horas)
    private static final int TEMPO_FINAL_EXPEDIENTE = 21600;
    // Inicializando a fila de clientes
    private static final Fila fila = new Fila();
    // Variáveis para rastrear o tempo atual do expediente, número de clientes, etc.
    private static int tempoExpedienteAtual = 0;
    private static int numeroClientes = 0;
    private static int tempoEsperaTotal = 0;
    private static int totalDePagamentos = 0;
    private static int totalDeSaques = 0;
    private static int totalDeDepositos = 0;
    private static int tempoExtraExpediente = 0;
    
    public static void main(String[] args) throws InterruptedException {
        // Flags para verificar se há guichês ocupados e se a fila está vazia
        boolean temGuicheOcupado = false;
        boolean filaVazia = true;

        // Loop principal do expediente bancário
        while(tempoExpedienteAtual <= TEMPO_FINAL_EXPEDIENTE || !filaVazia || temGuicheOcupado) {
            int qtdGuichesOcupados = 0;
            filaVazia = fila.isEmpty();

            // Mostra o tempo atual do expediente e a quantidade de clientes na fila
            System.out.println("\nTempo de Expediente: " + horarioExpediente(tempoExpedienteAtual));
            System.out.println("Quantidade de clientes na fila: " + fila.length);

            // Variável para armazenar um guichê disponível
            Guiche guicheDisponivel = null;        
            
            // Itera sobre os guichês para verificar o status de ocupação
            for(Guiche guiche : ARRAY_GUICHES) {                                
                if(guiche.estaOcupado()) {
                    // Se o guichê está ocupado, atualiza o tempo restante de ocupação
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
                    // Se um novo cliente chega, cria e adiciona à fila
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
                // Se há um guichê disponível e a fila não está vazia, aloca um cliente ao guichê
                adicionarClienteGuiche(guicheDisponivel);
            }
            
            if(tempoExpedienteAtual > TEMPO_FINAL_EXPEDIENTE) tempoExtraExpediente++;
            
            // Incrementa o tempo do expediente
            tempoExpedienteAtual++;
        }
        
        // Após o expediente, soma as transações realizadas em todos os guichês
        for(Guiche guiche : ARRAY_GUICHES) {
            totalDeDepositos += guiche.getQuantidadeDepositos();
            totalDePagamentos += guiche.getQuantidadePagamentos();
            totalDeSaques += guiche.getQuantidadeSaques();
        }
        
        // Calcula o tempo médio de espera dos clientes
        double tempoDeEsperaMedio = tempoEsperaTotal / numeroClientes;
        
        // Exibe o resumo do expediente
        System.out.println("\n================ Expediente encerrado ================\n");
        System.out.println("Número de Clientes atendidos: " + numeroClientes);
        System.out.println("Tempo de espera médio: " + converterSegundos(tempoDeEsperaMedio));
        System.out.println("Total de Saques: " + totalDeSaques);
        System.out.println("Total de Depósitos: " + totalDeDepositos);
        System.out.println("Total de Pagamentos: " + totalDePagamentos);
        System.out.println("Tempo extra do expediente: " + converterSegundos(tempoExtraExpediente));
        System.out.println("\n================ FIM ================\n");
    }
    
    // Função para alocar um cliente a um guichê
    public static void adicionarClienteGuiche(Guiche guiche) {
        int codigoTransacao = obterCodTransacao();
        Cliente cliente = fila.dequeue();
        int tempoEsperaCliente = tempoExpedienteAtual - cliente.getHorarioEntrada();
        tempoEsperaTotal += tempoEsperaCliente;
        System.out.println("Cliente que entrou na fila as " + horarioExpediente(cliente.getHorarioEntrada()) + " iniciará o atendimento no Guiche " + guiche.getNumero() + ". Tempo de Espera: " + tempoEsperaCliente);
        guiche.adicionarTempoOcupacao(codigoTransacao);
    }
    
    // Função para verificar se um novo cliente chegou (1 em 30 chances)
    public static boolean checarClientes() {
        Random random = new Random();
        return random.nextInt(30) == 0;
    }
    
    // Função para obter um código de transação aleatório
    public static int obterCodTransacao() {
        Random random = new Random();
        return random.nextInt(3);
    }
    
    // Função para converter segundos em formato "HH:MM:SS"
    public static String horarioExpediente(int segundos) {
        String tempoRestante = converterSegundos(segundos);
        return segundos > TEMPO_FINAL_EXPEDIENTE ? "+" + tempoRestante : tempoRestante;
    }
    
    // Função auxiliar para converter segundos em formato "HH:MM:SS"
    public static String converterSegundos(int segundos) {
        int horas = segundos / 3600;
        int minutos = (segundos % 3600) / 60;
        int segundosRestantes = segundos % 60;
        
        String tempoRestante = String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);

        return tempoRestante;
    }
    
    // Sobrecarga da função para converter segundos (com precisão de ponto flutuante) em formato "HH:MM:SS"
    public static String converterSegundos(double segundos) {
        int horas = (int) segundos / 3600;
        int minutos = (int) (segundos % 3600) / 60;
        int segundosRestantes = (int) segundos % 60;
        
        return String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);
    }
}
