package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.agenda.R;
import com.example.agenda.dao.AlunoDAO;
import com.example.agenda.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaAlunosActivity extends AppCompatActivity {
    private final String TITLE_APPBAR = "Lista de Alunos";
    private final AlunoDAO alunoDao = new AlunoDAO();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Toolbar toolBar = findViewById(R.id.activity_lista_alunos_toolbar);
        toolBar.setTitle(TITLE_APPBAR);
        configuraFabNovoAluno();


    }

    private void configuraFabNovoAluno() {
        FloatingActionButton fab = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        fab.setOnClickListener(v -> inicializarFormulario());
    }

    private void inicializarFormulario() {
        startActivity(new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarLista();
    }

    private void configurarLista() {
        ListView listaAlunos = findViewById(R.id.activity_lista_alunos_listview);
        listaAlunos.setAdapter(new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1,alunoDao.listarAlunos()));
    }
}