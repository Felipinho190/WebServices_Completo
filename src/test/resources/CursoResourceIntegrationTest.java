package br.edu.ifgoias.academico.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class CursoResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    // Configuração dinâmica do H2
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        registry.add("spring.datasource.driverClassName", () -> "org.h2.Driver");
        registry.add("spring.datasource.username", () -> "sa");
        registry.add("spring.datasource.password", () -> "");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.H2Dialect");
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // Teste para GET /cursos
    @Test
    void testFindAll() throws Exception {
        mockMvc.perform(get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Teste para POST /cursos
    @Test
    void testInsert() throws Exception {
        String cursoJson = """
                {
                    "nomecurso": "Engenharia de Software"
                }
                """;

        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cursoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idcurso").exists())
                .andExpect(jsonPath("$.nomecurso").value("Engenharia de Software"));
    }

    // Teste para DELETE /cursos/{id}
    @Test
    void testDelete() throws Exception {
        // Primeiro, insere um curso para ser deletado
        String cursoJson = """
                {
                    "nomecurso": "Curso Temporário"
                }
                """;
        String response = mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cursoJson))
                .andReturn().getResponse().getContentAsString();

        Integer id = Integer.parseInt(response.split("\"idcurso\":")[1].split(",")[0]);

        mockMvc.perform(delete("/cursos/" + id))
                .andExpect(status().isNoContent());
    }

    // Teste para PUT /cursos/{id}
    @Test
    void testUpdate() throws Exception {
        // Primeiro, insere um curso para ser atualizado
        String cursoJson = """
                {
                    "nomecurso": "Curso Antigo"
                }
                """;
        String response = mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cursoJson))
                .andReturn().getResponse().getContentAsString();

        Integer id = Integer.parseInt(response.split("\"idcurso\":")[1].split(",")[0]);

        String updatedCursoJson = """
                {
                    "nomecurso": "Curso Atualizado"
                }
                """;

        mockMvc.perform(put("/cursos/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCursoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idcurso").value(id))
                .andExpect(jsonPath("$.nomecurso").value("Curso Atualizado"));
    }
}
