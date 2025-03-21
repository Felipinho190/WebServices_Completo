package br.edu.ifgoias.academico.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.repositories.AlunoRepository;

class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRep;

    @InjectMocks
    private AlunoService alunoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Testes para findAll()
    @Test
    void testFindAll() {
        // Arrange
        Aluno aluno1 = new Aluno(1, "João Silva", "Masculino", new Date());
        Aluno aluno2 = new Aluno(2, "Maria Oliveira", "Feminino", new Date());
        List<Aluno> alunos = Arrays.asList(aluno1, aluno2);

        when(alunoRep.findAll()).thenReturn(alunos);

        // Act
        List<Aluno> result = alunoService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("João Silva", result.get(0).getNome());
        verify(alunoRep, times(1)).findAll();
    }

    @Test
    void testFindAll_EmptyList() {
        // Arrange
        when(alunoRep.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Aluno> result = alunoService.findAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(alunoRep, times(1)).findAll();
    }

    // Testes para findById(Integer id)
    @Test
    void testFindById_ExistingId() {
        // Arrange
        Aluno aluno = new Aluno(1, "João Silva", "Masculino", new Date());
        when(alunoRep.findById(1)).thenReturn(Optional.of(aluno));

        // Act
        Aluno result = alunoService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals("João Silva", result.getNome());
        verify(alunoRep, times(1)).findById(1);
    }

    @Test
    void testFindById_NonExistingId() {
        // Arrange
        when(alunoRep.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            alunoService.findById(999);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(alunoRep, times(1)).findById(999);
    }

    // Testes para insert(Aluno obj)
    @Test
    void testInsert() {
        // Arrange
        Aluno aluno = new Aluno(null, "João Silva", "Masculino", new Date());
        Aluno alunoSalvo = new Aluno(1, "João Silva", "Masculino", new Date());
        when(alunoRep.save(aluno)).thenReturn(alunoSalvo);

        // Act
        Aluno result = alunoService.insert(aluno);

        // Assert
        assertNotNull(result.getId());
        assertEquals(1, result.getId());
        verify(alunoRep, times(1)).save(aluno);
    }

    // Testes para delete(Integer id)
    @Test
    void testDelete_ExistingId() {
        // Arrange
        doNothing().when(alunoRep).deleteById(1);

        // Act
        alunoService.delete(1);

        // Assert
        verify(alunoRep, times(1)).deleteById(1);
    }

    @Test
    void testDelete_NonExistingId() {
        // Arrange
        doNothing().when(alunoRep).deleteById(999);

        // Act
        alunoService.delete(999);

        // Assert
        verify(alunoRep, times(1)).deleteById(999);
    }

    // Testes para update(Integer id, Aluno objAlterado)
    @Test
    void testUpdate_ExistingId() {
        // Arrange
        Aluno alunoDB = new Aluno(1, "João Silva", "Masculino", new Date());
        Aluno alunoAlterado = new Aluno(1, "João Silva Atualizado", "Masculino", new Date());
        when(alunoRep.findById(1)).thenReturn(Optional.of(alunoDB));
        when(alunoRep.save(any(Aluno.class))).thenReturn(alunoAlterado);

        // Act
        Aluno result = alunoService.update(1, alunoAlterado);

        // Assert
        assertEquals("João Silva Atualizado", result.getNome());
        verify(alunoRep, times(1)).findById(1);
        verify(alunoRep, times(1)).save(alunoDB);
    }

    @Test
    void testUpdate_NonExistingId() {
        // Arrange
        when(alunoRep.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            alunoService.update(999, new Aluno());
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(alunoRep, times(1)).findById(999);
    }
}
