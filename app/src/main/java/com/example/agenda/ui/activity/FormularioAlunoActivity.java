package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.agenda.R;
import com.example.agenda.dao.AlunoDAO;
import com.example.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {
    public static final String TITULO_APPBAR = "Novo Aluno";
    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtEmail;
    private final AlunoDAO dao = new AlunoDAO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        Toolbar toolbar = findViewById(R.id.formulario_aluno_toolbar);
        toolbar.setTitle(TITULO_APPBAR);
        setSupportActionBar(toolbar);

        inicializarCampos();

        Button btnSalvar = findViewById(R.id.activity_formulario_botao_salvar);
        btnSalvar.setOnClickListener(v -> {
            Aluno aluno = getAluno(edtNome, edtTelefone, edtEmail);
            salvarAluno(aluno);

        });
    }

    private void inicializarCampos() {
        edtNome = findViewById(R.id.activity_formulario_aluno_nome);
        edtTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        edtEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void salvarAluno(Aluno aluno) {
        aluno = getAluno(edtNome, edtTelefone, edtEmail);
        dao.salvar(aluno);
        finish();
    }

    private static @NonNull Aluno getAluno(EditText edtNome, EditText edtTelefone, EditText edtEmail) {
        String nome = edtNome.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String email = edtEmail.getText().toString();
        Aluno aluno = new Aluno(nome, telefone, email);
        return aluno;
    }

}