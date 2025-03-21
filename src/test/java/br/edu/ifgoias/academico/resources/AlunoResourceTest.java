package br.edu.ifgoias.academico.resources;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.services.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AlunoResourceTest {

    @Mock
    private AlunoService servico;

    @InjectMocks
    private AlunoResource alunoResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        Aluno aluno1 = new Aluno(1, "João Silva", "123456789", new Date());
        Aluno aluno2 = new Aluno(2, "Maria Oliveira", "987654321", new Date());
        List<Aluno> alunos = Arrays.asList(aluno1, aluno2);

        when(servico.findAll()).thenReturn(alunos);

        // Act
        ResponseEntity<List<Aluno>> response = alunoResource.findAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("João Silva", response.getBody().get(0).getNome());
        verify(servico, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        Aluno aluno = new Aluno(1, "João Silva", "123456789", new Date());
        when(servico.findById(1)).thenReturn(aluno);

        // Act
        ResponseEntity<Aluno> response = alunoResource.findById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João Silva", response.getBody().getNome());
        verify(servico, times(1)).findById(1);
    }

    @Test
    void testInsert() {
        // Arrange
        Aluno aluno = new Aluno(null, "João Silva", "123456789", new Date());
        Aluno alunoSalvo = new Aluno(1, "João Silva", "123456789", new Date());
        when(servico.insert(aluno)).thenReturn(alunoSalvo);

        // Act
        ResponseEntity<Aluno> response = alunoResource.insert(aluno);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("João Silva", response.getBody().getNome());
        verify(servico, times(1)).insert(aluno);
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(servico).delete(1);

        // Act
        alunoResource.delete(1);

        // Assert
        verify(servico, times(1)).delete(1);
    }

    @Test
    void testUpdate() {
        // Arrange
        Aluno alunoAtualizado = new Aluno(1, "João Silva Atualizado", "123456789", new Date());
        when(servico.update(1, alunoAtualizado)).thenReturn(alunoAtualizado);

        // Act
        ResponseEntity<Aluno> response = alunoResource.update(1, alunoAtualizado);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João Silva Atualizado", response.getBody().getNome());
        verify(servico, times(1)).update(1, alunoAtualizado);
    }
}
