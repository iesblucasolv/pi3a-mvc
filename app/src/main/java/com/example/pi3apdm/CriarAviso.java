package com.example.pi3apdm;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pi3apdm.dao.AvisoDAO;
import com.example.pi3apdm.model.AvisoVO;

public class CriarAviso extends AppCompatActivity {
    private int meuID = 0;
    private Button criar;
    private EditText titulo, conteudo;
    private final AvisoDAO avisoDAO = new AvisoDAO(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.escrever_aviso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        titulo = (EditText) findViewById(R.id.editTextTituloAviso);
        conteudo = (EditText) findViewById(R.id.editTextConteudoAviso);
        criar = (Button) findViewById(R.id.buttonPublicarAviso);
        criar.setOnClickListener(v-> criarAviso());
    }

    private void criarAviso(){
        AvisoVO novoAviso = new AvisoVO(
                avisoDAO.getCountAvisos()+1,
                titulo.getText().toString(),
                conteudo.getText().toString(), meuID);
        avisoDAO.addAviso(novoAviso);
    }
}