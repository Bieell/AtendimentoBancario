package TADS;

import TADS.entities.Node;
import atendimentobancario.model.Cliente;

public class Fila {
    private Node inicio, fim;
    
    public Fila() {
        inicio = null;
        fim = null;
    }
    
    public boolean isEmpty() {
        return inicio == null;
    }
    
    public void enqueue(Cliente cliente) {
        Node newNo = new Node(cliente);
        if(inicio == null) {
            inicio = newNo;
            fim = inicio;
            return;
        }
        
        fim.setNext(newNo);
        fim = newNo;
    }
    
    public Cliente dequeue() {
        if(isEmpty()) return null;
        
        Node temp = inicio;
        inicio = inicio.getNext();
        if(inicio == null) fim = null;
        return temp.getObject();
    }
    
    public void display() {
        if (isEmpty()) {
            System.out.print("Fila vazia.");
            return;
        }
        Node atual = inicio; 
        while (atual != null) {
            System.out.println(atual.getObject());
            atual = atual.getNext();
        }
    }
}
