package br.edu.ifgoias.academico.resources;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.services.CursoService;

@WebMvcTest(CursoResource.class)
class CursoResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService servico;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    // Teste para GET /cursos
    @Test
    void testFindAll() throws Exception {
        // Arrange
        Curso curso1 = new Curso(1, "Engenharia de Software");
        Curso curso2 = new Curso(2, "Ciência da Computação");
        when(servico.findAll()).thenReturn(Arrays.asList(curso1, curso2));

        // Act & Assert
        mockMvc.perform(get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idcurso").value(1))
                .andExpect(jsonPath("$[0].nomecurso").value("Engenharia de Software"))
                .andExpect(jsonPath("$[1].idcurso").value(2))
                .andExpect(jsonPath("$[1].nomecurso").value("Ciência da Computação"));

        verify(servico, times(1)).findAll();
    }

    // Teste para GET /cursos/{id}
    @Test
    void testFindById_ExistingId() throws Exception {
        // Arrange
        Curso curso = new Curso(1, "Engenharia de Software");
        when(servico.findById(1)).thenReturn(curso);

        // Act & Assert
        mockMvc.perform(get("/cursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idcurso").value(1))
                .andExpect(jsonPath("$.nomecurso").value("Engenharia de Software"));

        verify(servico, times(1)).findById(1);
    }

    @Test
    void testFindById_NonExistingId() throws Exception {
        // Arrange
        when(servico.findById(999)).thenThrow(new RuntimeException("Curso não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/cursos/999"))
                .andExpect(status().isNotFound());

        verify(servico, times(1)).findById(999);
    }

    // Teste para POST /cursos
    @Test
    void testInsert() throws Exception {
        // Arrange
        Curso curso = new Curso(null, "Engenharia de Software");
        Curso cursoSalvo = new Curso(1, "Engenharia de Software");
        when(servico.insert(curso)).thenReturn(cursoSalvo);

        // Act & Assert
        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idcurso").value(1))
                .andExpect(jsonPath("$.nomecurso").value("Engenharia de Software"));

        verify(servico, times(1)).insert(curso);
    }

    // Teste para DELETE /cursos/{id}
    @Test
    void testDelete_ExistingId() throws Exception {
        // Arrange
        doNothing().when(servico).delete(1);

        // Act & Assert
        mockMvc.perform(delete("/cursos/1"))
                .andExpect(status().isNoContent());

        verify(servico, times(1)).delete(1);
    }

    @Test
    void testDelete_NonExistingId() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Curso não encontrado")).when(servico).delete(999);

        // Act & Assert
        mockMvc.perform(delete("/cursos/999"))
                .andExpect(status().isNotFound());

        verify(servico, times(1)).delete(999);
    }

    // Teste para PUT /cursos/{id}
    @Test
    void testUpdate_ExistingId() throws Exception {
        // Arrange
        Curso cursoAlterado = new Curso(1, "Engenharia de Software Atualizado");
        when(servico.update(1, cursoAlterado)).thenReturn(cursoAlterado);

        // Act & Assert
        mockMvc.perform(put("/cursos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoAlterado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idcurso").value(1))
                .andExpect(jsonPath("$.nomecurso").value("Engenharia de Software Atualizado"));

        verify(servico, times(1)).update(1, cursoAlterado);
    }

    @Test
    void testUpdate_NonExistingId() throws Exception {
        // Arrange
        Curso cursoAlterado = new Curso(999, "Curso Inexistente");
        when(servico.update(999, cursoAlterado)).thenThrow(new RuntimeException("Curso não encontrado"));

        // Act & Assert
        mockMvc.perform(put("/cursos/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoAlterado)))
                .andExpect(status().isNotFound());

        verify(servico, times(1)).update(999, cursoAlterado);
    }
}
