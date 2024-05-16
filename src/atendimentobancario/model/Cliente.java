package atendimentobancario.model;

import java.time.LocalDateTime;

public class Cliente {
    private String nome;
    private LocalDateTime inicioAtendimento;
    private LocalDateTime fimAtendimento;

    public Cliente(String nome) {
        this.nome = nome;
    }    
}
