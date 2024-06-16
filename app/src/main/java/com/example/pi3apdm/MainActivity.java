package com.example.pi3apdm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pi3apdm.dao.UsuarioDAO;
import com.example.pi3apdm.model.UsuarioVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    //variaveis

    public boolean logado = false;


    public void btnOnClickViewPrimeiroAcesso(View view){
        setContentView(R.layout.primeiro_acesso);
    }

    public void btnOnClickViewVerifyLogin(View view){

        UsuarioVO usuarioAPP = new UsuarioVO();

        EditText matriculaEditText = (EditText) findViewById(R.id.editTextMatriculaLogin);
        String LoginMatricula = matriculaEditText.getText().toString();
        EditText senhaEditText = (EditText) findViewById(R.id.editTextSenhaLogin);
        String LoginSenha = senhaEditText.getText().toString();

        usuarioAPP.setMatricula(LoginMatricula);
        usuarioAPP.setSenha(LoginSenha);

        List<UsuarioVO> ltUsuarios = new ArrayList<UsuarioVO>();

            UsuarioDAO usuarioBD = new UsuarioDAO(this);
            ltUsuarios = usuarioBD.getAllUsuarios();
            try{

                //Toast.makeText(this, String.valueOf(usuarioBD.getCountUsuarios()), Toast.LENGTH_SHORT).show();

                if(usuarioBD.getCountUsuarios() != 0){
                    for(int i = 0; i<usuarioBD.getCountUsuarios();i++){
                        //Toast.makeText(this, ltUsuarios.get(i).getMatricula(), Toast.LENGTH_SHORT).show();
                        if(Objects.equals(ltUsuarios.get(i).getMatricula(), usuarioAPP.getMatricula()) && Objects.equals(ltUsuarios.get(i).getSenha(), usuarioAPP.getSenha())){
                            logado = true;
                        }
                    }
                }
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if(logado){
                setContentView(R.layout.menu);
            }


    }

    public void btnOnClickCadastrarPrimeiroAcesso(View view){

        UsuarioVO usuarioAPP = new UsuarioVO();

        EditText matriculaEditText = (EditText) findViewById(R.id.editTextMatriculaPrimeiroAcesso);
        String PrimeiroAcessoMatricula = matriculaEditText.getText().toString();
        EditText senhaEditText = (EditText) findViewById(R.id.editTextSenhaPrimeiroAcesso);
        String PrimeiroAcessoSenha = senhaEditText.getText().toString();
        EditText ConfirmarSenhaEditText = (EditText) findViewById(R.id.editTextConfirmarSenhaPrimeiroAcesso);
        String PrimeiroAcessoConfirmarSenha = ConfirmarSenhaEditText.getText().toString();
        EditText NomeEditText = (EditText) findViewById(R.id.editTextNomePrimeiroAcesso);
        String PrimeiroAcessoNome = NomeEditText.getText().toString();

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupIsProfessor);

        int selectedId = radioGroup.getCheckedRadioButtonId();
        String selectedOption = "";
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            selectedOption = selectedRadioButton.getText().toString();
        }
        //Toast.makeText(this, selectedOption, Toast.LENGTH_SHORT).show();

        if(!selectedOption.isEmpty() && !PrimeiroAcessoMatricula.isEmpty() && !PrimeiroAcessoSenha.isEmpty() && !PrimeiroAcessoConfirmarSenha.isEmpty() && !PrimeiroAcessoNome.isEmpty()){
            if(selectedOption.equals("Professor")){
                usuarioAPP.setProfessor(true);
            } else{
                usuarioAPP.setProfessor(false);
            }

            usuarioAPP.setNome(PrimeiroAcessoNome);
            usuarioAPP.setMatricula(PrimeiroAcessoMatricula);

            if(PrimeiroAcessoSenha.equals(PrimeiroAcessoConfirmarSenha)){
                usuarioAPP.setSenha(PrimeiroAcessoSenha);

                logado = false;
                UsuarioDAO usuarioBD = new UsuarioDAO(this);
                //Toast.makeText(this, usuarioAPP.getMatricula(), Toast.LENGTH_SHORT).show();
                usuarioBD.addUsuario(usuarioAPP);
                setContentView(R.layout.activity_main);
            }

        }

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