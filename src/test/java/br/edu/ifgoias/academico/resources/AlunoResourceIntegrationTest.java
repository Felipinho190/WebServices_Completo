package br.edu.ifgoias.academico.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;

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
class AlunoResourceIntegrationTest {

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

    // Teste para GET /alunos
    @Test
    void testFindAll() throws Exception {
        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Teste para GET /alunos/{id}
    @Test
    void testFindById_ExistingId() throws Exception {
        // Primeiro, insere um aluno para ser buscado
        String alunoJson = """
                {
                    "nome": "João Silva",
                    "sexo": "Masculino",
                    "dt_nascimento": "2000-01-01"
                }
                """;
        String response = mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(alunoJson))
                .andReturn().getResponse().getContentAsString();

        Integer id = Integer.parseInt(response.split("\"idaluno\":")[1].split(",")[0]);

        mockMvc.perform(get("/alunos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idaluno").value(id))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.sexo").value("Masculino"))
                .andExpect(jsonPath("$.dt_nascimento").value("2000-01-01"));
    }

    // Teste para POST /alunos
    @Test
    void testInsert() throws Exception {
        String alunoJson = """
                {
                    "nome": "Maria Oliveira",
                    "sexo": "Feminino",
                    "dt_nascimento": "1995-05-15"
                }
                """;

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(alunoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idaluno").exists())
                .andExpect(jsonPath("$.nome").value("Maria Oliveira"))
                .andExpect(jsonPath("$.sexo").value("Feminino"))
                .andExpect(jsonPath("$.dt_nascimento").value("1995-05-15"));
    }

    // Teste para DELETE /alunos/{id}
    @Test
    void testDelete() throws Exception {
        // Primeiro, insere um aluno para ser deletado
        String alunoJson = """
                {
                    "nome": "Carlos Souza",
                    "sexo": "Masculino",
                    "dt_nascimento": "1985-12-10"
                }
                """;
        String response = mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(alunoJson))
                .andReturn().getResponse().getContentAsString();

        Integer id = Integer.parseInt(response.split("\"idaluno\":")[1].split(",")[0]);

        mockMvc.perform(delete("/alunos/" + id))
                .andExpect(status().isNoContent());
    }

    // Teste para PUT /alunos/{id}
    @Test
    void testUpdate() throws Exception {
        // Primeiro, insere um aluno para ser atualizado
        String alunoJson = """
                {
                    "nome": "Ana Pereira",
                    "sexo": "Feminino",
                    "dt_nascimento": "1990-07-20"
                }
                """;
        String response = mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(alunoJson))
                .andReturn().getResponse().getContentAsString();

        Integer id = Integer.parseInt(response.split("\"idaluno\":")[1].split(",")[0]);

        String updatedAlunoJson = """
                {
                    "nome": "Ana Pereira Atualizada",
                    "sexo": "Feminino",
                    "dt_nascimento": "1990-07-20"
                }
                """;

        mockMvc.perform(put("/alunos/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedAlunoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idaluno").value(id))
                .andExpect(jsonPath("$.nome").value("Ana Pereira Atualizada"));
    }
}
