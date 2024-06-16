package com.example.pi3apdm;

import com.example.pi3apdm.dao.ResumoDAO;
import com.example.pi3apdm.model.ResumoVO;

import android.content.Intent;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class VerResumoProf extends AppCompatActivity {
    ResumoDAO resumoDAO = new ResumoDAO(this);
    private Button novoResumo, voltar;
    private ListView listaResumos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ver_resumos_vprofessores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        novoResumo = (Button) findViewById(R.id.buttonEscreverResumo);
        voltar = (Button) findViewById(R.id.buttonVoltarMenu);
        listaResumos = (ListView) findViewById(R.id.listViewResumos);

        listarResumos();
    }
    @Override
    public void onResume(){
        super.onResume();
        listarResumos();
    }
    public void btnNovoResumo(View view){
        Intent intent = new Intent(this, CriarResumo.class);
        startActivity(intent);
    }

    private void listarResumos(){
        ArrayList<ResumoVO> lista = new ArrayList<>(resumoDAO.getAllResumos());
        ArrayAdapter<ResumoVO> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaResumos.setAdapter(adapter);
    }

}