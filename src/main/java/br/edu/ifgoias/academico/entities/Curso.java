package br.edu.ifgoias.academico.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;

@Entity
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcurso;

    @Column(name = "nomecurso", nullable = false)
    private String nomecurso;

    @OneToMany(mappedBy = "curso")
    @JsonIgnore
    private List<Aluno> alunos = new ArrayList<>();

    // Construtor padrão
    public Curso() {}

    // Construtor com parâmetros
    public Curso(Integer idcurso, String nomecurso) {
        this.idcurso = idcurso;
        this.nomecurso = nomecurso;
    }

    // Getters e Setters
    public Integer getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(Integer idcurso) {
        this.idcurso = idcurso;
    }

    public String getNomecurso() {
        return nomecurso;
    }

    public void setNomecurso(String nomecurso) {
        this.nomecurso = nomecurso;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    // Método para adicionar um aluno ao curso
    public void addAluno(Aluno aluno) {
        if (!this.alunos.contains(aluno)) {
            this.alunos.add(aluno);
            aluno.setCurso(this); // Garante a consistência da relação bidirecional
        }
    }

    // Método para remover um aluno do curso
    public void removeAluno(Aluno aluno) {
        if (this.alunos.contains(aluno)) {
            this.alunos.remove(aluno); // Corrige o loop infinito
            aluno.setCurso(null); // Remove a referência ao curso
        }
    }

    // Representação textual do objeto
    @Override
    public String toString() {
        return "Curso [idcurso=" + idcurso + ", nomecurso=" + nomecurso + "]";
    }

    // Métodos hashCode e equals (opcional, mas recomendado para comparação de objetos)
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idcurso == null) ? 0 : idcurso.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Curso other = (Curso) obj;
        if (idcurso == null) {
            if (other.idcurso != null)
                return false;
        } else if (!idcurso.equals(other.idcurso))
            return false;
        return true;
    }
}
