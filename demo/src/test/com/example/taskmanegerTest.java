package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class taskmanagerTest {

    private taskmanager manager;

    @BeforeEach
    void setUp() {
        manager = new taskmanager();
    }

    @Test
    @DisplayName("CT01 - Login válido")
    void testLoginValido() {
        manager.cadastrarUsuario("João");
        assertTrue(manager.login("João"));
    }

    @Test
    @DisplayName("CT02 - Login inválido")
    void testLoginInvalido() {
        assertFalse(manager.login("Inexistente"));
    }

    @Test
    @DisplayName("CT03 - Login vazio")
    void testLoginVazio() {
        assertFalse(manager.login(""));
    }

    @Test
    @DisplayName("CT04 - Cadastrar tarefa")
    void testCadastrarTarefa() {
        manager.cadastrarUsuario("Maria");
        manager.login("Maria");
        assertTrue(manager.adicionarTarefa("Estudar Java"));
    }

    @Test
    @DisplayName("CT05 - Excluir tarefa")
    void testExcluirTarefa() {
        manager.cadastrarUsuario("Carlos");
        manager.login("Carlos");
        manager.adicionarTarefa("Fazer relatório");
        assertTrue(manager.removerTarefa(0));
    }

    @Test
    @DisplayName("CT06 - Tarefa duplicada")
    void testTarefaDuplicada() {
        manager.cadastrarUsuario("Ana");
        manager.login("Ana");
        manager.adicionarTarefa("Reunião");
        assertFalse(manager.adicionarTarefa("Reunião"));
    }

    @Test
    @DisplayName("CT07 - Usuário nome vazio")
    void testCadastrarUsuarioVazio() {
        assertFalse(manager.cadastrarUsuario("   "));
    }

    @Test
    @DisplayName("CT08 - Tarefa sem login")
    void testTarefaSemLogin() {
        assertFalse(manager.adicionarTarefa("Tarefa sem login"));
    }

    @Test
    @DisplayName("CT09 - Alterar status")
    void testAlterarStatus() {
        manager.cadastrarUsuario("Pedro");
        manager.login("Pedro");
        manager.adicionarTarefa("Revisar código");
        assertTrue(manager.alterarStatus(0, "CONCLUIDA"));
    }

    @Test
    @DisplayName("CT10 - Índice inválido")
    void testIndiceInvalido() {
        assertFalse(manager.removerTarefa(99));
    }
}