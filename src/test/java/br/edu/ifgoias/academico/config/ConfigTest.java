package br.edu.ifgoias.academico.config;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.repositories.AlunoRepository;
import br.edu.ifgoias.academico.repositories.CursoRepository;

class ConfigTest {

    @Mock
    private CursoRepository cursoRep;

    @Mock
    private AlunoRepository alunoRep;

    @InjectMocks
    private Config config;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRun() throws Exception {
        // Arrange
        when(cursoRep.count()).thenReturn(2L); // Simula 2 cursos no banco de dados

        Aluno a1 = new Aluno(null, "Caroline", "Feminino", Date.valueOf("2000-04-24"));
        Aluno a2 = new Aluno(null, "Isabelle", "Feminino", Date.valueOf("2000-12-28"));

        // Act
        config.run();

        // Assert
        verify(cursoRep, times(1)).count(); // Verifica se o método count() foi chamado
        verify(alunoRep, times(1)).save(a1); // Verifica se o aluno a1 foi salvo
        verify(alunoRep, times(1)).save(a2); // Verifica se o aluno a2 foi salvo
        verify(alunoRep, times(1)).count(); // Verifica se o método count() foi chamado

        // Validação adicional (opcional)
        // Aqui você pode adicionar verificações adicionais para garantir que os alunos foram salvos corretamente.
    }
}
