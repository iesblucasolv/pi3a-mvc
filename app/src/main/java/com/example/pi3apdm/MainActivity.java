package com.example.pi3apdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void btnOnClickViewPrimeiroAcesso(View view){
        Intent intent = new Intent(this, PrimeiroAcesso.class);
        startActivity(intent);
    }

    public void btnOnClickViewMenu(View view){
        setContentView(R.layout.menu);
    }

    public void btnOnClickViewVerAvisosVAlunos(View view){
        setContentView(R.layout.ver_avisos_valunos);
    }

    public void btnOnClickViewVerAvisosVrofessores(View view){
        setContentView(R.layout.ver_avisos_vprofessores);
    }

    public void btnOnClickViewVerResumosVAlunos(View view){
        setContentView(R.layout.ver_resumos_valunos);
    }

    public void btnOnClickViewVerResumosVrofessores(View view){
        setContentView(R.layout.ver_resumos_vprofessores);
    }

    public void btnOnClickViewEscreverAviso(View view){
        setContentView(R.layout.escrever_aviso);
    }

    public void btnOnClickViewEscreverResumo(View view){
        setContentView(R.layout.escrever_resumo);
    }

    public void btnOnClickViewLogin(View view){
        setContentView(R.layout.activity_main);
    }
}