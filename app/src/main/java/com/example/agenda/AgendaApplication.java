package com.example.agenda;

import android.app.Application;

import com.example.agenda.dao.AlunoDAO;
import com.example.agenda.model.Aluno;

public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        geraAlunoTeste();


    }

    private void geraAlunoTeste() {
        AlunoDAO alunoDao = new AlunoDAO();
        alunoDao.salvar(new Aluno("Kauan", "823923", "email"));
    }
}