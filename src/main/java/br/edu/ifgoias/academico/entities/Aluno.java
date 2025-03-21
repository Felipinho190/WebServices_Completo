package br.edu.ifgoias.academico.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idaluno;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "sexo", nullable = false)
    private String sexo;

    @Column(name = "dtNasc", nullable = false)
    private Date dtNasc;

    @ManyToOne
    @JoinColumn(name = "idcurso")
    private Curso curso;

    public Aluno() {
    }

    public Aluno(Integer id, String nome, String sexo, Date date) {
        this.idaluno = id;
        this.nome = nome;
        this.sexo = sexo;
        this.dtNasc = date;
    }

    public Integer getIdaluno() {
        return idaluno;
    }

    public void setIdaluno(Integer idaluno) {
        this.idaluno = idaluno;
    }

    // Adiciona o método getId() para compatibilidade com os testes
    public Integer getId() {
        return this.getIdaluno();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso c) {
        this.curso = c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dtNasc, idaluno, nome, sexo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aluno other = (Aluno) obj;
        return Objects.equals(dtNasc, other.dtNasc) && Objects.equals(idaluno, other.idaluno)
                && Objects.equals(nome, other.nome) && Objects.equals(sexo, other.sexo);
    }

    @Override
    public String toString() {
        return "Aluno [idaluno=" + idaluno + ", nome=" + nome + ", sexo=" + sexo + ", dtNasc=" + dtNasc + "]";
    }

    // Novo método para determinar o status do aluno
    public String getStatus() {
        LocalDate birthDate = new java.sql.Date(dtNasc.getTime()).toLocalDate();
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(birthDate, today);

        if (age >= 18) {
            return "Adulto";
        } else {
            return "Menor de idade";
        }
    }
}
