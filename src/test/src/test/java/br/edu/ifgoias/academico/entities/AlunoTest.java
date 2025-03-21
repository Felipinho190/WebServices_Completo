package br.edu.ifgoias.academico.entities; // Pacote correspondente à classe Aluno

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlunoTest {

    @Test
    void testGetStatusAdulto() {
        Aluno aluno = new Aluno();
        aluno.setIdade(20); // Idade >= 18
        assertEquals("Adulto", aluno.getStatus()); // Testa o branch "if"
    }

    @Test
    void testGetStatusMenorDeIdade() {
        Aluno aluno = new Aluno();
        aluno.setIdade(16); // Idade < 18
        assertEquals("Menor de idade", aluno.getStatus()); // Testa o branch "else"
    }
}
