package fila;

import entidades.Mensagem;

public class FilaMensagens {

    private Mensagem[] fila;
    private int inicio = 0, fim = -1, tamanho = 0;
    private int capacidade;

    public FilaMensagens(int capacidade) {
        this.capacidade = capacidade;
        this.fila = new Mensagem[capacidade];
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public boolean isFull() {
        return tamanho == capacidade;
    }

    public int size() {
        return tamanho;
    }

    public boolean enqueue(Mensagem msg) {
        if (isFull()) {
            System.out.println("[ERRO] Fila cheia!");
            return false;
        }
        fim = (fim + 1) % capacidade;
        fila[fim] = msg;
        tamanho++;
        return true;
    }

    public Mensagem dequeue() {
        if (isEmpty()) {
            System.out.println("[ERRO] Fila vazia!");
            return null;
        }
        Mensagem msg = fila[inicio];
        fila[inicio] = null; // limpa referência
        inicio = (inicio + 1) % capacidade;
        tamanho--;
        return msg;
    }

    public Mensagem peek() {
        return isEmpty() ? null : fila[inicio];
    }
}