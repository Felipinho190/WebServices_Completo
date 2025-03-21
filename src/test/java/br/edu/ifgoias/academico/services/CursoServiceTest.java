package br.edu.ifgoias.academico.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.repositories.CursoRepository;

class CursoServiceTest {

    @Mock
    private CursoRepository cursoRep;

    @InjectMocks
    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Testes para findAll()
    @Test
    void testFindAll() {
        // Arrange
        Curso curso1 = new Curso(1, "Engenharia de Software");
        Curso curso2 = new Curso(2, "Ciência da Computação");
        List<Curso> cursos = Arrays.asList(curso1, curso2);

        when(cursoRep.findAll()).thenReturn(cursos);

        // Act
        List<Curso> result = cursoService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Engenharia de Software", result.get(0).getNomecurso());
        verify(cursoRep, times(1)).findAll();
    }

    @Test
    void testFindAll_EmptyList() {
        // Arrange
        when(cursoRep.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Curso> result = cursoService.findAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(cursoRep, times(1)).findAll();
    }

    // Testes para findById(Integer id)
    @Test
    void testFindById_ExistingId() {
        // Arrange
        Curso curso = new Curso(1, "Engenharia de Software");
        when(cursoRep.findById(1)).thenReturn(Optional.of(curso));

        // Act
        Curso result = cursoService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals("Engenharia de Software", result.getNomecurso());
        verify(cursoRep, times(1)).findById(1);
    }

    @Test
    void testFindById_NonExistingId() {
        // Arrange
        when(cursoRep.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cursoService.findById(999);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(cursoRep, times(1)).findById(999);
    }

    // Testes para insert(Curso obj)
    @Test
    void testInsert() {
        // Arrange
        Curso curso = new Curso(null, "Engenharia de Software");
        Curso cursoSalvo = new Curso(1, "Engenharia de Software");
        when(cursoRep.save(curso)).thenReturn(cursoSalvo);

        // Act
        Curso result = cursoService.insert(curso);

        // Assert
        assertNotNull(result.getId());
        assertEquals(1, result.getId());
        verify(cursoRep, times(1)).save(curso);
    }

    // Testes para delete(Integer id)
    @Test
    void testDelete_ExistingId() {
        // Arrange
        doNothing().when(cursoRep).deleteById(1);

        // Act
        cursoService.delete(1);

        // Assert
        verify(cursoRep, times(1)).deleteById(1);
    }

    @Test
    void testDelete_NonExistingId() {
        // Arrange
        doNothing().when(cursoRep).deleteById(999);

        // Act
        cursoService.delete(999);

        // Assert
        verify(cursoRep, times(1)).deleteById(999);
    }

    // Testes para update(Integer id, Curso objAlterado)
    @Test
    void testUpdate_ExistingId() {
        // Arrange
        Curso cursoDB = new Curso(1, "Engenharia de Software");
        Curso cursoAlterado = new Curso(1, "Engenharia de Software Atualizado");
        when(cursoRep.findById(1)).thenReturn(Optional.of(cursoDB));
        when(cursoRep.save(any(Curso.class))).thenReturn(cursoAlterado);

        // Act
        Curso result = cursoService.update(1, cursoAlterado);

        // Assert
        assertEquals("Engenharia de Software Atualizado", result.getNomecurso());
        verify(cursoRep, times(1)).findById(1);
        verify(cursoRep, times(1)).save(cursoDB);
    }

    @Test
    void testUpdate_NonExistingId() {
        // Arrange
        when(cursoRep.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cursoService.update(999, new Curso());
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(cursoRep, times(1)).findById(999);
    }
}
