package com.example.eventos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eventos.database.EventoDAO;
import com.example.eventos.modelo.Evento;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    // private final int REQUEST_CODE_NOVO_EVENTO = 1;
    //private final int RESULT_CODE_NOVO_EVENTO = 10;
    // private final int REQUEST_CODE_EDITAR_EVENTO = 2;
    // private final int RESULT_CODE_EVENTO_EDITADO = 11;


    private ListView listViewEventos;
    private ArrayAdapter<Evento> adapterEventos;
    private int id = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Meus Eventos");
        listViewEventos = findViewById(R.id.listView_eventos);
        definirOnClickListenerListView();
        excluirOnClickListenerListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventoDAO eventoDao = new EventoDAO(getBaseContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDao.listar());
        listViewEventos.setAdapter(adapterEventos);

    }

    private void definirOnClickListenerListView(){
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Evento eventoClicado = adapterEventos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroEventoActivity.class);
                intent.putExtra("eventoEdicao",eventoClicado);
                startActivity(intent);

            }
        });
    }

    public void onClickNovoEvento(View v){
        Intent intent = new Intent(MainActivity.this, CadastroEventoActivity.class);
        startActivity(intent);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode == REQUEST_CODE_EDITAR_EVENTO && resultCode == RESULT_CODE_EVENTO_EDITADO){
//            Evento eventoEditado = (Evento) data.getExtras().getSerializable("eventoEditado");
//            for (int i=0; i< adapterEventos.getCount(); i++){
//                Evento evento = adapterEventos.getItem(i);
//                if (evento.getId() == eventoEditado.getId()){
//                    adapterEventos.remove(evento);
//                    adapterEventos.insert(eventoEditado, i);
//                    break;
//                }
//            }
//            Toast.makeText(MainActivity.this, "Evento Editado", Toast.LENGTH_LONG).show();
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void excluirOnClickListenerListView(){
        listViewEventos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id)
            {
                Evento eventoClicado = adapterEventos.getItem(position);
                new AlertDialog.Builder(av.getContext())
                        .setTitle("EXCLUIR EVENTO")
                        .setMessage("Deseja excluir o evento " + eventoClicado.getNome() + "?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                EventoDAO eventoDAO = new EventoDAO(getBaseContext());
                                boolean excluiu = eventoDAO.Excluir(eventoClicado);
                                if(excluiu){
                                    adapterEventos.remove(eventoClicado);
                                }else {
                                    dialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Erro ao excluir", Toast.LENGTH_LONG).show();
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
