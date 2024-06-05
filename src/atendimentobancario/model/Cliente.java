package atendimentobancario.model;

import java.time.LocalDateTime;

public class Cliente {
    private int horarioEntrada;
    

    public Cliente(int horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public Cliente() {
    }

    public int getHorarioEntrada() {
        return horarioEntrada;
    }
    
    
}
