package com.example.pi3apdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pi3apdm.dao.AvisoDAO;
import com.example.pi3apdm.model.AvisoVO;

import java.util.ArrayList;

public class VerAvisoProf extends AppCompatActivity {

    AvisoDAO AvisoDAO = new AvisoDAO(this);
    private Button novoAviso, voltar;
    private ListView listaAvisos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ver_avisos_vprofessores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        novoAviso = (Button) findViewById(R.id.buttonEscreverAviso);
        voltar = (Button) findViewById(R.id.buttonVoltarMenu);
        listaAvisos = (ListView) findViewById(R.id.listViewAvisos);

        listarAvisos();
    }
    @Override
    public void onResume(){
        super.onResume();
        listarAvisos();
    }
    public void btnNovoAviso(View view){
        Intent intent = new Intent(this, CriarAviso.class);
        startActivity(intent);
    }

    private void listarAvisos(){
        ArrayList<AvisoVO> lista = new ArrayList<>(AvisoDAO.getAllAvisos());
        ArrayAdapter<AvisoVO> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaAvisos.setAdapter(adapter);
    }
}