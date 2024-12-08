package com.example.agenda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.model.Aluno;
import com.example.agenda.ui.activity.ListaAlunosActivity;

import java.util.ArrayList;
import java.util.List;

public class ListaAlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos = new ArrayList<>();
    private final Context context;

    public ListaAlunosAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Aluno getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = getInflate(parent);//.inflate(R.layout.item_aluno,parent,attachToRoot);
        Aluno aluno = alunos.get(position);
        vinculaView(view, aluno);
        return view;
    }

    private void vinculaView(View view, Aluno aluno) {
        TextView nome = view.findViewById(R.id.item_aluno_nome);
        TextView telefone = view.findViewById(R.id.item_aluno_telefone);
        nome.setText(aluno.getNome());
        //telefone.setText(aluno.getTelefone());
    }

    private View getInflate(ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_aluno, parent, false);
    }

    public void atualiza(List<Aluno> listaAlunos){
        clear();
        addAll(listaAlunos);
        notifyDataSetChanged();
    }
    private void clear() {
        this.alunos.clear();
    }

    private void addAll(List<Aluno> alunos) {
        this.alunos.addAll(alunos);
    }

    public void remove(Aluno aluno) {
        this.alunos.remove(aluno);
        notifyDataSetChanged();

    }
}
