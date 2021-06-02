package com.example.eventos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eventos.database.Entity.CategoriaDAO;
import com.example.eventos.modelo.Categoria;



public class ListarCategoriaActivity extends AppCompatActivity {

    private ListView listViewCategorias;
    private ArrayAdapter<Categoria> adapterCategorias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_categoria);
        listViewCategorias = findViewById(R.id.listView_categorias);
        definirOnClickListenerListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CategoriaDAO categoriaDao = new CategoriaDAO((getBaseContext()));
        adapterCategorias = new ArrayAdapter<Categoria>(ListarCategoriaActivity.this,
                android.R.layout.simple_list_item_1,
                categoriaDao.listar());
        listViewCategorias.setAdapter(adapterCategorias);

    }

    private void definirOnClickListenerListView(){
        listViewCategorias.setOnItemClickListener((parent, view, position, id) -> {
                Categoria categoriaClicado = adapterCategorias.getItem(position);
                Intent intent = new Intent(ListarCategoriaActivity.this, CadastroCategoriaActivity.class);
                intent.putExtra("categoriaEdicao",categoriaClicado);
                startActivity(intent);
        });
    }

    public void onClickNovaCategori(View v){
        Intent intent = new Intent(ListarCategoriaActivity.this, CadastroCategoriaActivity.class);
        startActivity(intent);
    }

    private void excluirOnClickListenerListView(){
        listViewCategorias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id)
            {
                Categoria categoriaClicado = adapterCategorias.getItem(position);
                new AlertDialog.Builder(av.getContext())
                        .setTitle("EXCLUIR CATEGORIA")
                        .setMessage("Deseja excluir a categoria " + categoriaClicado.getDescricao() + "?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                CategoriaDAO categoriaDAO = new CategoriaDAO(getBaseContext());
                                boolean excluiu = categoriaDAO.Excluir(categoriaClicado);
                                if(excluiu){
                                    adapterCategorias.remove(categoriaClicado);
                                }else {
                                    dialog.dismiss();
                                    Toast.makeText(ListarCategoriaActivity.this, "Erro ao excluir", Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("Não", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });
    }
}
