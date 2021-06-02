package com.example.eventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.eventos.database.EventoDAO;
import com.example.eventos.modelo.Evento;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroEventoActivity extends AppCompatActivity {
//    private final int RESULT_CODE_NOVO_EVENTO = 10;
//    private final int RESULT_CODE_EVENTO_EDITADO = 11;
//
//    private boolean edicao = false;
    private int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Evento");

        EditText editTextData = findViewById(R.id.editTextDate_data);
        TextWatcher textWatcher = MaskEditUtil.mask(editTextData, MaskEditUtil.FORMAT_DATE);
        editTextData.addTextChangedListener(textWatcher);
        carregarEvento();

    }

    private void carregarEvento() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("eventoEdicao") != null) {
            Evento evento = (Evento) intent.getExtras().get("eventoEdicao");
            EditText editTextNome = findViewById(R.id.editText_nome);
            EditText editTextLocal = findViewById(R.id.editText_local);
            EditText editTextData = findViewById(R.id.editTextDate_data);
            editTextNome.setText(evento.getNome());
            editTextLocal.setText(String.valueOf(evento.getLocal()));
            editTextData.setText(String.valueOf(evento.getData()));
 //         edicao = true;
            id = evento.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        EditText editTextNome = findViewById(R.id.editText_nome);
        EditText editTextLocal = findViewById(R.id.editText_local);
        EditText editTextData = findViewById(R.id.editTextDate_data);

        String nome = editTextNome.getText().toString();
        String local = editTextLocal.getText().toString();
        String dataString = editTextData.getText().toString();
        /*DateFormat formatar = new SimpleDateFormat("dd-MM-yyyy");
        Date data = formatar.parse(dataString);*/

        if (!validarCampo(nome)) {
            mostrarErro(editTextNome);
        }else if(!validarCampo(local)) {
            mostrarErro(editTextLocal);
        }else if(!validarCampo(dataString)) {
        mostrarErro(editTextData);
        }else{
            Evento evento = new Evento(id, nome, local, dataString);
//            Intent intent = new Intent();
//            if (edicao) {
//                intent.putExtra("eventoEditado", evento);
//                setResult(RESULT_CODE_EVENTO_EDITADO, intent);
//            } else {
            EventoDAO eventoDAO = new EventoDAO(getBaseContext());
            boolean salvou = eventoDAO.salvar(evento);
            if(salvou){
                finish();
            }else {
                Toast.makeText(CadastroEventoActivity.this, "Erro ao salvar", Toast.LENGTH_LONG).show();
            }

            }

    }


    private boolean validarCampo(String campo){
        if (campo.equals("")){
            return false;
        }else{
            return true;
        }
    }

    private void mostrarErro(EditText editText){
        editText.setError("Campo obrigatório");
    }
}


