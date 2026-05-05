package aplicacao;

import entidades.Mensagem;
import fila.FilaMensagens;
import java.util.Scanner;

public class AtendimentoMensagem {

    private static final int CAPACIDADE = 50;

    private static FilaMensagens filaReclamacao = new FilaMensagens(CAPACIDADE);
    private static FilaMensagens filaSugestao   = new FilaMensagens(CAPACIDADE);
    private static FilaMensagens filaResolucao  = new FilaMensagens(CAPACIDADE);

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int op;

        do {
            menu();
            op = lerInt("Escolha: ");

            switch (op) {
                case 0 -> op = podeEncerrar();
                case 1 -> receberMensagem();
                case 2 -> atenderMensagem();
                case 3 -> processarResolucao();
                default -> System.out.println("[ERRO] Opção inválida.");
            }

        } while (op != 0);

        System.out.println("Sistema encerrado.");
        sc.close();
    }

    public static void menu() {
        System.out.println("\n--- MENU ---");
        System.out.println("Reclamações: " + filaReclamacao.size());
        System.out.println("Sugestões:   " + filaSugestao.size());
        System.out.println("Resoluções:  " + filaResolucao.size());
        System.out.println("0-Sair  1-Receber  2-Atender  3-Resolver");
    }

    public static int podeEncerrar() {
        if (!filaReclamacao.isEmpty() || !filaSugestao.isEmpty() || !filaResolucao.isEmpty()) {
            System.out.println("[AVISO] Ainda há mensagens pendentes.");
            return 1;
        }
        return 0;
    }

    public static void receberMensagem() {
        System.out.println("\n--- NOVA MENSAGEM ---");

        int canal = lerOpcao("1-App  2-WhatsApp: ", 1, 2);
        String canalStr = (canal == 1) ? "APP" : "WHATSAPP";

        System.out.print("Nome (opcional): ");
        String nome = sc.nextLine().trim();

        String contato = lerTextoObrigatorio(
                canal == 1 ? "Email: " : "Telefone: "
        );

        int motivo = lerOpcao("1-Reclamação  2-Sugestão: ", 1, 2);
        String texto = lerTextoObrigatorio("Mensagem: ");

        Mensagem msg = new Mensagem(nome, contato, motivo, texto, canalStr);

        FilaMensagens fila = (motivo == 1) ? filaReclamacao : filaSugestao;
        fila.enqueue(msg);

        System.out.println("[OK] Mensagem adicionada.");
    }

    public static void atenderMensagem() {
        System.out.println("\n--- ATENDER ---");

        int tipo = lerOpcao("1-Reclamação  2-Sugestão: ", 1, 2);
        FilaMensagens fila = (tipo == 1) ? filaReclamacao : filaSugestao;

        if (fila.isEmpty()) {
            System.out.println("[AVISO] Fila vazia.");
            return;
        }

        Mensagem msg = fila.dequeue();
        System.out.println(msg);

        int acao = lerOpcao("1-Resolver  2-Encaminhar: ", 1, 2);

        if (acao == 1) {
            System.out.println("Resposta enviada.");
        } else {
            filaResolucao.enqueue(msg);
            System.out.println("Encaminhado para resolução.");
        }
    }

    public static void processarResolucao() {
        System.out.println("\n--- RESOLUÇÃO ---");

        if (filaResolucao.isEmpty()) {
            System.out.println("[AVISO] Nenhuma pendência.");
            return;
        }

        Mensagem msg = filaResolucao.dequeue();
        System.out.println(msg);
        System.out.println("Resolvido pelo setor responsável.");
    }

    // ===== UTILITÁRIOS =====

    public static int lerInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("[ERRO] Digite um número válido.");
            }
        }
    }

    public static int lerOpcao(String msg, int min, int max) {
        int op;
        do {
            op = lerInt(msg);
        } while (op < min || op > max);
        return op;
    }

    public static String lerTextoObrigatorio(String msg) {
        String txt;
        do {
            System.out.print(msg);
            txt = sc.nextLine().trim();
        } while (txt.isBlank());
        return txt;
    }
}