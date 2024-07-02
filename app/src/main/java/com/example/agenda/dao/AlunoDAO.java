package com.example.agenda.dao;

import com.example.agenda.model.Aluno;

import java.security.cert.PKIXRevocationChecker;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class AlunoDAO {
    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contadorId = 1;

    public void salvar(Aluno aluno) {
        aluno.setId(contadorId);
        alunos.add(aluno);
        contadorId++;
    }

    public void edita(Aluno aluno) {
       /* alunos.forEach(a ->
        {
            if (a.getId() == aluno.getId()) {
                a = aluno;
            }
        });
        if (aluno != null) {
            int posicaoDoAluno = alunos.indexOf(aluno);
            alunos.set(posicaoDoAluno, aluno);
        }*/
        Aluno a = alunos.stream().filter(aln -> aln.getId() == aluno.getId()).findFirst().orElse(null);
        int pos = alunos.indexOf(a);
        alunos.set(pos,aluno);
    }

    public List<Aluno> listarAlunos() {
        return new ArrayList<>(alunos);
    }
    public void remover(Aluno aluno){
        alunos.removeIf(a-> a == aluno);
    }
    public Aluno buscarAlunoPorId(int id){
        return alunos.stream().filter(a -> a.getId() == id).findFirst().get();
    }

}
