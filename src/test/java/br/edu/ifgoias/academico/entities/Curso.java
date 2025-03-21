package br.edu.ifgoias.academico.entities;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CursoTest {

    private Curso curso;
    private Aluno aluno1;
    private Aluno aluno2;

    @BeforeEach
    void setUp() {
        // Inicializa objetos para os testes
        curso = new Curso(1, "Engenharia de Software");
        aluno1 = new Aluno(1, "João Silva", "Masculino", null);
        aluno2 = new Aluno(2, "Maria Oliveira", "Feminino", null);
    }

    // Testes para Construtores
    @Test
    void testConstrutorPadrao() {
        Curso cursoVazio = new Curso();
        assertNotNull(cursoVazio);
        assertNull(cursoVazio.getIdcurso());
        assertNull(cursoVazio.getNomecurso());
        assertTrue(cursoVazio.getAlunos().isEmpty());
    }

    @Test
    void testConstrutorComParametros() {
        assertEquals(1, curso.getIdcurso());
        assertEquals("Engenharia de Software", curso.getNomecurso());
        assertTrue(curso.getAlunos().isEmpty());
    }

    // Testes para Getters e Setters
    @Test
    void testGetSetIdcurso() {
        curso.setIdcurso(2);
        assertEquals(2, curso.getIdcurso());
    }

    @Test
    void testGetSetNomecurso() {
        curso.setNomecurso("Ciência da Computação");
        assertEquals("Ciência da Computação", curso.getNomecurso());
    }

    @Test
    void testGetSetAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(aluno1);
        curso.setAlunos(alunos);
        assertEquals(1, curso.getAlunos().size());
        assertTrue(curso.getAlunos().contains(aluno1));
    }

    // Testes para Métodos Auxiliares
    @Test
    void testAddAluno() {
        curso.addAluno(aluno1);
        assertEquals(1, curso.getAlunos().size());
        assertTrue(curso.getAlunos().contains(aluno1));
        assertEquals(curso, aluno1.getCurso()); // Verifica a relação bidirecional
    }

    @Test
    void testAddAlunoDuplicado() {
        curso.addAluno(aluno1);
        curso.addAluno(aluno1); // Tentativa de adicionar o mesmo aluno novamente
        assertEquals(1, curso.getAlunos().size()); // O aluno não deve ser duplicado
    }

    @Test
    void testRemoveAluno() {
        curso.addAluno(aluno1);
        curso.removeAluno(aluno1);
        assertEquals(0, curso.getAlunos().size());
        assertNull(aluno1.getCurso()); // Verifica a remoção da referência ao curso
    }

    @Test
    void testRemoveAlunoInexistente() {
        curso.removeAluno(aluno1); // Tentativa de remover um aluno que não está na lista
        assertEquals(0, curso.getAlunos().size());
    }

    // Testes para hashCode e equals
    @Test
    void testHashCode() {
        Curso curso2 = new Curso(1, "Engenharia de Software");
        assertEquals(curso.hashCode(), curso2.hashCode());
    }

    @Test
    void testEquals_SameObject() {
        assertTrue(curso.equals(curso));
    }

    @Test
    void testEquals_DifferentObjectWithSameId() {
        Curso curso2 = new Curso(1, "Engenharia de Software");
        assertTrue(curso.equals(curso2));
    }

    @Test
    void testEquals_DifferentObjectWithDifferentId() {
        Curso curso2 = new Curso(2, "Ciência da Computação");
        assertFalse(curso.equals(curso2));
    }

    @Test
    void testEquals_NullObject() {
        assertFalse(curso.equals(null));
    }

    @Test
    void testEquals_DifferentClass() {
        assertFalse(curso.equals(new Object()));
    }

    // Testes para toString
    @Test
    void testToString() {
        String expected = "Curso [idcurso=1, nomecurso=Engenharia de Software]";
        assertEquals(expected, curso.toString());
    }
}
