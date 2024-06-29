package com.example.agenda.ui.activity;

import static com.example.agenda.ui.activity.ConstantsActivities.CHAVE_ALUNO;

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

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {
    private final String TITLE_APPBAR = "Lista de Alunos";
    private final AlunoDAO alunoDao = new AlunoDAO();
    private ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Toolbar toolBar = findViewById(R.id.activity_lista_alunos_toolbar);
        toolBar.setTitle(TITLE_APPBAR);
        configuraFabNovoAluno();
        configurarLista();


    }

    private void configuraFabNovoAluno() {
        FloatingActionButton fab = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        fab.setOnClickListener(v -> inicializarFormularioModoInsereAluno());
    }

    private void inicializarFormularioModoInsereAluno() {
        startActivity(new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaAlunos();
    }

    private void atualizaAlunos() {
        adapter.clear();
        adapter.addAll(alunoDao.listarAlunos());
    }

    private void configurarLista() {
        ListView listaAlunos = findViewById(R.id.activity_lista_alunos_listview);
        configuraAdapter(listaAlunos);
        configuraListenerDeCliquePorItem(listaAlunos);
        configurarListenerDeCliqueLongoPorItem(listaAlunos);
    }

    private void configurarListenerDeCliqueLongoPorItem(ListView listaAlunos) {
        listaAlunos.setOnItemLongClickListener((parent, view, position, id) -> {
            Aluno aluno = (Aluno) parent.getItemAtPosition(position);
            remove(aluno);
            return true;
        });
    }

    private void remove(Aluno aluno) {
        adapter.remove(aluno);
        alunoDao.remover(aluno);
    }

    private void configuraListenerDeCliquePorItem(ListView listaAlunos) {
        listaAlunos.setOnItemClickListener((parent, view, position, id) -> {//seleciona um item em específico da lista
            Aluno aluno = (Aluno) parent.getItemAtPosition(position);//obtem o item da lista, usar o parent ou a listview
            abreFormularioModoEditarAluno(aluno);
        });
    }

    private void abreFormularioModoEditarAluno(Aluno aluno) {
        //O intent vai do contexto que eu estou para a classe que quero ir
        Intent intentIrFormulario = new Intent(this, FormularioAlunoActivity.class);
        //insere dados extras para a intent
        intentIrFormulario.putExtra(CHAVE_ALUNO, aluno);
        //inicia outra activity
        startActivity(intentIrFormulario);
    }

    private void configuraAdapter(ListView listaAlunos) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunoDao.listarAlunos());
        listaAlunos.setAdapter(adapter);
    }
}