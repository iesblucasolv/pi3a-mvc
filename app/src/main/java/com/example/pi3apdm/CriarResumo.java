package com.example.pi3apdm;

import com.example.pi3apdm.model.ResumoVO;
import com.example.pi3apdm.dao.ResumoDAO;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pi3apdm.dao.ResumoDAO;

import org.w3c.dom.Text;

public class CriarResumo extends AppCompatActivity {

    private int meuID = 0;
    private Button criar;
    private EditText titulo, conteudo;
    private  ResumoDAO resumoDAO = new ResumoDAO(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.escrever_resumo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        titulo = (EditText) findViewById(R.id.editTextTituloResumo);
        conteudo = (EditText) findViewById(R.id.editTextConteudoResumo);
        criar = (Button) findViewById(R.id.buttonPublicarResumo);
        criar.setOnClickListener(v-> criarResumo());
    }

    private void criarResumo(){
        ResumoVO novoResumo = new ResumoVO(
                resumoDAO.getCountResumos()+1,
                titulo.getText().toString(),
                conteudo.getText().toString(), meuID);
        resumoDAO.addResumo(novoResumo);
    }

}