package com.example.agenda.ui.activity;

import static com.example.agenda.ui.activity.ConstantsActivities.CHAVE_ALUNO;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.agenda.R;
import com.example.agenda.adapters.ListaAlunosAdapter;
import com.example.agenda.dao.AlunoDAO;
import com.example.agenda.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ListaAlunosActivity extends AppCompatActivity {
    private final String TITLE_APPBAR = "Lista de Alunos";
    private final AlunoDAO alunoDao = new AlunoDAO();
    private ListaAlunosAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Toolbar toolBar = findViewById(R.id.activity_lista_alunos_toolbar);
        toolBar.setTitle(TITLE_APPBAR);
        configuraFabNovoAluno();
        configurarLista();


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
        if (id == R.id.activity_lista_alunos_menu_remover) {
            confirmarRemocao(alunoEscolhido);
        }
        return super.onContextItemSelected(item);
    }

    private void confirmarRemocao(Aluno alunoEscolhido) {
        new AlertDialog.Builder(this).setTitle("Removendo aluno")
                .setMessage("Tem certeza que quer remover este aluno?").
                setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(alunoEscolhido);
                    }
                }).
                setNegativeButton("Não", null).show();
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
        adapter.atualiza(alunoDao.listarAlunos());
    }

    private void configurarLista() {
        ListView listaAlunos = findViewById(R.id.activity_lista_alunos_listview);
        configuraAdapter(listaAlunos);
        configuraListenerDeCliquePorItem(listaAlunos);
        registerForContextMenu(listaAlunos);

    }



    private void remove(Aluno aluno) {
        adapter.remove(aluno);
        alunoDao.remover(aluno);
        adapter.notifyDataSetChanged();
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
        adapter = new ListaAlunosAdapter(this);

        listaAlunos.setAdapter(adapter);
    }
}