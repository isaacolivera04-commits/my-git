package com.example;
import java.util.ArrayList;
import java.util.Scanner;


public class taskmanager {

    static class Tarefa {
        String titulo;
        String responsavel;
        String status;

        Tarefa(String titulo, String responsavel) {
            this.titulo = titulo;
            this.responsavel = responsavel;
            this.status = "PENDENTE";
        }

        @Override
        public String toString() {
            return "[" + status + "] " + titulo + " (Responsável: " + responsavel + ")";
        }
    }

    private final ArrayList<Tarefa> tarefas = new ArrayList<>();
    private final ArrayList<String> usuarios = new ArrayList<>();
    private String usuarioLogado = null;

    public boolean cadastrarUsuario(String nome) {
        if (nome == null || nome.isBlank()) { System.out.println("Nome inválido."); return false; }
        if (usuarios.contains(nome)) { System.out.println("Usuário já cadastrado."); return false; }
        usuarios.add(nome);
        System.out.println("Usuário cadastrado: " + nome);
        return true;
    }

    public boolean login(String nome) {
        if (usuarios.contains(nome)) {
            usuarioLogado = nome;
            System.out.println("Bem-vindo, " + nome + "!");
            return true;
        }
        System.out.println("Usuário não encontrado.");
        return false;
    }

    public void logout() {
        System.out.println("Até logo, " + usuarioLogado + "!");
        usuarioLogado = null;
    }

    public boolean adicionarTarefa(String titulo) {
        if (usuarioLogado == null) { System.out.println("Faça login primeiro."); return false; }
        if (titulo == null || titulo.isBlank()) { System.out.println("Título inválido."); return false; }
        for (Tarefa t : tarefas) {
            if (t.titulo.equalsIgnoreCase(titulo)) { System.out.println("Tarefa já existe."); return false; }
        }
        tarefas.add(new Tarefa(titulo, usuarioLogado));
        System.out.println("Tarefa adicionada: " + titulo);
        return true;
    }

    public boolean removerTarefa(int index) {
        if (index < 0 || index >= tarefas.size()) { System.out.println("Índice inválido."); return false; }
        System.out.println("Tarefa removida: " + tarefas.get(index).titulo);
        tarefas.remove(index);
        return true;
    }

    public boolean alterarStatus(int index, String novoStatus) {
        if (index < 0 || index >= tarefas.size()) { System.out.println("Índice inválido."); return false; }
        tarefas.get(index).status = novoStatus.toUpperCase();
        System.out.println("Status atualizado!");
        return true;
    }

    public void exibirTarefas() {
        if (tarefas.isEmpty()) { System.out.println("Nenhuma tarefa cadastrada."); return; }
        System.out.println("\n===== TAREFAS =====");
        for (int i = 0; i < tarefas.size(); i++) System.out.println((i + 1) + ". " + tarefas.get(i));
        System.out.println("===================");
    }

    public void exibirRelatorio() {
        long pendentes = tarefas.stream().filter(t -> t.status.equals("PENDENTE")).count();
        long emAndamento = tarefas.stream().filter(t -> t.status.equals("EM_ANDAMENTO")).count();
        long concluidas = tarefas.stream().filter(t -> t.status.equals("CONCLUIDA")).count();
        System.out.println("\n===== RELATÓRIO =====");
        System.out.println("Total : " + tarefas.size());
        System.out.println("Pendentes : " + pendentes);
        System.out.println("Em andamento : " + emAndamento);
        System.out.println("Concluídas : " + concluidas);
        System.out.println("=====================");
    }

    public static void main(String[] args) {
        taskmanager manager = new taskmanager();
        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            System.out.println("=== Sistema de Gerenciamento de Tarefas ===");
            do {
                System.out.println("\n--- MENU ---");
                if (manager.usuarioLogado != null) System.out.println("Logado como: " + manager.usuarioLogado);
                System.out.println("1. Cadastrar usuário");
                System.out.println("2. Login");
                System.out.println("3. Adicionar tarefa");
                System.out.println("4. Remover tarefa");
                System.out.println("5. Alterar status");
                System.out.println("6. Exibir tarefas");
                System.out.println("7. Relatório");
                System.out.println("8. Logout");
                System.out.println("0. Sair");
                System.out.print("Escolha: ");
                choice = lerInt(scanner);
                switch (choice) {
                    case 1 -> { System.out.print("Nome: "); manager.cadastrarUsuario(scanner.nextLine().trim()); }
                    case 2 -> { System.out.print("Nome: "); manager.login(scanner.nextLine().trim()); }
                    case 3 -> { System.out.print("Título: "); manager.adicionarTarefa(scanner.nextLine().trim()); }
                    case 4 -> { manager.exibirTarefas(); System.out.print("Número: "); manager.removerTarefa(lerInt(scanner) - 1); }
                    case 5 -> { manager.exibirTarefas(); System.out.print("Número: "); int idx = lerInt(scanner) - 1;
                    System.out.println("1-PENDENTE 2-EM_ANDAMENTO 3-CONCLUIDA"); int s = lerInt(scanner);
                    String[] st = {"PENDENTE","EM_ANDAMENTO","CONCLUIDA"};
                    if (s >= 1 && s <= 3) manager.alterarStatus(idx, st[s-1]); }
                    case 6 -> manager.exibirTarefas();
                    case 7 -> manager.exibirRelatorio();
                    case 8 -> manager.logout();
                    case 0 -> System.out.println("Encerrando!");
                    default -> System.out.println("Opção inválida.");
                }
            } while (choice != 0);
        }
    }

    private static int lerInt(Scanner sc) {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}