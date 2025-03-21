package br.edu.ifgoias.academico.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Calendar;

class AlunoTest {

    @Test
    void testGetStatusAdulto() {
        // Cria um aluno com idade >= 18
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -20); // Idade 20 anos
        Aluno aluno = new Aluno();
        aluno.setDtNasc(cal.getTime());

        assertEquals("Adulto", aluno.getStatus());
    }

    @Test
    void testGetStatusMenorDeIdade() {
        // Cria um aluno com idade < 18
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -16); // Idade 16 anos
        Aluno aluno = new Aluno();
        aluno.setDtNasc(cal.getTime());

        assertEquals("Menor de idade", aluno.getStatus());
    }
}
