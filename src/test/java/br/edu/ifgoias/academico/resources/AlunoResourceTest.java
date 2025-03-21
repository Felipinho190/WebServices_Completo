package br.edu.ifgoias.academico.resources;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.services.AlunoService;

@ExtendWith(MockitoExtension.class)
public class AlunoResourceTest {

    @Mock
    private AlunoService servico;

    @InjectMocks
    private AlunoResource alunoResource;

    private Aluno aluno1;
    private Aluno aluno2;

    @BeforeEach
    public void setUp() {
        // Configura alunos fictícios para os testes
        aluno1 = new Aluno(1, "João", "Masculino");
        aluno2 = new Aluno(2, "Maria", "Feminino");
    }

    @Test
    public void testFindAll() {
        // Arrange
        List<Aluno> alunos = Arrays.asList(aluno1, aluno2);
        when(servico.findAll()).thenReturn(alunos);

        // Act
        ResponseEntity<List<Aluno>> response = alunoResource.findAll();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(servico, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        // Arrange
        Integer id = 1;
        when(servico.findById(id)).thenReturn(aluno1);

        // Act
        ResponseEntity<Aluno> response = alunoResource.findById(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João", response.getBody().getNome());
        verify(servico, times(1)).findById(id);
    }

    @Test
    public void testInsert() {
        // Arrange
        when(servico.insert(any(Aluno.class))).thenReturn(aluno1);

        // Act
        ResponseEntity<Aluno> response = alunoResource.insert(aluno1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João", response.getBody().getNome());
        verify(servico, times(1)).insert(any(Aluno.class));
    }

    @Test
    public void testDelete() {
        // Arrange
        Integer id = 1;

        // Act
        alunoResource.delete(id);

        // Assert
        verify(servico, times(1)).delete(id);
    }

    @Test
    public void testUpdate() {
        // Arrange
        Integer id = 1;
        Aluno alunoAtualizado = new Aluno(1, "João Atualizado", "Masculino");
        when(servico.update(eq(id), any(Aluno.class))).thenReturn(alunoAtualizado);

        // Act
        ResponseEntity<Aluno> response = alunoResource.update(id, aluno1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João Atualizado", response.getBody().getNome());
        verify(servico, times(1)).update(eq(id), any(Aluno.class));
    }
}
