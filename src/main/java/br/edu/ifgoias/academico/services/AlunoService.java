package br.edu.ifgoias.academico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.repositories.AlunoRepository;

@Service
public class AlunoService {

    private final AlunoRepository alunoRep;

    @Autowired
    public AlunoService(AlunoRepository alunoRep) {
        this.alunoRep = alunoRep;
    }

	public List<Aluno> findAll() {
		return alunoRep.findAll();
	}

	public Aluno findById(Integer id) {
		return alunoRep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Aluno insert(Aluno obj) {
		return alunoRep.save(obj);
	}

	public void delete(Integer id) {
		alunoRep.deleteById(id);
	}

	public Aluno update(Integer id, Aluno objAlterado) {
		return alunoRep.findById(id).map(alunoDB -> {
			alunoDB.setNome(obj_alterado.getNome());
			alunoDB.setSexo(obj_alterado.getSexo());
			alunoDB.setDt_nasc(obj_alterado.getDt_nasc());
			return alunoRep.save(alunoDB);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
