package com.example.agenda.dao;

import com.example.agenda.model.Aluno;
import java.util.List;
import java.util.ArrayList;

public class AlunoDAO {
    private final static List<Aluno> alunos= new ArrayList<>();

    public void salvar(Aluno aluno) {
        alunos.add(aluno);
    }

    public List<Aluno> listarAlunos(){
        return new ArrayList<>(alunos);
    }

}
