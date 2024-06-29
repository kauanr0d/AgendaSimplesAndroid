package com.example.agenda.ui.activity;

import static com.example.agenda.ui.activity.ConstantsActivities.CHAVE_ALUNO;

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

import java.util.Optional;

public class FormularioAlunoActivity extends AppCompatActivity {
    private static final String TITULO_APPBAR_EDITAR_ALUNO = "Editar Aluno";
    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo Aluno";
    private final AlunoDAO dao = new AlunoDAO();
    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtEmail;
    private Aluno aluno = new Aluno();

    private static @NonNull Aluno getAluno(EditText edtNome, EditText edtTelefone, EditText edtEmail) {
        String nome = edtNome.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String email = edtEmail.getText().toString();
        Aluno aluno = new Aluno(nome, telefone, email);
        return aluno;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        Toolbar toolbar = findViewById(R.id.formulario_aluno_toolbar);

        setSupportActionBar(toolbar);

        inicializarCampos();
        configurarBtnSalvar();
        carregaAluno(toolbar);


    }

    private void carregaAluno(Toolbar toolbar) {
        Intent dados = getIntent(); // Recebe os dados da intent que iniciou esta atividade
        if (dados.hasExtra(CHAVE_ALUNO)) {
            toolbar.setTitle(TITULO_APPBAR_EDITAR_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            Optional.ofNullable(aluno).ifPresent(a -> {
                preencheCampos();
            });
        } else {
            toolbar.setTitle(TITULO_APPBAR_NOVO_ALUNO);
        }
    }

    private void preencheCampos() {
        edtNome.setText(aluno.getNome());
        edtTelefone.setText(aluno.getTelefone());
        edtEmail.setText(aluno.getEmail());
    }

    private void configurarBtnSalvar() {
        Button btnSalvar = findViewById(R.id.activity_formulario_botao_salvar);
        btnSalvar.setOnClickListener(v -> {
            finalizaFormulario();

        });
    }

    private void finalizaFormulario() {
        //Aluno aluno = getAluno(edtNome, edtTelefone, edtEmail);
        // salvarAluno(aluno);
        preencheAluno();
        if (aluno.temIdValido() && (aluno != null)) {
            dao.edita(aluno);
        } else {
            salvarAluno(aluno);
        }
        finish();

    }

    private void inicializarCampos() {
        edtNome = findViewById(R.id.activity_formulario_aluno_nome);
        edtTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        edtEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void salvarAluno(Aluno aluno) {
        aluno = getAluno(edtNome, edtTelefone, edtEmail);
        dao.salvar(aluno);
    }

    private void preencheAluno() {
        String nome = edtNome.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String email = edtEmail.getText().toString();
        Optional.ofNullable(aluno).ifPresent(a -> {
            aluno.setNome(nome);
            aluno.setTelefone(telefone);
            aluno.setEmail(email);
        });
    }

}