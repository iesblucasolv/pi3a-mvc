package com.example.pi3apdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuProfessor extends AppCompatActivity {
    private Button avisos, resumos, infoTurma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void btnOnClickViewVerAvisosVrofessores(View view){
        Intent intent = new Intent(this, PrimeiroAcesso.class);
        startActivity(intent);
    }
    public void btnOnClickViewVerResumosVrofessores(View view){
        Intent intent = new Intent(this, PrimeiroAcesso.class);
        startActivity(intent);
    }
}